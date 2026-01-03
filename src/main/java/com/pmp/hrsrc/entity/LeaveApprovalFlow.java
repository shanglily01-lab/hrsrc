package com.pmp.hrsrc.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "leave_approval_flows")
public class LeaveApprovalFlow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "leave_type")
    private String leave_type;

    @Column(name = "min_duration")
    private Float min_duration;

    @Column(name = "max_duration")
    private Float max_duration;

    @Column(name = "department")
    private String department;

    @Column(name = "approval_level")
    private Integer approval_level;

    @Column(name = "approver_role")
    private String approver_role;

    @Column(name = "created_at")
    private Date created_at;

    @Column(name = "updated_at")
    private Date updated_at;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLeave_type() {
        return leave_type;
    }

    public void setLeave_type(String leave_type) {
        this.leave_type = leave_type;
    }

    public Float getMin_duration() {
        return min_duration;
    }

    public void setMin_duration(Float min_duration) {
        this.min_duration = min_duration;
    }

    public Float getMax_duration() {
        return max_duration;
    }

    public void setMax_duration(Float max_duration) {
        this.max_duration = max_duration;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getApproval_level() {
        return approval_level;
    }

    public void setApproval_level(Integer approval_level) {
        this.approval_level = approval_level;
    }

    public String getApprover_role() {
        return approver_role;
    }

    public void setApprover_role(String approver_role) {
        this.approver_role = approver_role;
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