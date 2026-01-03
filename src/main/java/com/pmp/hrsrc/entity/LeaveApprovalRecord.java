package com.pmp.hrsrc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "leave_approval_records")
public class LeaveApprovalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "leave_application_id")
    private Long leave_application_id;

    @Column(name = "approval_level")
    private Integer approval_level;

    @Column(name = "approver_id")
    private Long approver_id;

    @Column(name = "approver_name")
    private String approver_name;

    @Column(name = "approver_role")
    private String approver_role;

    @Column(name = "status")
    private String status;

    @Column(name = "comment")
    private String comment;

    @Column(name = "approval_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date approval_time;

    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created_at;

    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updated_at;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLeave_application_id() {
        return leave_application_id;
    }

    public void setLeave_application_id(Long leave_application_id) {
        this.leave_application_id = leave_application_id;
    }

    public Integer getApproval_level() {
        return approval_level;
    }

    public void setApproval_level(Integer approval_level) {
        this.approval_level = approval_level;
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

    public String getApprover_role() {
        return approver_role;
    }

    public void setApprover_role(String approver_role) {
        this.approver_role = approver_role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getApproval_time() {
        return approval_time;
    }

    public void setApproval_time(Date approval_time) {
        this.approval_time = approval_time;
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