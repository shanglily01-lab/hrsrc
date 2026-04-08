from sqlalchemy import Column, Integer, String, Text
from app.database import Base


class HintCard(Base):
    __tablename__ = "t_hintcard"

    id = Column(Integer, primary_key=True)
    uid = Column(Integer)
    uname = Column(String(100))
    ampm = Column(String(10))
    hinttime = Column(String(50))


class HandBook(Base):
    __tablename__ = "t_handbook"

    id = Column(Integer, primary_key=True)
    segid = Column(Integer)
    books = Column(Text)


class Distri(Base):
    __tablename__ = "t_user_disp"

    id = Column(Integer, primary_key=True)
    uname = Column(String(200))
    tgname = Column(String(200))
    exp = Column(String(200))
    email = Column(String(200))
    utype = Column(String(100))
    skills = Column(Text)


class Zpacc(Base):
    __tablename__ = "t_zpacc"

    id = Column(Integer, primary_key=True)
    zh_name = Column(String(200))
    zh_pwd = Column(String(200))
    yx_name = Column(String(200))
    yx_pwd = Column(String(200))
    mark = Column(Text)


class Demark(Base):
    __tablename__ = "t_demark"

    id = Column(Integer, primary_key=True)
    sx = Column(String(200))
    ms = Column(Text)


class Announcement(Base):
    __tablename__ = "announcements"

    id = Column(Integer, primary_key=True)
    title = Column(String(300))
    content = Column(Text)
    publish_date = Column(String(50))
    publisher_id = Column(Integer)
    publisher_name = Column(String(100))
    status = Column(String(20))
