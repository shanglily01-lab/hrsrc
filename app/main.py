from fastapi import FastAPI
from fastapi.staticfiles import StaticFiles
from starlette.middleware.sessions import SessionMiddleware
from app.config import SECRET_KEY
from app.database import engine, Base
from app.routers import auth, pages, finance, hr, projects, leave, announcement, team

# 自动创建新表（不影响已有表）
import app.models.team  # noqa: F401 — 注册 Team/TeamMember 到 Base
Base.metadata.create_all(bind=engine, checkfirst=True)

app = FastAPI(title="分布式项目管理系统")

app.add_middleware(SessionMiddleware, secret_key=SECRET_KEY, max_age=900)

app.mount("/static", StaticFiles(directory="static"), name="static")
app.mount("/uploads", StaticFiles(directory="uploads"), name="uploads")

app.include_router(auth.router)
app.include_router(pages.router)
app.include_router(finance.router)
app.include_router(hr.router)
app.include_router(projects.router)
app.include_router(leave.router)
app.include_router(announcement.router)
app.include_router(team.router)
