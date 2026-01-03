package com.pmp.hrsrc.service.impl;

import com.pmp.hrsrc.entity.LeaveApplication;
import com.pmp.hrsrc.mapper.LeaveApplicationMapper;
import com.pmp.hrsrc.service.LeaveApplicationService;
import com.pmp.hrsrc.mapper.LeaveApprovalRecordMapper;
import com.pmp.hrsrc.entity.LeaveApprovalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class LeaveApplicationServiceImpl implements LeaveApplicationService {

    @Autowired
    private LeaveApplicationMapper leaveApplicationMapper;

    @Autowired
    private LeaveApprovalRecordMapper leaveApprovalRecordMapper;

    @Override
    @Transactional
    public LeaveApplication submit(LeaveApplication application) {
        // 设置基本信息
        application.setStatus("PENDING");
        
        // 如果员工信息为空，设置默认值
        if (application.getEmployee_id() == null) {
            application.setEmployee_id(1L); // 默认员工ID
        }
        if (application.getEmployee_name() == null || application.getEmployee_name() .trim().isEmpty()) {
            application.setEmployee_name("测试员工"); // 默认员工姓名
        }
        if (application.getDepartment() == null || application.getDepartment().trim().isEmpty()) {
            application.setDepartment("技术部"); // 默认部门
        }
        
        // 设置时间戳
        Date now = new Date();
        if (application.getCreated_at() == null) {
            application.setCreated_at((now));
        }
        if (application.getUpdated_at() == null) {
            application.setUpdated_at(now);
        }

        // 确保请假类型不为空
        if (application.getLeave_type() == null || application.getLeave_type().trim().isEmpty()) {
            throw new RuntimeException("请假类型不能为空");
        }

        // 确保开始时间和结束时间不为空
        if (application.getStart_date() == null || application.getEnd_date() == null) {
            throw new RuntimeException("请假时间不能为空");
        }

        // 确保请假天数不为空
        if (application.getDuration() == null || application.getDuration() <= 0) {
            throw new RuntimeException("请假天数必须大于0");
        }

        // 确保请假原因不为空
        if (application.getReason() == null || application.getReason().trim().isEmpty()) {
            throw new RuntimeException("请假原因不能为空");
        }

        leaveApplicationMapper.insert(application);
        return application;
    }

    @Override
    @Transactional
    public LeaveApplication approve(Long id, Long approverId, String approverName, String comment) {
        LeaveApplication application = leaveApplicationMapper.findById(id);
        if (application == null) {
            throw new RuntimeException("请假申请不存在");
        }
        if (!"PENDING".equals(application.getStatus())) {
            throw new RuntimeException("该申请已处理");
        }
        // 更新审批记录
        System.out.println("id="+id);
        List<LeaveApprovalRecord> records = leaveApprovalRecordMapper.findByLeaveApplicationId(id);
        if (records.isEmpty()) throw new RuntimeException("审批记录不存在");
        LeaveApprovalRecord record = records.get(0); // 只有一级审批
        record.setStatus("APPROVED");
        record.setComment(comment);
        record.setApproval_time(new Date());
        record.setUpdated_at(new Date());
        leaveApprovalRecordMapper.update(record);
        // 更新主表
        application.setStatus("APPROVED");
        application.setApprover_id(approverId);
        application.setApprover_name(approverName);
        application.setApproval_comment(comment);
        application.setApproval_time(new Date());
        application.setUpdated_at(new Date());
        leaveApplicationMapper.updateApproval(application);
        return application;
    }

    @Override
    @Transactional
    public LeaveApplication reject(Long id, Long approverId, String approverName, String comment) {
        LeaveApplication application = leaveApplicationMapper.findById(id);
        if (application == null) {
            throw new RuntimeException("请假申请不存在");
        }
        if (!"PENDING".equals(application.getStatus())) {
            throw new RuntimeException("该申请已处理");
        }
        // 更新审批记录
        List<LeaveApprovalRecord> records = leaveApprovalRecordMapper.findByLeaveApplicationId(id);
        if (records.isEmpty()) throw new RuntimeException("审批记录不存在");
        LeaveApprovalRecord record = records.get(0); // 只有一级审批
        record.setStatus("REJECTED");
        record.setComment(comment);
        record.setApproval_time(new Date());
        record.setUpdated_at(new Date());
        leaveApprovalRecordMapper.update(record);
        // 更新主表
        application.setStatus("REJECTED");
        application.setApprover_id(approverId);
        application.setApprover_name(approverName);
        application.setApproval_comment(comment);
        application.setApproval_time(new Date());
        application.setUpdated_at(new Date());
        leaveApplicationMapper.updateApproval(application);
        return application;
    }

    @Override
    @Transactional
    public LeaveApplication cancel(Long id) {
        LeaveApplication application = leaveApplicationMapper.findById(id);
        if (application == null) {
            throw new RuntimeException("请假申请不存在");
        }
        if (!"PENDING".equals(application.getStatus())) {
            throw new RuntimeException("只能取消待审批的申请");
        }

        application.setUpdated_at(new Date());
        leaveApplicationMapper.cancel(application);
        return application;
    }

    @Override
    public LeaveApplication findById(Long id) {

        return leaveApplicationMapper.findById(id);
    }

    @Override
    public List<LeaveApplication> findByEmployeeId(Long employeeId) {
        return leaveApplicationMapper.findByEmployeeId(employeeId);

    }

    @Override
    public List<LeaveApplication> findPendingByApproverId(Long approverId) {
        List<LeaveApplication> applications = leaveApplicationMapper.findPendingByApproverId(approverId);
        
        // 为每个待审批的申请创建审批记录
        for (LeaveApplication application : applications) {
            // 检查是否已存在审批记录
            List<LeaveApprovalRecord> existingRecords = leaveApprovalRecordMapper.findByLeaveApplicationId(application.getId());
            if (existingRecords.isEmpty()) {
                // 创建新的审批记录
                LeaveApprovalRecord record = new LeaveApprovalRecord();
                record.setLeave_application_id(application.getId());
                record.setApproval_level(1);
                record.setApprover_id(approverId);
                record.setApprover_name("测试审批人"); // 这里应该从用户服务获取审批人姓名
                record.setApprover_role("DIRECT_MANAGER");
                record.setStatus("PENDING");
                record.setCreated_at(new Date());
                record.setUpdated_at(new Date());
                leaveApprovalRecordMapper.insert(record);
            }
        }
        
        return applications;
    }

    @Override
    public List<LeaveApplication> findByDepartment(String department) {
        return leaveApplicationMapper.findByDepartment(department);
    }

    @Override
    public List<LeaveApplication> findByStatus(String status) {
        return leaveApplicationMapper.findByStatus(status);
    }
} 