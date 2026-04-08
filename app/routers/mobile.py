from __future__ import annotations

import os
import shutil
import uuid
from datetime import datetime, date, timedelta

from fastapi import APIRouter, Request, Depends, UploadFile, File, Form
from fastapi.responses import HTMLResponse, RedirectResponse, JSONResponse
from fastapi.templating import Jinja2Templates
from sqlalchemy.orm import Session

from app.database import get_db
from app.models.user import User
from app.models.project import Pro, DayReport, WeekReport
from app.models.finance import FinExpense
from app.models.mobile import CheckIn
from app.auth import verify_password

router = APIRouter()
templates = Jinja2Templates(directory="app/templates")

UPLOAD_DIR = "uploads/expenses"
os.makedirs(UPLOAD_DIR, exist_ok=True)

# 上班时间 09:30 之后算迟到
LATE_HOUR, LATE_MIN = 9, 30


def _user(request: Request, db: Session) -> User | None:
    uid = request.session.get("user_id")
    if not uid:
        return None
    return db.query(User).filter(User.id == uid).first()


def _today() -> str:
    return datetime.now().strftime("%Y-%m-%d")


def _month() -> str:
    return datetime.now().strftime("%Y-%m")


def _redirect_login():
    return RedirectResponse("/mobile/login", status_code=302)


# ─────────────────────────────────────────────
# 登录
# ─────────────────────────────────────────────

@router.get("/mobile/login", response_class=HTMLResponse)
def mobile_login_page(request: Request):
    user = request.session.get("user_id")
    if user:
        return RedirectResponse("/mobile/checkin", status_code=302)
    return templates.TemplateResponse("mobile/login.html", {"request": request, "error": None})


from fastapi import Form as FastForm


@router.post("/mobile/login")
async def mobile_login_post(
    request: Request,
    db: Session = Depends(get_db),
):
    form = await request.form()
    uname = str(form.get("uname", "")).strip()
    pwd = str(form.get("pwd", "")).strip()
    captcha = str(form.get("captcha", "")).strip().upper()

    ctx = {"request": request, "error": None}

    sess_cap = str(request.session.get("captcha", "")).upper()
    if captcha != sess_cap:
        ctx["error"] = "验证码错误"
        return templates.TemplateResponse("mobile/login.html", ctx)

    user = db.query(User).filter(User.uname == uname).first()
    if not user or not verify_password(pwd, user.upass or ""):
        ctx["error"] = "用户名或密码错误"
        return templates.TemplateResponse("mobile/login.html", ctx)

    request.session["user_id"] = user.id
    request.session["uname"] = user.uname
    return RedirectResponse("/mobile/checkin", status_code=302)


@router.get("/mobile/logout")
def mobile_logout(request: Request):
    request.session.clear()
    return RedirectResponse("/mobile/login", status_code=302)


# ─────────────────────────────────────────────
# 打卡
# ─────────────────────────────────────────────

@router.get("/mobile/checkin", response_class=HTMLResponse)
def page_checkin(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return _redirect_login()

    today = _today()
    month = _month()

    today_record = db.query(CheckIn).filter(
        CheckIn.uid == user.id,
        CheckIn.checkin_date == today
    ).first()

    month_records = db.query(CheckIn).filter(
        CheckIn.uid == user.id,
        CheckIn.checkin_date.like(month + "%")
    ).order_by(CheckIn.checkin_date.desc()).all()

    days = len([r for r in month_records if r.clock_in])
    late = len([r for r in month_records if r.clock_in and _is_late(r.clock_in)])

    # Work days in month so far
    today_dt = date.today()
    first = today_dt.replace(day=1)
    work_days = sum(1 for d in range((today_dt - first).days + 1)
                    if (first + timedelta(days=d)).weekday() < 5)
    absent = max(0, work_days - days)

    return templates.TemplateResponse("mobile/checkin.html", {
        "request": request,
        "uname": user.uname,
        "today": datetime.now().strftime("%Y年%m月%d日"),
        "today_record": today_record,
        "month_records": month_records,
        "stats": {"days": days, "late": late, "absent": absent},
        "active_tab": "checkin",
    })


def _is_late(clock_in: str) -> bool:
    try:
        h, m = map(int, clock_in.split(":"))
        return (h, m) > (LATE_HOUR, LATE_MIN)
    except Exception:
        return False


@router.post("/api/mobile/checkin")
async def api_checkin(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return JSONResponse({"success": False, "message": "未登录"})

    body = await request.json()
    clock_type = body.get("type")  # "in" or "out"
    today = _today()
    now_time = datetime.now().strftime("%H:%M")

    record = db.query(CheckIn).filter(
        CheckIn.uid == user.id,
        CheckIn.checkin_date == today
    ).first()

    if clock_type == "in":
        if record and record.clock_in:
            return JSONResponse({"success": False, "message": "今日已打上班卡"})
        if not record:
            record = CheckIn(uid=user.id, uname=user.uname, checkin_date=today)
            db.add(record)
        record.clock_in = now_time
        db.commit()
        return JSONResponse({"success": True, "time": now_time})

    elif clock_type == "out":
        if not record or not record.clock_in:
            return JSONResponse({"success": False, "message": "请先打上班卡"})
        if record.clock_out:
            return JSONResponse({"success": False, "message": "今日已打下班卡"})
        record.clock_out = now_time
        db.commit()
        return JSONResponse({"success": True, "time": now_time})

    return JSONResponse({"success": False, "message": "参数错误"})


# ─────────────────────────────────────────────
# 日报
# ─────────────────────────────────────────────

@router.get("/mobile/dayreport", response_class=HTMLResponse)
def page_dayreport(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return _redirect_login()

    today = _today()
    submitted = db.query(DayReport).filter(
        DayReport.uid == user.id,
        DayReport.sdate == today
    ).first() is not None

    projects = db.query(Pro).filter(Pro.pstatus != "已完成").order_by(Pro.id.desc()).all()

    return templates.TemplateResponse("mobile/dayreport.html", {
        "request": request,
        "uname": user.uname,
        "today": datetime.now().strftime("%Y年%m月%d日"),
        "submitted": submitted,
        "projects": projects,
        "active_tab": "dayreport",
    })


@router.post("/api/mobile/dayreport")
async def api_dayreport(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return JSONResponse({"success": False, "message": "未登录"})

    body = await request.json()
    scontext = (body.get("scontext") or "").strip()
    pid = body.get("pid")
    if not scontext:
        return JSONResponse({"success": False, "message": "内容不能为空"})

    today = _today()
    existing = db.query(DayReport).filter(
        DayReport.uid == user.id,
        DayReport.sdate == today
    ).first()
    if existing:
        return JSONResponse({"success": False, "message": "今日日报已存在"})

    r = DayReport(
        uid=user.id,
        uname=user.uname,
        sdate=today,
        scontext=scontext,
        pid=int(pid) if pid else None
    )
    db.add(r)
    db.commit()
    return JSONResponse({"success": True})


# ─────────────────────────────────────────────
# 周报
# ─────────────────────────────────────────────

def _week_range() -> tuple[str, str]:
    today = date.today()
    monday = today - timedelta(days=today.weekday())
    sunday = monday + timedelta(days=6)
    return monday.strftime("%Y-%m-%d"), sunday.strftime("%Y-%m-%d")


@router.get("/mobile/weekreport", response_class=HTMLResponse)
def page_weekreport(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return _redirect_login()

    sdate, edate = _week_range()
    submitted = db.query(WeekReport).filter(
        WeekReport.uid == user.id,
        WeekReport.sdate == sdate
    ).first() is not None

    projects = db.query(Pro).filter(Pro.pstatus != "已完成").order_by(Pro.id.desc()).all()

    return templates.TemplateResponse("mobile/weekreport.html", {
        "request": request,
        "uname": user.uname,
        "week_range": f"{sdate} ~ {edate}",
        "submitted": submitted,
        "projects": projects,
        "active_tab": "weekreport",
    })


@router.post("/api/mobile/weekreport")
async def api_weekreport(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return JSONResponse({"success": False, "message": "未登录"})

    body = await request.json()
    scontext = (body.get("scontext") or "").strip()
    pid = body.get("pid")
    if not scontext:
        return JSONResponse({"success": False, "message": "内容不能为空"})

    sdate, edate = _week_range()
    existing = db.query(WeekReport).filter(
        WeekReport.uid == user.id,
        WeekReport.sdate == sdate
    ).first()
    if existing:
        return JSONResponse({"success": False, "message": "本周周报已存在"})

    r = WeekReport(
        uid=user.id,
        sdate=sdate,
        edate=edate,
        scontext=scontext,
        pid=int(pid) if pid else None
    )
    db.add(r)
    db.commit()
    return JSONResponse({"success": True})


# ─────────────────────────────────────────────
# 报销
# ─────────────────────────────────────────────

@router.get("/mobile/expense", response_class=HTMLResponse)
def page_expense(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user:
        return _redirect_login()

    records = db.query(FinExpense).filter(
        FinExpense.applicant == user.uname
    ).order_by(FinExpense.id.desc()).limit(10).all()

    today = date.today()
    return templates.TemplateResponse("mobile/expense.html", {
        "request": request,
        "uname": user.uname,
        "records": records,
        "default_month": today.strftime("%Y-%m"),
        "today_date": today.strftime("%Y-%m-%d"),
        "active_tab": "expense",
    })


@router.post("/api/mobile/expense")
async def api_expense(
    request: Request,
    db: Session = Depends(get_db),
    expense_month: str = FastForm(""),
    expense_date: str = FastForm(""),
    category: str = FastForm(""),
    currency: str = FastForm("CNY"),
    amount: str = FastForm(""),
    description: str = FastForm(""),
    remarks: str = FastForm(""),
    image: UploadFile = File(None),
):
    user = _user(request, db)
    if not user:
        return JSONResponse({"success": False, "message": "未登录"})

    if not amount or not description or not category:
        return JSONResponse({"success": False, "message": "金额、类别、描述不能为空"})

    image_path = None
    if image and image.filename:
        ext = os.path.splitext(image.filename)[1].lower()
        fname = uuid.uuid4().hex + ext
        fpath = os.path.join(UPLOAD_DIR, fname)
        with open(fpath, "wb") as f:
            shutil.copyfileobj(image.file, f)
        image_path = "/" + fpath.replace("\\", "/")

    now = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    r = FinExpense(
        expense_month=expense_month,
        expense_date=expense_date,
        category=category,
        currency=currency,
        amount=float(amount),
        description=description,
        applicant=user.uname,
        remarks=remarks,
        image_path=image_path,
        status="PENDING",
        created_at=now,
        updated_at=now,
    )
    db.add(r)
    db.commit()
    return JSONResponse({"success": True})
