from sqlalchemy import Column, Integer, String, Text
from app.database import Base


class CheckIn(Base):
    __tablename__ = "t_checkin"

    id = Column(Integer, primary_key=True, autoincrement=True)
    uid = Column(Integer)
    uname = Column(String(100))
    checkin_date = Column(String(20))   # YYYY-MM-DD
    clock_in = Column(String(10))       # HH:MM
    clock_out = Column(String(10))      # HH:MM
    remark = Column(Text)
