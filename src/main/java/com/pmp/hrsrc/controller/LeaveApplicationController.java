package com.pmp.hrsrc.controller;

import com.pmp.hrsrc.entity.LeaveApplication;
import com.pmp.hrsrc.service.LeaveApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leave-applications")
@Api(tags = "请假申请接口")
public class LeaveApplicationController {

    @Autowired
    private LeaveApplicationService leaveApplicationService;

    @PostMapping
    @ApiOperation("提交请假申请")
    public ResponseEntity<?> submit(@RequestBody LeaveApplication la) {
        try {
            LeaveApplication result = leaveApplicationService.submit(la);
            return ResponseEntity.ok(createSuccessResponse(result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
        }
    }

    @PutMapping("/{id}/approve")
    @ApiOperation("审批通过")
    public ResponseEntity<?> approve(
            @PathVariable Long id,
            @RequestBody Map<String, Object> approvalInfo) {
        try {
            LeaveApplication result = leaveApplicationService.approve(
                    id,
                    Long.valueOf(approvalInfo.get("approverId").toString()),
                    approvalInfo.get("approverName").toString(),
                    approvalInfo.get("comment").toString()
            );
            return ResponseEntity.ok(createSuccessResponse(result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
        }
    }

    @PutMapping("/{id}/reject")
    @ApiOperation("审批拒绝")
    public ResponseEntity<?> reject(
            @PathVariable Long id,
            @RequestBody Map<String, Object> approvalInfo) {
        try {
            LeaveApplication result = leaveApplicationService.reject(
                    id,
                    Long.valueOf(approvalInfo.get("approverId").toString()),
                    approvalInfo.get("approverName").toString(),
                    approvalInfo.get("comment").toString()
            );
            return ResponseEntity.ok(createSuccessResponse(result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
        }
    }

    @PutMapping("/{id}/cancel")
    @ApiOperation("取消申请")
    public ResponseEntity<?> cancel(@PathVariable Long id) {
        try {
            LeaveApplication result = leaveApplicationService.cancel(id);
            return ResponseEntity.ok(createSuccessResponse(result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @ApiOperation("获取请假申请详情")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            LeaveApplication application = leaveApplicationService.findById(id);
            if (application == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(createSuccessResponse(application));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/employee/{employeeId}")
    @ApiOperation("获取员工的请假记录")
    public ResponseEntity<?> getByEmployeeId(@PathVariable Long employeeId) {
        try {
            List<LeaveApplication> applications = leaveApplicationService.findByEmployeeId(employeeId);
            return ResponseEntity.ok(createSuccessResponse(applications));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/pending/{approverId}")
    @ApiOperation("获取待审批的请假申请")
    public ResponseEntity<?> getPendingByApproverId(@PathVariable Long approverId) {
        try {
            List<LeaveApplication> applications = leaveApplicationService.findPendingByApproverId(approverId);
            return ResponseEntity.ok(createSuccessResponse(applications));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/department/{department}")
    @ApiOperation("获取部门的请假记录")
    public ResponseEntity<?> getByDepartment(@PathVariable String department) {
        try {
            List<LeaveApplication> applications = leaveApplicationService.findByDepartment(department);
            return ResponseEntity.ok(createSuccessResponse(applications));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/status/{status}")
    @ApiOperation("根据状态查询请假申请")
    public ResponseEntity<?> getByStatus(@PathVariable String status) {
        try {
            List<LeaveApplication> applications = leaveApplicationService.findByStatus(status);
            return ResponseEntity.ok(createSuccessResponse(applications));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
        }
    }

    private Map<String, Object> createSuccessResponse(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", data);
        return response;
    }

    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        return response;
    }
} 