from sqlalchemy import Column, Integer, String, Text, DateTime
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
    # Java 原表为驼峰列名；另有空的 snake_case 列是后来误加的，不能用
    publish_date = Column("publishDate", DateTime)
    publisher_id = Column("publisherId", Integer)
    publisher_name = Column("publisherName", String(100))
    status = Column(String(20))
