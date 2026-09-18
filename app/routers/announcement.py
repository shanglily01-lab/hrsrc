from __future__ import annotations
from app.config import now_cst
from fastapi import APIRouter, Request, Depends
from fastapi.responses import HTMLResponse, RedirectResponse
from fastapi.templating import Jinja2Templates
from sqlalchemy.orm import Session
from app.database import get_db
from app.models.user import User
from app.models.hr import Announcement
from app.auth import is_admin

router = APIRouter()
templates = Jinja2Templates(directory="app/templates")


def _user(request: Request, db: Session) -> User | None:
    uid = request.session.get("user_id")
    if not uid:
        return None
    return db.query(User).filter(User.id == uid).first()


def ok(data=None, msg="success"):
    return {"success": True, "message": msg, "data": data}


def err(msg):
    return {"success": False, "message": msg}


@router.get("/announcement/list", response_class=HTMLResponse)
def ann_list(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return RedirectResponse("/login")
    if is_admin(user):
        anns = db.query(Announcement).order_by(Announcement.id.desc()).all()
    else:
        anns = db.query(Announcement).filter(Announcement.status == "PUBLISHED").order_by(Announcement.id.desc()).all()
    return templates.TemplateResponse("announcement/list.html", {"request": request, "anns": anns, "is_admin": is_admin(user)})


@router.get("/api/announcements")
def list_anns(db: Session = Depends(get_db)):
    return ok([_ann_dict(a) for a in db.query(Announcement).order_by(Announcement.id.desc()).all()])


@router.get("/api/announcements/{aid}")
def get_ann(aid: int, db: Session = Depends(get_db)):
    a = db.query(Announcement).filter(Announcement.id == aid).first()
    return ok(_ann_dict(a)) if a else err("不存在")


def _safe(db: Session, fn):
    try:
        return fn()
    except Exception as e:
        db.rollback()
        return err(str(e) or "服务器内部错误")


@router.post("/api/announcements/create")
async def create_ann(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return err("未登录")
    body = await request.json()
    title = (body.get("title") or "").strip()
    if not title:
        return err("标题不能为空")

    def _do():
        a = Announcement(
            title=title,
            content=body.get("content"),
            status="DRAFT",
            publisher_id=user.id,
            publisher_name=user.uname,
        )
        db.add(a)
        db.commit()
        return ok()

    return _safe(db, _do)


@router.put("/api/announcements/{aid}")
async def update_ann(aid: int, request: Request, db: Session = Depends(get_db)):
    body = await request.json()

    def _do():
        a = db.query(Announcement).filter(Announcement.id == aid).first()
        if not a:
            return err("不存在")
        title = (body.get("title") or "").strip()
        if not title:
            return err("标题不能为空")
        a.title = title
        a.content = body.get("content")
        db.commit()
        return ok()

    return _safe(db, _do)


@router.put("/api/announcements/{aid}/publish")
def publish_ann(aid: int, db: Session = Depends(get_db)):
    def _do():
        a = db.query(Announcement).filter(Announcement.id == aid).first()
        if a:
            a.status = "PUBLISHED"
            a.publish_date = now_cst()
            db.commit()
        return ok()

    return _safe(db, _do)


@router.put("/api/announcements/{aid}/archive")
def archive_ann(aid: int, db: Session = Depends(get_db)):
    def _do():
        a = db.query(Announcement).filter(Announcement.id == aid).first()
        if a:
            a.status = "ARCHIVED"
            db.commit()
        return ok()

    return _safe(db, _do)


@router.delete("/api/announcements/{aid}")
def delete_ann(aid: int, db: Session = Depends(get_db)):
    def _do():
        db.query(Announcement).filter(Announcement.id == aid).delete()
        db.commit()
        return ok()

    return _safe(db, _do)


def _fmt_dt(v):
    if v is None:
        return None
    if hasattr(v, "strftime"):
        return v.strftime("%Y-%m-%d %H:%M:%S")
    return str(v)


def _ann_dict(a):
    published = _fmt_dt(a.publish_date)
    return {
        "id": a.id,
        "title": a.title,
        "content": a.content,
        "status": a.status,
        "publishDate": published,
        "publisherName": a.publisher_name,
        "createdAt": published,
    }
