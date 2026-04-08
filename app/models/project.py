from sqlalchemy import Column, Integer, String, Text
from app.database import Base


class Pro(Base):
    __tablename__ = "t_pro"

    id = Column(Integer, primary_key=True)
    pname = Column(String(200))
    pmark = Column(Text)


class Plan(Base):
    __tablename__ = "t_plan"

    id = Column(Integer, primary_key=True)
    pid = Column(Integer)
    vid = Column(Integer)
    ptext = Column(Text)
    sdate = Column(String(50))
    edate = Column(String(50))


class PlanDetail(Base):
    __tablename__ = "t_plandetail"

    id = Column(Integer, primary_key=True)
    planid = Column(Integer)
    dtext = Column(Text)
    ddate = Column(String(50))
    uid = Column(Integer)
    uname = Column(String(100))


class Version(Base):
    __tablename__ = "t_version"

    id = Column(Integer, primary_key=True)
    pid = Column(Integer)
    vers = Column(String(100))
    mark = Column(Text)


class Require(Base):
    __tablename__ = "t_req"

    id = Column(Integer, primary_key=True)
    model = Column(String(200))
    block = Column(String(200))
    item = Column(Text)
    ms = Column(Text)
    pid = Column(Integer)


class Reqmark(Base):
    __tablename__ = "t_reqmark"

    id = Column(Integer, primary_key=True)
    uid = Column(Integer)
    reqname = Column(String(200))
    reqdesc = Column(Text)
    reqlevel = Column(String(50))
    reqver = Column(String(100))
    reqmark = Column(Text)
    pid = Column(Integer)


class DayReport(Base):
    __tablename__ = "t_dayreport"

    id = Column(Integer, primary_key=True)
    uid = Column(Integer)
    sdate = Column(String(50))
    uname = Column(String(100))
    scontext = Column(Text)
    pid = Column(Integer)


class WeekReport(Base):
    __tablename__ = "t_weekreport"

    id = Column(Integer, primary_key=True)
    uid = Column(Integer)
    sdate = Column(String(50))
    edate = Column(String(50))
    scontext = Column(Text)
    pid = Column(Integer)


class MeetReport(Base):
    __tablename__ = "t_meetreport"

    id = Column(Integer, primary_key=True)
    mtype = Column(String(100))
    mdate = Column(String(50))
    mdesc = Column(Text)
    mzg = Column(String(200))
    pid = Column(Integer)
