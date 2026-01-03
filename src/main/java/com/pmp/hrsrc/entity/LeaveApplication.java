package com.pmp.hrsrc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "leave_applications")
public class LeaveApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id")
    private Long employee_id;

    @Column(name = "employee_name")
    private String employee_name;

    @Column(name = "department")
    private String department;

    @Column(name = "leave_type")
    private String leave_type;  // 年假、病假、事假等

    @Column(name = "start_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start_date;

    @Column(name = "end_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end_date;

    @Column(name = "duration")
    private Float duration;  // 请假天数

    @Column(columnDefinition = "TEXT")
    private String reason;

    private String status;  // PENDING-待审批, APPROVED-已批准, REJECTED-已拒绝, CANCELLED-已取消

    @Column(name = "approver_id")
    private Long approver_id;

    @Column(name = "approver_name")
    private String approver_name;

    @Column(name = "approval_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date approval_time;

    @Column(name = "approval_comment")
    private String approval_comment;

    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created_at;

    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updated_at;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Long employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLeave_type() {
        return leave_type;
    }

    public void setLeave_type(String leave_type) {
        this.leave_type = leave_type;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getApprover_id() {
        return approver_id;
    }

    public void setApprover_id(Long approver_id) {
        this.approver_id = approver_id;
    }

    public String getApprover_name() {
        return approver_name;
    }

    public void setApprover_name(String approver_name) {
        this.approver_name = approver_name;
    }

    public Date getApproval_time() {
        return approval_time;
    }

    public void setApproval_time(Date approval_time) {
        this.approval_time = approval_time;
    }

    public String getApproval_comment() {
        return approval_comment;
    }

    public void setApproval_comment(String approval_comment) {
        this.approval_comment = approval_comment;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}