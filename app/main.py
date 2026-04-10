import os
from fastapi import FastAPI
from fastapi.staticfiles import StaticFiles
from starlette.middleware.sessions import SessionMiddleware
from app.config import SECRET_KEY
from app.database import engine, Base

# 确保上传目录存在
for _d in ("uploads/expenses", "uploads/fundusage", "uploads/images", "static"):
    os.makedirs(_d, exist_ok=True)

# 补充数据库列（如已存在则忽略错误）
def _add_column_if_missing(engine, table, col_def):
    try:
        with engine.connect() as conn:
            conn.execute(__import__("sqlalchemy").text(f"ALTER TABLE {table} ADD COLUMN {col_def}"))
            conn.commit()
    except Exception:
        pass
from app.routers import auth, pages, finance, hr, projects, leave, announcement, team
from app.routers import mobile

# 自动创建新表（不影响已有表）
import app.models.team    # noqa: F401
import app.models.mobile  # noqa: F401 — 注册 CheckIn 到 Base
Base.metadata.create_all(bind=engine, checkfirst=True)

_add_column_if_missing(engine, "fin_fund_usage", "exchange_rate DECIMAL(10,4) DEFAULT NULL")

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
app.include_router(mobile.router)
