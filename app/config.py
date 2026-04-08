import os
from datetime import datetime, date, timezone, timedelta
from dotenv import load_dotenv

CST = timezone(timedelta(hours=8))


def now_cst() -> datetime:
    """返回 UTC+8 当前时间（服务器/DB 均为 UTC，统一用此函数）"""
    return datetime.now(tz=CST).replace(tzinfo=None)


def today_cst() -> date:
    """返回 UTC+8 当前日期"""
    return datetime.now(tz=CST).date()

load_dotenv()

DB_HOST = os.getenv("DB_HOST", "localhost")
DB_PORT = os.getenv("DB_PORT", "3306")
DB_NAME = os.getenv("DB_NAME", "ty_hrsrc")
DB_USER = os.getenv("DB_USER", "root")
DB_PASS = os.getenv("DB_PASS", "")
SECRET_KEY = os.getenv("SECRET_KEY", "changeme")
ANTHROPIC_API_KEY = os.getenv("ANTHROPIC_API_KEY", "")
GEMINI_API_KEY = os.getenv("GEMINI_API_KEY", "")
SERVER_PORT = int(os.getenv("SERVER_PORT", "8090"))

DATABASE_URL = (
    f"mysql+pymysql://{DB_USER}:{DB_PASS}@{DB_HOST}:{DB_PORT}/{DB_NAME}"
    "?charset=utf8mb4"
)
