package com.pmp.hrsrc.service;

import com.pmp.hrsrc.entity.LeaveApplication;
import java.util.List;

public interface LeaveApplicationService {
    LeaveApplication submit(LeaveApplication application);
    LeaveApplication approve(Long id, Long approverId, String approverName, String comment);
    LeaveApplication reject(Long id, Long approverId, String approverName, String comment);
    LeaveApplication cancel(Long id);
    LeaveApplication findById(Long id);
    List<LeaveApplication> findByEmployeeId(Long employeeId);
    List<LeaveApplication> findPendingByApproverId(Long approverId);
    List<LeaveApplication> findByDepartment(String department);
    List<LeaveApplication> findByStatus(String status);
} 