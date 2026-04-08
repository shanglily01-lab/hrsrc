from __future__ import annotations
from datetime import datetime
from fastapi import APIRouter, Request, Depends, Form, UploadFile, File
from fastapi.responses import HTMLResponse, RedirectResponse
from fastapi.templating import Jinja2Templates
from sqlalchemy.orm import Session
from sqlalchemy import text
from app.database import get_db
from app.models.user import User, Profile, Role, Right, UserRole, RoleRight, Secrete
from app.models.hr import HintCard, HandBook, Distri, Zpacc, Demark
from app.models.finance import FinEmployee
from app.auth import is_admin, is_super_admin
import bcrypt
import uuid
import os
import logging

logger = logging.getLogger(__name__)

router = APIRouter()


def _decode_qr(content: bytes) -> str | None:
    """Try to decode a QR code from image bytes using multiple methods. Returns decoded text or None."""
    try:
        import cv2
        import numpy as np
        arr = np.frombuffer(content, np.uint8)
        img = cv2.imdecode(arr, cv2.IMREAD_COLOR)
        if img is None:
            return None
        detector = cv2.QRCodeDetector()

        def _try(image):
            data, _, _ = detector.detectAndDecode(image)
            return data.strip() if data else None

        # 1. Original color image
        result = _try(img)
        if result:
            return result

        # 2. Grayscale
        gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        result = _try(gray)
        if result:
            return result

        # 3. Binary threshold
        _, thresh = cv2.threshold(gray, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)
        result = _try(thresh)
        if result:
            return result

        # 4. Scaled up (helps with small/low-res QR codes)
        h, w = img.shape[:2]
        if max(h, w) < 800:
            scale = 800 / max(h, w)
            big = cv2.resize(img, (int(w * scale), int(h * scale)), interpolation=cv2.INTER_CUBIC)
            result = _try(big)
            if result:
                return result
            big_gray = cv2.cvtColor(big, cv2.COLOR_BGR2GRAY)
            _, big_thresh = cv2.threshold(big_gray, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)
            result = _try(big_thresh)
            if result:
                return result

        return None
    except Exception as e:
        logger.warning("QR decode failed: %s", e)
        return None
templates = Jinja2Templates(directory="app/templates")


def _user(request: Request, db: Session) -> User | None:
    uid = request.session.get("user_id")
    if not uid:
        return None
    return db.query(User).filter(User.id == uid).first()


def now_str():
    return datetime.now().strftime("%Y-%m-%d %H:%M:%S")


def ok(data=None, message="success"):
    return {"success": True, "message": message, "data": data}


def err(message):
    return {"success": False, "message": message}


# ─── 个人信息 ───────────────────────────────────────────────

@router.get("/profile", response_class=HTMLResponse)
def profile_page(request: Request, view_uid: int = 0, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return RedirectResponse("/login")

    admin = is_admin(user)

    # Admin can view any user's profile via ?view_uid=
    if admin and view_uid and view_uid != user.id:
        target_user = db.query(User).filter(User.id == view_uid).first()
        if not target_user:
            return HTMLResponse("用户不存在", status_code=404)
    else:
        target_user = user

    p = db.query(Profile).filter(Profile.uid == target_user.id).first()
    tg_name = (p.tg if p and p.tg else target_user.uname)
    emp = db.query(FinEmployee).filter(FinEmployee.tg_name == tg_name).first()
    if not emp:
        emp = db.query(FinEmployee).filter(FinEmployee.tg_name.ilike(tg_name)).first()
    # Fallback: try matching by username when TG name lookup fails
    if not emp and tg_name != target_user.uname:
        emp = db.query(FinEmployee).filter(FinEmployee.tg_name.ilike(target_user.uname)).first()

    sec = db.query(Secrete).filter(Secrete.uid == target_user.id).first()

    # For admin: build user list for picker
    all_users = []
    if admin:
        all_users = db.query(User).order_by(User.uname).all()

    return templates.TemplateResponse("profile.html", {
        "request": request, "user": user, "profile": p, "emp": emp,
        "target_user": target_user, "is_admin": admin,
        "all_users": all_users, "view_uid": target_user.id,
        "sec": sec,
    })


@router.post("/api/profile/upload")
async def upload_profile_file(
    request: Request,
    field: str = Form(...),
    file: UploadFile = File(...),
    db: Session = Depends(get_db)
):
    user = _user(request, db)
    if not user:
        return err("未登录")
    content_type = file.content_type or ""
    if content_type.startswith("image/"):
        save_dir = "uploads/images"
        ext = os.path.splitext(file.filename)[1] or ".png"
    elif content_type.startswith("audio/"):
        save_dir = "uploads/audio"
        ext = os.path.splitext(file.filename)[1] or ".mp3"
    else:
        return err("仅支持图片或音频文件")
    os.makedirs(save_dir, exist_ok=True)
    filename = str(uuid.uuid4()) + ext
    filepath = os.path.join(save_dir, filename)
    content = await file.read()
    with open(filepath, "wb") as f:
        f.write(content)
    url = "/" + filepath.replace("\\", "/")
    # For wallet image uploads: store in saddr_img/vaddr_img, attempt QR decode
    # field param is "saddr_img" or "vaddr_img" for image uploads
    qr_data = None
    if content_type.startswith("image/") and field in ("saddr_img", "vaddr_img"):
        qr_data = _decode_qr(content)
    p = db.query(Profile).filter(Profile.uid == user.id).first()
    if p:
        setattr(p, field, url)
        p.udate = now_str()
    else:
        p = Profile(uid=user.id, uname=user.uname, udate=now_str(), **{field: url})
        db.add(p)
    db.commit()
    return ok(data={"url": url, "qr_data": qr_data})


@router.post("/api/profile")
async def save_profile(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return err("未登录")
    body = await request.json()

    # Admin can save any user's profile via view_uid in body
    target_uid = body.get("view_uid")
    if target_uid and is_admin(user) and int(target_uid) != user.id:
        target_user = db.query(User).filter(User.id == int(target_uid)).first()
        if not target_user:
            return err("用户不存在")
    else:
        target_user = user

    p = db.query(Profile).filter(Profile.uid == target_user.id).first()
    # Non-admin may not change wallet addresses
    _base_fields = ["email", "tg", "whatsapp", "phone"]
    _addr_fields = ["saddr", "vaddr"]
    _fields = _base_fields + (_addr_fields if is_admin(user) else [])
    if p:
        for k in _fields:
            setattr(p, k, body.get(k))
        p.udate = now_str()
    else:
        p = Profile(uid=target_user.id, uname=target_user.uname, udate=now_str(),
                    **{k: body.get(k) for k in _fields})
        db.add(p)
    db.commit()
    return ok()


@router.post("/api/profile/secrete")
async def save_secrete(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return err("未登录")
    body = await request.json()
    target_uid = body.get("view_uid")
    if target_uid and is_admin(user) and int(target_uid) != user.id:
        target_user = db.query(User).filter(User.id == int(target_uid)).first()
        if not target_user:
            return err("用户不存在")
    else:
        target_user = user
    scode = (body.get("scode") or "").strip()
    sec = db.query(Secrete).filter(Secrete.uid == target_user.id).first()
    if sec:
        sec.scode = scode
        sec.sdate = now_str()[:10]
    else:
        sec = Secrete(uid=target_user.id, uname=target_user.uname,
                      scode=scode, sdate=now_str()[:10])
        db.add(sec)
    db.commit()
    return ok()


# ─── 修改密码 ──────────────────────────────────────────────

@router.get("/pass", response_class=HTMLResponse)
def pass_page(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return RedirectResponse("/login")
    return templates.TemplateResponse("pass.html", {"request": request})


@router.post("/api/updatePwd")
async def update_pwd(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return err("未登录")
    body = await request.json()
    old_pwd = body.get("oldPwd", "")
    new_pwd = body.get("newPwd", "")
    if not bcrypt.checkpw(old_pwd.encode(), user.upass.encode()):
        return err("原密码错误")
    hashed = bcrypt.hashpw(new_pwd.encode(), bcrypt.gensalt(10)).decode()
    user.upass = hashed
    db.commit()
    return ok(message="密码修改成功")


# ─── 打卡 ─────────────────────────────────────────────────

@router.get("/hintCard", response_class=HTMLResponse)
def hintcard_page(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return RedirectResponse("/login")
    today = datetime.now().strftime("%Y-%m-%d")
    records = db.query(HintCard).filter(HintCard.uid == user.id, HintCard.hinttime.like(f"{today}%")).all()
    return templates.TemplateResponse("hintcard.html", {"request": request, "user": user, "records": records, "today": today})


@router.post("/api/hintCard")
async def do_hint(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return err("未登录")
    body = await request.json()
    ampm = body.get("ampm", "AM")
    hc = HintCard(uid=user.id, uname=user.uname, ampm=ampm, hinttime=now_str())
    db.add(hc)
    db.commit()
    return ok(message="打卡成功")


@router.get("/hintCardList", response_class=HTMLResponse)
def hintcard_list(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user or not is_admin(user):
        return HTMLResponse("无权访问", 403)
    from datetime import datetime, timedelta
    from collections import OrderedDict
    since = (datetime.now() - timedelta(days=30)).strftime("%Y-%m-%d")
    records = (db.query(HintCard)
               .filter(HintCard.hinttime >= since)
               .order_by(HintCard.hinttime.desc())
               .all())
    grouped = OrderedDict()
    for r in records:
        d = str(r.hinttime)[:10]
        grouped.setdefault(d, []).append(r)
    all_users = sorted({r.uname for r in records if r.uname})
    return templates.TemplateResponse("hintcards.html", {
        "request": request, "grouped": grouped, "all_users": all_users
    })


# ─── 员工守则 ──────────────────────────────────────────────

@router.get("/handbook", response_class=HTMLResponse)
def handbook_page(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return RedirectResponse("/login")
    books = db.query(HandBook).order_by(HandBook.segid, HandBook.id).all()
    grouped = {}
    for b in books:
        key = b.segid or 0
        if key not in grouped:
            grouped[key] = []
        grouped[key].append(b)
    return templates.TemplateResponse("handbook.html", {"request": request, "books": books, "grouped": grouped})


@router.get("/handbooklist", response_class=HTMLResponse)
def handbooklist_page(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user or not is_super_admin(user):
        return HTMLResponse("无权访问", 403)
    books = db.query(HandBook).order_by(HandBook.segid, HandBook.id).all()
    grouped = {}
    for b in books:
        key = b.segid or 0
        if key not in grouped:
            grouped[key] = []
        grouped[key].append(b)
    return templates.TemplateResponse("handbooklist.html", {"request": request, "books": books, "grouped": grouped})


@router.post("/api/insertHandBook")
async def insert_handbook(request: Request, db: Session = Depends(get_db)):
    body = await request.json()
    hb = HandBook(segid=body.get("segid"), books=body.get("books"))
    db.add(hb)
    db.commit()
    return ok()


@router.post("/api/updateHandBook")
async def update_handbook(request: Request, db: Session = Depends(get_db)):
    body = await request.json()
    hb = db.query(HandBook).filter(HandBook.id == body.get("id")).first()
    if hb:
        hb.segid = body.get("segid")
        hb.books = body.get("books")
        db.commit()
    return ok()


@router.post("/api/deleteHandBook")
async def delete_handbook(request: Request, db: Session = Depends(get_db)):
    body = await request.json()
    db.query(HandBook).filter(HandBook.id == body.get("id")).delete()
    db.commit()
    return ok()


# ─── 分布式事项 ────────────────────────────────────────────

@router.get("/demark", response_class=HTMLResponse)
def demark_page(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user or not is_admin(user):
        return HTMLResponse("无权访问", 403)
    items = db.query(Demark).order_by(Demark.id.desc()).all()
    return templates.TemplateResponse("demarks.html", {"request": request, "items": items})


@router.get("/api/demark")
def list_demark(db: Session = Depends(get_db)):
    return ok([{"id": d.id, "sx": d.sx, "ms": d.ms} for d in db.query(Demark).order_by(Demark.id.desc()).all()])


@router.post("/api/demark")
async def create_demark(request: Request, db: Session = Depends(get_db)):
    body = await request.json()
    db.add(Demark(sx=body.get("sx"), ms=body.get("ms")))
    db.commit()
    return ok()


@router.put("/api/demark/{did}")
async def update_demark(did: int, request: Request, db: Session = Depends(get_db)):
    body = await request.json()
    d = db.query(Demark).filter(Demark.id == did).first()
    if d:
        d.sx = body.get("sx")
        d.ms = body.get("ms")
        db.commit()
    return ok()


@router.delete("/api/demark/{did}")
def delete_demark(did: int, db: Session = Depends(get_db)):
    db.query(Demark).filter(Demark.id == did).delete()
    db.commit()
    return ok()


# ─── 招聘账号 ──────────────────────────────────────────────

@router.get("/zpacc", response_class=HTMLResponse)
def zpacc_page(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user or not is_admin(user):
        return HTMLResponse("无权访问", 403)
    items = db.query(Zpacc).order_by(Zpacc.id.desc()).all()
    return templates.TemplateResponse("zpaccs.html", {"request": request, "items": items})


@router.get("/api/zpacc/{zid}")
def get_zpacc(zid: int, db: Session = Depends(get_db)):
    z = db.query(Zpacc).filter(Zpacc.id == zid).first()
    return ok({"id": z.id, "zhName": z.zh_name, "zhPwd": z.zh_pwd, "yxName": z.yx_name, "yxPwd": z.yx_pwd, "mark": z.mark} if z else None)


@router.post("/api/zpacc")
async def create_zpacc(request: Request, db: Session = Depends(get_db)):
    body = await request.json()
    db.add(Zpacc(zh_name=body.get("zhName"), zh_pwd=body.get("zhPwd"), yx_name=body.get("yxName"), yx_pwd=body.get("yxPwd"), mark=body.get("mark")))
    db.commit()
    return ok()


@router.put("/api/zpacc/{zid}")
async def update_zpacc(zid: int, request: Request, db: Session = Depends(get_db)):
    body = await request.json()
    z = db.query(Zpacc).filter(Zpacc.id == zid).first()
    if z:
        z.zh_name = body.get("zhName"); z.zh_pwd = body.get("zhPwd")
        z.yx_name = body.get("yxName"); z.yx_pwd = body.get("yxPwd"); z.mark = body.get("mark")
        db.commit()
    return ok()


@router.delete("/api/zpacc/{zid}")
def delete_zpacc(zid: int, db: Session = Depends(get_db)):
    db.query(Zpacc).filter(Zpacc.id == zid).delete()
    db.commit()
    return ok()


# ─── 分发招聘 ──────────────────────────────────────────────

@router.get("/distri", response_class=HTMLResponse)
def distri_page(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return RedirectResponse("/login")
    items = db.query(Distri).order_by(Distri.id.desc()).all()
    return templates.TemplateResponse("distris.html", {"request": request, "items": items})


# ─── 用户安全码 ────────────────────────────────────────────

@router.get("/usersecs", response_class=HTMLResponse)
def usersecs_page(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user or not is_admin(user):
        return HTMLResponse("无权访问", 403)
    secs = db.execute(text("SELECT u.id, u.uname, s.scode FROM t_user u LEFT JOIN t_sec s ON u.id = s.uid ORDER BY u.id")).fetchall()
    return templates.TemplateResponse("usersecs.html", {"request": request, "secs": secs})


# ─── 系统配置 ──────────────────────────────────────────────

@router.get("/users", response_class=HTMLResponse)
def users_page(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user or not is_super_admin(user):
        return HTMLResponse("无权访问", 403)
    users = db.query(User).order_by(User.id).all()
    return templates.TemplateResponse("users.html", {"request": request, "users": users})


@router.post("/api/insertUser")
async def insert_user(request: Request, db: Session = Depends(get_db)):
    body = await request.json()
    hashed = bcrypt.hashpw(body.get("upass", "123456").encode(), bcrypt.gensalt(10)).decode()
    db.add(User(uname=body.get("uname"), upass=hashed))
    db.commit()
    return ok()


@router.post("/api/deleteUser")
async def delete_user(request: Request, db: Session = Depends(get_db)):
    body = await request.json()
    db.query(User).filter(User.id == body.get("id")).delete()
    db.commit()
    return ok()


@router.get("/userroles", response_class=HTMLResponse)
def userroles_page(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user or not is_super_admin(user):
        return HTMLResponse("无权访问", 403)
    rows = db.query(UserRole).order_by(UserRole.id).all()
    users = db.query(User).order_by(User.id).all()
    roles = db.query(Role).order_by(Role.id).all()
    return templates.TemplateResponse("userroles.html", {"request": request, "rows": rows, "users": users, "roles": roles})


@router.get("/role", response_class=HTMLResponse)
def roles_page(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user or not is_super_admin(user):
        return HTMLResponse("无权访问", 403)
    roles = db.query(Role).order_by(Role.id).all()
    return templates.TemplateResponse("roles.html", {"request": request, "roles": roles})


@router.get("/right", response_class=HTMLResponse)
def rights_page(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user or not is_super_admin(user):
        return HTMLResponse("无权访问", 403)
    rights = db.query(Right).order_by(Right.id).all()
    return templates.TemplateResponse("rights.html", {"request": request, "rights": rights})


@router.get("/rolerights", response_class=HTMLResponse)
def rolerights_page(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user or not is_super_admin(user):
        return HTMLResponse("无权访问", 403)
    rows = db.query(RoleRight).order_by(RoleRight.id).all()
    roles = db.query(Role).all()
    rights = db.query(Right).all()
    return templates.TemplateResponse("rolerights.html", {"request": request, "rows": rows, "roles": roles, "rights": rights})


# ─── 角色 REST ─────────────────────────────────────────────

@router.post("/api/role")
async def create_role(request: Request, db: Session = Depends(get_db)):
    body = await request.json()
    db.add(Role(rname=body.get("rname")))
    db.commit()
    return ok()


@router.put("/api/role/{rid}")
async def update_role(rid: int, request: Request, db: Session = Depends(get_db)):
    body = await request.json()
    r = db.query(Role).filter(Role.id == rid).first()
    if r:
        r.rname = body.get("rname")
        db.commit()
    return ok()


@router.delete("/api/role/{rid}")
def delete_role(rid: int, db: Session = Depends(get_db)):
    db.query(Role).filter(Role.id == rid).delete()
    db.commit()
    return ok()


# ─── 权限 REST ─────────────────────────────────────────────

@router.post("/api/right")
async def create_right(request: Request, db: Session = Depends(get_db)):
    body = await request.json()
    db.add(Right(rname=body.get("rname"), pid=body.get("pid")))
    db.commit()
    return ok()


@router.put("/api/right/{rid}")
async def update_right(rid: int, request: Request, db: Session = Depends(get_db)):
    body = await request.json()
    r = db.query(Right).filter(Right.id == rid).first()
    if r:
        r.rname = body.get("rname")
        r.pid = body.get("pid")
        db.commit()
    return ok()


@router.delete("/api/right/{rid}")
def delete_right(rid: int, db: Session = Depends(get_db)):
    db.query(Right).filter(Right.id == rid).delete()
    db.commit()
    return ok()


# ─── 用户角色 REST ─────────────────────────────────────────

@router.post("/api/userrole")
async def create_userrole(request: Request, db: Session = Depends(get_db)):
    body = await request.json()
    db.add(UserRole(uid=body.get("uid"), rid=body.get("rid")))
    db.commit()
    return ok()


@router.delete("/api/userrole/{rid}")
def delete_userrole(rid: int, db: Session = Depends(get_db)):
    db.query(UserRole).filter(UserRole.id == rid).delete()
    db.commit()
    return ok()


# ─── 角色权限 REST ─────────────────────────────────────────

@router.post("/api/rolerights")
async def create_roleright(request: Request, db: Session = Depends(get_db)):
    body = await request.json()
    db.add(RoleRight(rid=body.get("rid"), rgid=body.get("rgid")))
    db.commit()
    return ok()


@router.delete("/api/rolerights/{rid}")
def delete_roleright(rid: int, db: Session = Depends(get_db)):
    db.query(RoleRight).filter(RoleRight.id == rid).delete()
    db.commit()
    return ok()


# ─── 用户密码重置 ─────────────────────────────────────────

DEFAULT_RESET_PWD = "AA@1111"

@router.get("/admin/resetpwd", response_class=HTMLResponse)
def page_reset_pwd(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user or not is_admin(user):
        return HTMLResponse("无权访问", status_code=403)
    users = db.query(User).order_by(User.id.asc()).all()
    return templates.TemplateResponse("resetpwd.html", {
        "request": request, "users": users,
    })


@router.put("/api/admin/resetpwd/{uid}")
def api_reset_pwd(uid: int, request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user or not is_admin(user):
        return err("无权操作")
    target = db.query(User).filter(User.id == uid).first()
    if not target:
        return err("用户不存在")
    hashed = bcrypt.hashpw(DEFAULT_RESET_PWD.encode(), bcrypt.gensalt(10)).decode()
    target.upass = hashed
    db.commit()
    return ok(message=f"已将 {target.uname} 的密码重置为 {DEFAULT_RESET_PWD}")
