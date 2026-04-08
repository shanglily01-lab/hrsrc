from fastapi import APIRouter, Request, Depends
from fastapi.responses import HTMLResponse, RedirectResponse
from fastapi.templating import Jinja2Templates
from sqlalchemy.orm import Session
from app.database import get_db
from app.models.user import User
from app.models.team import Team, TeamMember
from app.auth import is_super_admin

router = APIRouter()
templates = Jinja2Templates(directory="app/templates")


def _user(request: Request, db: Session) -> User | None:
    uid = request.session.get("user_id")
    if not uid:
        return None
    return db.query(User).filter(User.id == uid).first()


def ok(data=None, message="success"):
    return {"success": True, "message": message, "data": data}


def err(message):
    return {"success": False, "message": message}


# ─── 团队页面 ───────────────────────────────────────────────

@router.get("/team", response_class=HTMLResponse)
def team_page(request: Request, db: Session = Depends(get_db)):
    user = _user(request, db)
    if not user or not is_super_admin(user):
        return HTMLResponse("无权访问", 403)

    teams = db.query(Team).order_by(Team.id).all()
    all_users = db.query(User).order_by(User.id).all()
    members = db.query(TeamMember).all()

    # 构建 team_id -> [user_id, ...] 映射
    member_map: dict[int, list[int]] = {}
    for m in members:
        member_map.setdefault(m.team_id, []).append(m.user_id)

    # 构建 user_id -> uname 映射
    user_map = {u.id: u.uname for u in all_users}

    # 构建 user_id -> team_ids 映射（哪些用户已分配团队）
    assigned = {m.user_id for m in members}

    return templates.TemplateResponse("team.html", {
        "request": request,
        "teams": teams,
        "all_users": all_users,
        "member_map": member_map,
        "user_map": user_map,
        "assigned": assigned,
    })


# ─── 团队 CRUD ──────────────────────────────────────────────

@router.post("/api/team")
async def create_team(request: Request, db: Session = Depends(get_db)):
    body = await request.json()
    name = (body.get("name") or "").strip()
    if not name:
        return err("团队名称不能为空")
    db.add(Team(name=name, description=body.get("description", "")))
    db.commit()
    return ok()


@router.put("/api/team/{tid}")
async def update_team(tid: int, request: Request, db: Session = Depends(get_db)):
    body = await request.json()
    t = db.query(Team).filter(Team.id == tid).first()
    if t:
        t.name = (body.get("name") or "").strip() or t.name
        t.description = body.get("description", t.description)
        db.commit()
    return ok()


@router.delete("/api/team/{tid}")
def delete_team(tid: int, db: Session = Depends(get_db)):
    db.query(TeamMember).filter(TeamMember.team_id == tid).delete()
    db.query(Team).filter(Team.id == tid).delete()
    db.commit()
    return ok()


# ─── 团队成员 ───────────────────────────────────────────────

@router.post("/api/team/{tid}/member")
async def add_member(tid: int, request: Request, db: Session = Depends(get_db)):
    body = await request.json()
    user_id = body.get("user_id")
    if not user_id:
        return err("缺少 user_id")
    exists = db.query(TeamMember).filter(TeamMember.team_id == tid, TeamMember.user_id == user_id).first()
    if exists:
        return err("该成员已在团队中")
    db.add(TeamMember(team_id=tid, user_id=user_id))
    db.commit()
    return ok()


@router.delete("/api/team/{tid}/member/{uid}")
def remove_member(tid: int, uid: int, db: Session = Depends(get_db)):
    db.query(TeamMember).filter(TeamMember.team_id == tid, TeamMember.user_id == uid).delete()
    db.commit()
    return ok()
