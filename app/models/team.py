from sqlalchemy import Column, Integer, String, Text
from app.database import Base


class Team(Base):
    __tablename__ = "t_team"

    id = Column(Integer, primary_key=True, autoincrement=True)
    name = Column(String(100))
    description = Column(Text)


class TeamMember(Base):
    __tablename__ = "t_team_member"

    id = Column(Integer, primary_key=True, autoincrement=True)
    team_id = Column(Integer)
    user_id = Column(Integer)
