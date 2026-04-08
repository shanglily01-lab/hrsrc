from __future__ import annotations
from datetime import datetime, timedelta
from fastapi import APIRouter, Request, Depends
from fastapi.responses import HTMLResponse, RedirectResponse
from fastapi.templating import Jinja2Templates
from sqlalchemy.orm import Session
from app.database import get_db
from app.models.user import User
from app.models.project import Pro, Plan, Version, Require, Reqmark, DayReport, WeekReport, MeetReport
from app.auth import is_super_admin, accessible_pids

router = APIRouter()
templates = Jinja2Templates(directory="app/templates")


def _user(request: Request, db: Session) -> User | None:
    uid = request.session.get("user_id")
    if not uid:
        return None
    return db.query(User).filter(User.id == uid).first()


def now_str():
    return datetime.now().strftime("%Y-%m-%d %H:%M:%S")


def _can_access_pid(user: User, pid: int, db: Session) -> bool:
    pids = accessible_pids(user, db)
    return pids is None or pid in pids


def ok(data=None, msg="success"):
    return {"success": True, "message": msg, "data": data}


def err(msg):
    return {"success": False, "message": msg}


# ─── 项目管理 (admin only) ─────────────────────────────────

@router.get("/pro", response_class=HTMLResponse)
def pro_page(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user or not is_super_admin(user):
        return HTMLResponse("无权访问", 403)
    pros = db.query(Pro).order_by(Pro.id).all()
    return templates.TemplateResponse("pros.html", {"request": request, "pros": pros})


@router.get("/api/pro")
def list_pro(db: Session = Depends(get_db)):
    return ok([{"id": p.id, "pname": p.pname, "pmark": p.pmark} for p in db.query(Pro).order_by(Pro.id).all()])


@router.post("/api/pro")
async def create_pro(request: Request, db: Session = Depends(get_db)):
    body = await request.json()
    db.add(Pro(pname=body.get("pname"), pmark=body.get("pmark")))
    db.commit()
    return ok()


@router.put("/api/pro/{pid}")
async def update_pro(pid: int, request: Request, db: Session = Depends(get_db)):
    body = await request.json()
    p = db.query(Pro).filter(Pro.id == pid).first()
    if p:
        p.pname = body.get("pname"); p.pmark = body.get("pmark")
        db.commit()
    return ok()


@router.delete("/api/pro/{pid}")
def delete_pro(pid: int, db: Session = Depends(get_db)):
    db.query(Pro).filter(Pro.id == pid).delete()
    db.commit()
    return ok()


# ─── 需求 ─────────────────────────────────────────────────

@router.get("/req", response_class=HTMLResponse)
def req_page(request: Request, pid: int = 0, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return RedirectResponse("/login")
    if not _can_access_pid(user, pid, db):
        return HTMLResponse("无权访问该项目", status_code=403)
    raw = db.query(Require).filter(Require.pid == pid).order_by(Require.id).all()
    raw = sorted(raw, key=lambda r: (r.model or "", r.block or ""))
    pro = db.query(Pro).filter(Pro.id == pid).first()
    # pre-group in Python to avoid Jinja2 groupby None-comparison error
    grouped = {}
    for r in raw:
        key = r.model or "(未分类)"
        if key not in grouped:
            grouped[key] = []
        grouped[key].append(r)
    return templates.TemplateResponse("reqs.html", {"request": request, "reqs": raw, "grouped": grouped, "pro": pro, "pid": pid})


@router.get("/api/req/{rid}")
def get_req(rid: int, db: Session = Depends(get_db)):
    r = db.query(Require).filter(Require.id == rid).first()
    return ok({"id": r.id, "model": r.model, "block": r.block, "item": r.item, "ms": r.ms, "pid": r.pid} if r else None)


@router.post("/api/req")
async def create_req(request: Request, db: Session = Depends(get_db)):
    body = await request.json()
    db.add(Require(model=body.get("model"), block=body.get("block"), item=body.get("item"), ms=body.get("ms"), pid=body.get("pid")))
    db.commit()
    return ok()


@router.put("/api/req/{rid}")
async def update_req(rid: int, request: Request, db: Session = Depends(get_db)):
    body = await request.json()
    r = db.query(Require).filter(Require.id == rid).first()
    if r:
        r.model = body.get("model"); r.block = body.get("block"); r.item = body.get("item"); r.ms = body.get("ms")
        db.commit()
    return ok()


@router.delete("/api/req/{rid}")
def delete_req(rid: int, db: Session = Depends(get_db)):
    db.query(Require).filter(Require.id == rid).delete()
    db.commit()
    return ok()


# ─── 日报 ─────────────────────────────────────────────────

@router.get("/dayreport", response_class=HTMLResponse)
def dayreport_page(request: Request, pid: int = 0, days: int = 30, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return RedirectResponse("/login")
    if not _can_access_pid(user, pid, db):
        return HTMLResponse("无权访问该项目", status_code=403)
    since = (datetime.now() - timedelta(days=days)).strftime("%Y-%m-%d")
    reports = (db.query(DayReport)
               .filter(DayReport.pid == pid, DayReport.sdate >= since)
               .order_by(DayReport.sdate.desc(), DayReport.id.desc())
               .all())
    pro = db.query(Pro).filter(Pro.id == pid).first()
    # 按日期分组
    from collections import OrderedDict
    grouped = OrderedDict()
    for r in reports:
        d = str(r.sdate)[:10]
        grouped.setdefault(d, []).append(r)
    return templates.TemplateResponse("dayreports.html", {
        "request": request, "grouped": grouped, "pro": pro,
        "pid": pid, "user": user, "days": days
    })


@router.post("/api/dayreport")
async def create_dayreport(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return err("未登录")
    body = await request.json()
    db.add(DayReport(uid=user.id, uname=user.uname, sdate=now_str()[:10], scontext=body.get("scontext"), pid=body.get("pid")))
    db.commit()
    return ok()


@router.delete("/api/dayreport/{rid}")
def delete_dayreport(rid: int, db: Session = Depends(get_db)):
    db.query(DayReport).filter(DayReport.id == rid).delete()
    db.commit()
    return ok()


# ─── 周报 ─────────────────────────────────────────────────

@router.get("/weekreport", response_class=HTMLResponse)
def weekreport_page(request: Request, pid: int = 0, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return RedirectResponse("/login")
    if not _can_access_pid(user, pid, db):
        return HTMLResponse("无权访问该项目", status_code=403)
    reports = (db.query(WeekReport)
               .filter(WeekReport.pid == pid)
               .order_by(WeekReport.id.desc())
               .limit(200)
               .all())
    # 清理日期格式（去掉时间部分），并补充 uname
    users_map = {u.id: u.uname for u in db.query(User).all()}
    for r in reports:
        r._uname = users_map.get(r.uid, str(r.uid))
        r._sdate = str(r.sdate)[:10] if r.sdate else ""
        r._edate = str(r.edate)[:10] if r.edate else ""
    # 按周（sdate前10位）分组
    from collections import OrderedDict
    grouped = OrderedDict()
    for r in reports:
        key = r._sdate
        grouped.setdefault(key, []).append(r)
    pro = db.query(Pro).filter(Pro.id == pid).first()
    return templates.TemplateResponse("weekreports.html", {
        "request": request, "grouped": grouped, "pro": pro,
        "pid": pid, "user": user
    })


@router.post("/api/weekreport")
async def create_weekreport(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return err("未登录")
    body = await request.json()
    db.add(WeekReport(uid=user.id, sdate=body.get("sdate"), edate=body.get("edate"), scontext=body.get("scontext"), pid=body.get("pid")))
    db.commit()
    return ok()


@router.delete("/api/weekreport/{rid}")
def delete_weekreport(rid: int, db: Session = Depends(get_db)):
    db.query(WeekReport).filter(WeekReport.id == rid).delete()
    db.commit()
    return ok()


# ─── 周例会 ────────────────────────────────────────────────

@router.get("/meetreport", response_class=HTMLResponse)
def meetreport_page(request: Request, pid: int = 0, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return RedirectResponse("/login")
    if not _can_access_pid(user, pid, db):
        return HTMLResponse("无权访问该项目", status_code=403)
    reports = db.query(MeetReport).filter(MeetReport.pid == pid).order_by(MeetReport.id.desc()).all()
    pro = db.query(Pro).filter(Pro.id == pid).first()
    return templates.TemplateResponse("meetreports.html", {"request": request, "reports": reports, "pro": pro, "pid": pid})


@router.post("/api/meetreport")
async def create_meetreport(request: Request, db: Session = Depends(get_db)):
    body = await request.json()
    db.add(MeetReport(mtype=body.get("mtype"), mdate=body.get("mdate"), mdesc=body.get("mdesc"), mzg=body.get("mzg"), pid=body.get("pid")))
    db.commit()
    return ok()


@router.delete("/api/meetreport/{rid}")
def delete_meetreport(rid: int, db: Session = Depends(get_db)):
    db.query(MeetReport).filter(MeetReport.id == rid).delete()
    db.commit()
    return ok()


# ─── 版本 ─────────────────────────────────────────────────

@router.get("/version", response_class=HTMLResponse)
def version_page(request: Request, pid: int = 0, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return RedirectResponse("/login")
    if not _can_access_pid(user, pid, db):
        return HTMLResponse("无权访问该项目", status_code=403)
    versions = db.query(Version).filter(Version.pid == pid).order_by(Version.id.desc()).all()
    pro = db.query(Pro).filter(Pro.id == pid).first()
    return templates.TemplateResponse("versions.html", {"request": request, "versions": versions, "pro": pro, "pid": pid})


@router.post("/api/version")
async def create_version(request: Request, db: Session = Depends(get_db)):
    body = await request.json()
    db.add(Version(pid=body.get("pid"), vers=body.get("vers"), mark=body.get("mark")))
    db.commit()
    return ok()


@router.delete("/api/version/{vid}")
def delete_version(vid: int, db: Session = Depends(get_db)):
    db.query(Version).filter(Version.id == vid).delete()
    db.commit()
    return ok()


# ─── 计划 ─────────────────────────────────────────────────

@router.get("/plan", response_class=HTMLResponse)
def plan_page(request: Request, pid: int = 0, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return RedirectResponse("/login")
    if not _can_access_pid(user, pid, db):
        return HTMLResponse("无权访问该项目", status_code=403)
    plans = db.query(Plan).filter(Plan.pid == pid).order_by(Plan.id.desc()).all()
    pro = db.query(Pro).filter(Pro.id == pid).first()
    versions = db.query(Version).filter(Version.pid == pid).all()
    return templates.TemplateResponse("plans.html", {"request": request, "plans": plans, "pro": pro, "pid": pid, "versions": versions})


@router.post("/api/plan")
async def create_plan(request: Request, db: Session = Depends(get_db)):
    body = await request.json()
    db.add(Plan(pid=body.get("pid"), vid=body.get("vid"), ptext=body.get("ptext"), sdate=body.get("sdate"), edate=body.get("edate")))
    db.commit()
    return ok()


@router.delete("/api/plan/{plid}")
def delete_plan(plid: int, db: Session = Depends(get_db)):
    db.query(Plan).filter(Plan.id == plid).delete()
    db.commit()
    return ok()


# ─── 运营需求 ──────────────────────────────────────────────

@router.get("/reqmark", response_class=HTMLResponse)
def reqmark_page(request: Request, pid: int = 0, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return RedirectResponse("/login")
    if not _can_access_pid(user, pid, db):
        return HTMLResponse("无权访问该项目", status_code=403)
    marks = db.query(Reqmark).filter(Reqmark.pid == pid).order_by(Reqmark.id.desc()).all()
    pro = db.query(Pro).filter(Pro.id == pid).first()
    return templates.TemplateResponse("reqmarks.html", {"request": request, "marks": marks, "pro": pro, "pid": pid, "user": user})


@router.post("/api/reqmark")
async def create_reqmark(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return err("未登录")
    body = await request.json()
    db.add(Reqmark(uid=user.id, reqname=body.get("reqname"), reqdesc=body.get("reqdesc"), reqlevel=body.get("reqlevel"), reqver=body.get("reqver"), reqmark=body.get("reqmark"), pid=body.get("pid")))
    db.commit()
    return ok()


@router.delete("/api/reqmark/{mid}")
def delete_reqmark(mid: int, db: Session = Depends(get_db)):
    db.query(Reqmark).filter(Reqmark.id == mid).delete()
    db.commit()
    return ok()
