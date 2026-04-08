from __future__ import annotations
from fastapi import APIRouter, Request, Depends
from fastapi.responses import HTMLResponse, RedirectResponse
from fastapi.templating import Jinja2Templates
from sqlalchemy.orm import Session
from app.database import get_db
from app.models.user import User
from app.models.project import Pro
from app.auth import is_admin, is_super_admin, is_finance, accessible_pids

router = APIRouter()
templates = Jinja2Templates(directory="app/templates")


def _get_user(request: Request, db: Session) -> User | None:
    user_id = request.session.get("user_id")
    if not user_id:
        return None
    return db.query(User).filter(User.id == user_id).first()


def _is_mobile(request: Request) -> bool:
    ua = request.headers.get("user-agent", "").lower()
    return any(k in ua for k in ("mobile", "android", "iphone", "ipad", "ipod"))


@router.get("/", response_class=HTMLResponse)
def root(request: Request):
    if _is_mobile(request):
        return RedirectResponse(url="/mobile/checkin")
    return RedirectResponse(url="/index")


@router.get("/index", response_class=HTMLResponse)
def index(request: Request, db: Session = Depends(get_db)):
    user = _get_user(request, db)
    if not user:
        return RedirectResponse(url="/login")
    pids = accessible_pids(user, db)
    if pids is None:
        pros = db.query(Pro).order_by(Pro.id).all()
    else:
        pros = db.query(Pro).filter(Pro.id.in_(pids)).order_by(Pro.id).all() if pids else []
    return templates.TemplateResponse("index.html", {
        "request": request,
        "uname": user.uname,
        "pros": pros,
        "is_admin": is_admin(user),
        "is_finance": is_finance(user),
        "is_super_admin": is_super_admin(user),
    })
