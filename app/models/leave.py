from sqlalchemy import Column, Integer, String, Text
from app.database import Base


class LeaveApplication(Base):
    __tablename__ = "leave_applications"

    id = Column(Integer, primary_key=True)
    employee_id = Column(Integer)
    employee_name = Column(String(100))
    department = Column(String(200))
    leave_type = Column(String(50))
    start_date = Column(String(50))
    end_date = Column(String(50))
    duration = Column(String(50))
    reason = Column(Text)
    status = Column(String(20))
    approver_id = Column(Integer)
    approver_name = Column(String(100))
    approval_time = Column(String(50))
    approval_comment = Column(Text)
    created_at = Column(String(50))
    updated_at = Column(String(50))
