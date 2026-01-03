package com.pmp.hrsrc.mapper;

import com.pmp.hrsrc.entity.LeaveApprovalRecord;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface LeaveApprovalRecordMapper {
    @Select("SELECT * FROM leave_approval_records WHERE leave_application_id = #{leaveApplicationId} ORDER BY approval_level")
    List<LeaveApprovalRecord> findByLeaveApplicationId(Long leaveApplicationId);

    @Insert("INSERT INTO leave_approval_records (leave_application_id, approval_level, approver_id, approver_name, approver_role, status, comment, approval_time, created_at, updated_at) " +
            "VALUES (#{leave_application_id}, #{approval_level}, #{approver_id}, #{approver_name}, #{approver_role}, #{status}, #{comment}, #{approval_time}, #{created_at}, #{updated_at})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(LeaveApprovalRecord record);

    @Update("UPDATE leave_approval_records SET status = #{status}, comment = #{comment}, approval_time = #{approval_time}, updated_at = #{updated_at} WHERE id = #{id}")
    int update(LeaveApprovalRecord record);

    @Select("SELECT * FROM leave_approval_records WHERE approver_id = #{approverId} AND status = 'PENDING'")
    List<LeaveApprovalRecord> findPendingByApproverId(Long approverId);
} 