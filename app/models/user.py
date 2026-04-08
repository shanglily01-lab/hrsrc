from sqlalchemy import Column, Integer, String, Text, DateTime
from app.database import Base


class User(Base):
    __tablename__ = "t_user"

    id = Column(Integer, primary_key=True)
    uname = Column(String(100))
    upass = Column(String(200))
    lodate = Column(String(50))
    ip = Column(String(50))


class Profile(Base):
    __tablename__ = "t_profile"

    id = Column(Integer, primary_key=True)
    uid = Column(Integer)
    uname = Column(String(100))
    email = Column(String(200))
    tg = Column(String(100))
    whatsapp = Column(String(100))
    phone = Column(String(50))
    addr = Column(Text)
    saddr = Column(Text)      # TRC20 wallet address (text)
    saddr_img = Column(Text)  # TRC20 QR code image path
    vaddr = Column(Text)      # ERC20 wallet address (text)
    vaddr_img = Column(Text)  # ERC20 QR code image path
    udate = Column(String(50))


class Role(Base):
    __tablename__ = "t_role"

    id = Column(Integer, primary_key=True)
    rname = Column(String(100))


class Right(Base):
    __tablename__ = "t_right"

    id = Column(Integer, primary_key=True)
    rname = Column(String(100))
    pid = Column(Integer)


class UserRole(Base):
    __tablename__ = "t_user_role"

    id = Column(Integer, primary_key=True)
    uid = Column(Integer)
    rid = Column(Integer)


class RoleRight(Base):
    __tablename__ = "t_role_right"

    id = Column(Integer, primary_key=True)
    rid = Column(Integer)
    rgid = Column(Integer)


class Secrete(Base):
    __tablename__ = "t_sec"

    id = Column(Integer, primary_key=True)
    uid = Column(Integer)
    uname = Column(String(100))
    scode = Column(String(200))
    sdate = Column(String(50))
