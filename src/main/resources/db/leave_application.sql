-- 创建请假申请表
CREATE TABLE IF NOT EXISTS `leave_applications` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `employee_id` BIGINT NOT NULL COMMENT '员工ID',
    `employee_name` VARCHAR(100) NOT NULL COMMENT '员工姓名',
    `department` VARCHAR(100) NOT NULL COMMENT '部门',
    `leave_type` VARCHAR(20) NOT NULL COMMENT '请假类型：ANNUAL-年假，SICK-病假，PERSONAL-事假，MARRIAGE-婚假，MATERNITY-产假，OTHER-其他',
    `start_date` DATETIME NOT NULL COMMENT '开始时间',
    `end_date` DATETIME NOT NULL COMMENT '结束时间',
    `duration` FLOAT NOT NULL COMMENT '请假天数',
    `reason` TEXT COMMENT '请假原因',
    `status` VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT '状态：PENDING-待审批，APPROVED-已批准，REJECTED-已拒绝，CANCELLED-已取消',
    `approver_id` BIGINT COMMENT '审批人ID',
    `approver_name` VARCHAR(100) COMMENT '审批人姓名',
    `approval_time` DATETIME COMMENT '审批时间',
    `approval_comment` TEXT COMMENT '审批意见',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `idx_employee_id` (`employee_id`),
    INDEX `idx_department` (`department`),
    INDEX `idx_status` (`status`),
    INDEX `idx_start_date` (`start_date`),
    INDEX `idx_approver_id` (`approver_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='请假申请表';

-- 创建审批流程配置表
CREATE TABLE IF NOT EXISTS `leave_approval_flows` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `leave_type` VARCHAR(20) NOT NULL COMMENT '请假类型',
    `min_duration` FLOAT NOT NULL DEFAULT 0 COMMENT '最小请假天数',
    `max_duration` FLOAT COMMENT '最大请假天数',
    `department` VARCHAR(100) COMMENT '部门（为空表示适用于所有部门）',
    `approval_level` INT NOT NULL COMMENT '审批级别',
    `approver_role` VARCHAR(50) NOT NULL COMMENT '审批人角色',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `idx_leave_type` (`leave_type`),
    INDEX `idx_department` (`department`),
    INDEX `idx_approval_level` (`approval_level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='请假审批流程配置表';

-- 创建请假审批记录表
CREATE TABLE IF NOT EXISTS `leave_approval_records` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `leave_application_id` BIGINT NOT NULL COMMENT '请假申请ID',
    `approval_level` INT NOT NULL COMMENT '审批级别',
    `approver_id` BIGINT NOT NULL COMMENT '审批人ID',
    `approver_name` VARCHAR(100) NOT NULL COMMENT '审批人姓名',
    `approver_role` VARCHAR(50) NOT NULL COMMENT '审批人角色',
    `status` VARCHAR(20) NOT NULL COMMENT '审批状态：PENDING-待审批，APPROVED-已批准，REJECTED-已拒绝',
    `comment` TEXT COMMENT '审批意见',
    `approval_time` DATETIME COMMENT '审批时间',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `idx_leave_application_id` (`leave_application_id`),
    INDEX `idx_approver_id` (`approver_id`),
    INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='请假审批记录表';

-- 插入一些示例数据
INSERT INTO `leave_applications` 
(`employee_id`, `employee_name`, `department`, `leave_type`, `start_date`, `end_date`, `duration`, `reason`, `status`, `approver_id`, `approver_name`, `approval_time`, `approval_comment`) 
VALUES
(1, '张三', '技术部', 'ANNUAL', '2024-04-15 09:00:00', '2024-04-16 18:00:00', 2, '年假休息', 'APPROVED', 100, '李经理', '2024-04-10 14:30:00', '同意'),
(2, '李四', '人事部', 'SICK', '2024-04-12 09:00:00', '2024-04-12 18:00:00', 1, '感冒发烧', 'APPROVED', 101, '王经理', '2024-04-11 10:20:00', '注意休息'),
(3, '王五', '市场部', 'PERSONAL', '2024-04-18 09:00:00', '2024-04-18 18:00:00', 1, '家中有事', 'PENDING', NULL, NULL, NULL, NULL),
(4, '赵六', '财务部', 'MARRIAGE', '2024-05-01 09:00:00', '2024-05-07 18:00:00', 7, '结婚', 'APPROVED', 102, '张经理', '2024-04-10 16:45:00', '祝福'),
(5, '孙七', '技术部', 'ANNUAL', '2024-04-20 09:00:00', '2024-04-20 18:00:00', 1, '休息', 'REJECTED', 100, '李经理', '2024-04-11 11:30:00', '当前项目较紧，建议改期'),
(6, '周八', '市场部', 'SICK', '2024-04-13 09:00:00', '2024-04-14 18:00:00', 2, '发烧', 'CANCELLED', NULL, NULL, NULL, NULL);

-- 插入审批流程配置示例数据
INSERT INTO `leave_approval_flows` 
(`leave_type`, `min_duration`, `max_duration`, `department`, `approval_level`, `approver_role`) 
VALUES
-- 年假审批流程
('ANNUAL', 0, 3, NULL, 1, 'DIRECT_MANAGER'),
('ANNUAL', 3, 7, NULL, 1, 'DIRECT_MANAGER'),
('ANNUAL', 3, 7, NULL, 2, 'HR_MANAGER'),
('ANNUAL', 7, NULL, NULL, 1, 'DIRECT_MANAGER'),
('ANNUAL', 7, NULL, NULL, 2, 'HR_MANAGER'),
('ANNUAL', 7, NULL, NULL, 3, 'GENERAL_MANAGER'),

-- 病假审批流程
('SICK', 0, 3, NULL, 1, 'DIRECT_MANAGER'),
('SICK', 3, 7, NULL, 1, 'DIRECT_MANAGER'),
('SICK', 3, 7, NULL, 2, 'HR_MANAGER'),
('SICK', 7, NULL, NULL, 1, 'DIRECT_MANAGER'),
('SICK', 7, NULL, NULL, 2, 'HR_MANAGER'),
('SICK', 7, NULL, NULL, 3, 'GENERAL_MANAGER'),

-- 事假审批流程
('PERSONAL', 0, 1, NULL, 1, 'DIRECT_MANAGER'),
('PERSONAL', 1, 3, NULL, 1, 'DIRECT_MANAGER'),
('PERSONAL', 1, 3, NULL, 2, 'HR_MANAGER'),
('PERSONAL', 3, NULL, NULL, 1, 'DIRECT_MANAGER'),
('PERSONAL', 3, NULL, NULL, 2, 'HR_MANAGER'),
('PERSONAL', 3, NULL, NULL, 3, 'GENERAL_MANAGER'),

-- 婚假审批流程
('MARRIAGE', 0, NULL, NULL, 1, 'DIRECT_MANAGER'),
('MARRIAGE', 0, NULL, NULL, 2, 'HR_MANAGER'),

-- 产假审批流程
('MATERNITY', 0, NULL, NULL, 1, 'DIRECT_MANAGER'),
('MATERNITY', 0, NULL, NULL, 2, 'HR_MANAGER'),
('MATERNITY', 0, NULL, NULL, 3, 'GENERAL_MANAGER'); 