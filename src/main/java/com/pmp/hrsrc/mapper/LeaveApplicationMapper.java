package com.pmp.hrsrc.mapper;

import com.pmp.hrsrc.entity.LeaveApplication;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LeaveApplicationMapper {
    
    @Select("SELECT * FROM leave_applications WHERE employee_id = #{employeeId} ORDER BY created_at DESC")
    List<LeaveApplication> findByEmployeeId(Long employeeId);

    @Select("SELECT * FROM leave_applications  ORDER BY created_at DESC")
    List<LeaveApplication> findPendingByApproverId(Long approverId);

    @Select("SELECT * FROM leave_applications WHERE id = #{id}")
    LeaveApplication findById(Long id);

    @Insert("INSERT INTO leave_applications (employee_id, employee_name, department, leave_type, " +
            "start_date, end_date, duration, reason, status, created_at, updated_at) " +
            "VALUES (#{employee_id}, #{employee_name}, #{department}, #{leave_type}, " +
            "#{start_date}, #{end_date}, #{duration}, #{reason}, #{status}, #{created_at}, #{updated_at})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(LeaveApplication leaveApplication);

    @Update("UPDATE leave_applications SET status = #{status}, approver_id = #{approver_id}, " +
            "approver_name = #{approver_name}, approval_time = #{approval_time}, " +
            "approval_comment = #{approval_comment}, updated_at = #{updated_at} WHERE id = #{id}")
    int updateApproval(LeaveApplication leaveApplication);

    @Update("UPDATE leave_applications SET status = 'CANCELLED', updated_at = #{updated_at} WHERE id = #{id}")
    int cancel(LeaveApplication leaveApplication);

    @Select("SELECT * FROM leave_applications WHERE department = #{department} ORDER BY created_at DESC")
    List<LeaveApplication> findByDepartment(String department);

    @Select("SELECT * FROM leave_applications WHERE status = #{status} ORDER BY created_at DESC")
    List<LeaveApplication> findByStatus(String status);
} 