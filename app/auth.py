from __future__ import annotations

import bcrypt
from fastapi import Request, Depends, HTTPException
from fastapi.responses import RedirectResponse
from sqlalchemy.orm import Session
from app.database import get_db
from app.models.user import User


def verify_password(plain: str, hashed: str) -> bool:
    try:
        return bcrypt.checkpw(plain.encode(), hashed.encode())
    except Exception:
        return False


def get_current_user(request: Request, db: Session = Depends(get_db)) -> User:
    user_id = request.session.get("user_id")
    if not user_id:
        raise HTTPException(status_code=302, headers={"Location": "/login"})
    user = db.query(User).filter(User.id == user_id).first()
    if not user:
        raise HTTPException(status_code=302, headers={"Location": "/login"})
    return user


def require_login(request: Request, db: Session = Depends(get_db)) -> User:
    user_id = request.session.get("user_id")
    if not user_id:
        return None
    return db.query(User).filter(User.id == user_id).first()


def is_admin(user: User) -> bool:
    return user is not None and (user.id in (100,) or user.uname == "admin")


# Finance-only users (not full admin, but can access all Finance menu & routes)
_FINANCE_USER_IDS = {139}  # Jack

def is_finance(user: User) -> bool:
    return is_admin(user) or (user is not None and user.id in _FINANCE_USER_IDS)


def is_super_admin(user: User) -> bool:
    return user is not None and user.uname == "admin"


def accessible_pids(user: User, db: Session) -> set[int] | None:
    """Return the set of project IDs the user is authorized to access.
    Returns None if the user is admin (meaning all projects are accessible).
    Chain: UserRole.uid -> RoleRight.rid -> Right.id -> Right.pid
    """
    if is_admin(user):
        return None  # None = unrestricted

    from app.models.user import UserRole, RoleRight, Right
    from sqlalchemy import text

    rows = db.execute(text("""
        SELECT DISTINCT r.pid
        FROM t_right r
        JOIN t_role_right rr ON r.id = rr.rgid
        JOIN t_user_role ur  ON rr.rid = ur.rid
        WHERE ur.uid = :uid AND r.pid IS NOT NULL
    """), {"uid": user.id}).fetchall()

    return {row[0] for row in rows}
