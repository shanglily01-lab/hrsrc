from datetime import datetime
from fastapi import APIRouter, Request, Depends
from fastapi.responses import HTMLResponse, RedirectResponse
from fastapi.templating import Jinja2Templates
from sqlalchemy.orm import Session
from app.database import get_db
from app.models.user import User
from app.models.leave import LeaveApplication
from app.auth import is_admin

router = APIRouter()
templates = Jinja2Templates(directory="app/templates")


def _user(request: Request, db: Session) -> User | None:
    uid = request.session.get("user_id")
    if not uid:
        return None
    return db.query(User).filter(User.id == uid).first()


def now_str():
    return datetime.now().strftime("%Y-%m-%d %H:%M:%S")


def ok(data=None, msg="success"):
    return {"success": True, "message": msg, "data": data}


def err(msg):
    return {"success": False, "message": msg}


@router.get("/leave/my", response_class=HTMLResponse)
def my_leave(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return RedirectResponse("/login")
    apps = db.query(LeaveApplication).filter(LeaveApplication.employee_id == user.id).order_by(LeaveApplication.id.desc()).all()
    return templates.TemplateResponse("leave/my.html", {"request": request, "apps": apps, "user": user})


@router.get("/leave/approve", response_class=HTMLResponse)
def approve_leave_page(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user or not is_admin(user):
        return HTMLResponse("无权访问", 403)
    apps = db.query(LeaveApplication).order_by(LeaveApplication.id.desc()).all()
    return templates.TemplateResponse("leave/approve.html", {"request": request, "apps": apps})


@router.post("/api/leave-applications")
async def create_leave(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return err("未登录")
    body = await request.json()
    la = LeaveApplication(
        employee_id=user.id, employee_name=user.uname,
        department=body.get("department"), leave_type=body.get("leaveType"),
        start_date=body.get("startDate"), end_date=body.get("endDate"),
        duration=body.get("duration"), reason=body.get("reason"),
        status="PENDING", created_at=now_str(), updated_at=now_str(),
    )
    db.add(la)
    db.commit()
    return ok()


@router.put("/api/leave-applications/{lid}/approve")
async def approve_leave(lid: int, request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return err("未登录")
    body = await request.json()
    la = db.query(LeaveApplication).filter(LeaveApplication.id == lid).first()
    if not la:
        return err("不存在")
    la.status = "APPROVED"
    la.approver_id = user.id
    la.approver_name = user.uname
    la.approval_time = now_str()
    la.approval_comment = body.get("comment", "")
    la.updated_at = now_str()
    db.commit()
    return ok()


@router.put("/api/leave-applications/{lid}/reject")
async def reject_leave(lid: int, request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return err("未登录")
    body = await request.json()
    la = db.query(LeaveApplication).filter(LeaveApplication.id == lid).first()
    if not la:
        return err("不存在")
    la.status = "REJECTED"
    la.approver_id = user.id
    la.approver_name = user.uname
    la.approval_time = now_str()
    la.approval_comment = body.get("comment", "")
    la.updated_at = now_str()
    db.commit()
    return ok()


@router.put("/api/leave-applications/{lid}/cancel")
def cancel_leave(lid: int, db: Session = Depends(get_db)):
    la = db.query(LeaveApplication).filter(LeaveApplication.id == lid).first()
    if la:
        la.status = "CANCELLED"
        la.updated_at = now_str()
        db.commit()
    return ok()
