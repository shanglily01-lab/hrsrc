import random
import string
import io
from PIL import Image, ImageDraw, ImageFont
from fastapi import APIRouter, Request, Depends, Form
from fastapi.responses import HTMLResponse, RedirectResponse, StreamingResponse
from fastapi.templating import Jinja2Templates
from sqlalchemy.orm import Session
from sqlalchemy import text
from app.database import get_db
from app.models.user import User
from app.auth import verify_password

router = APIRouter()
templates = Jinja2Templates(directory="app/templates")


@router.get("/login", response_class=HTMLResponse)
def login_page(request: Request):
    return templates.TemplateResponse("login.html", {"request": request})


@router.get("/api/captcha")
def captcha(request: Request):
    code = "".join(random.choices(string.ascii_uppercase + string.digits, k=4))
    request.session["captcha"] = code.upper()

    W, H = 150, 46
    img = Image.new("RGB", (W, H), color=(245, 245, 245))
    draw = ImageDraw.Draw(img)

    # 尝试加载 Windows 系统字体
    font = None
    for font_path in [
        r"C:\Windows\Fonts\arial.ttf",
        r"C:\Windows\Fonts\consola.ttf",
        r"C:\Windows\Fonts\cour.ttf",
    ]:
        try:
            font = ImageFont.truetype(font_path, 32)
            break
        except (IOError, OSError):
            continue
    if font is None:
        try:
            font = ImageFont.load_default(size=32)
        except TypeError:
            font = ImageFont.load_default()

    # 干扰线
    for _ in range(5):
        draw.line(
            [random.randint(0, W), random.randint(0, H),
             random.randint(0, W), random.randint(0, H)],
            fill=(190, 190, 190), width=1,
        )
    # 字符
    for i, ch in enumerate(code):
        x = 6 + i * 34 + random.randint(-2, 2)
        y = random.randint(4, 8)
        draw.text((x, y), ch, fill=(
            random.randint(20, 120),
            random.randint(20, 120),
            random.randint(20, 120),
        ), font=font)

    buf = io.BytesIO()
    img.save(buf, format="PNG")
    buf.seek(0)
    return StreamingResponse(buf, media_type="image/png")


@router.post("/api/login")
async def do_login(
    request: Request,
    username: str = Form(...),
    password: str = Form(...),
    captchaCode: str = Form(...),
    db: Session = Depends(get_db),
):
    session_captcha = request.session.get("captcha", "")
    if captchaCode.upper() != session_captcha.upper():
        return {"success": False, "message": "验证码错误"}

    user = db.query(User).filter(User.uname == username).first()
    if not user:
        return {"success": False, "message": "用户名或密码错误"}

    if not verify_password(password, user.upass):
        return {"success": False, "message": "用户名或密码错误"}

    request.session["user_id"] = user.id
    request.session["uname"] = user.uname

    # 更新登录时间和 IP
    try:
        ip = request.headers.get("X-Forwarded-For", request.client.host)
        from datetime import datetime
        now = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
        db.execute(
            text("UPDATE t_user SET lodate=:d, ip=:ip WHERE id=:id"),
            {"d": now, "ip": ip, "id": user.id}
        )
        db.commit()
    except Exception:
        pass

    return {"success": True, "message": "登录成功"}


@router.get("/logout")
def logout(request: Request):
    request.session.clear()
    return RedirectResponse(url="/login", status_code=302)
