-- 创建公告表
CREATE TABLE IF NOT EXISTS `announcements` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(255) NOT NULL COMMENT '公告标题',
    `content` TEXT COMMENT '公告内容',
    `publish_date` DATETIME COMMENT '发布时间',
    `publisher_id` BIGINT COMMENT '发布人ID',
    `publisher_name` VARCHAR(100) COMMENT '发布人姓名',
    `status` VARCHAR(20) DEFAULT 'DRAFT' COMMENT '状态：DRAFT-草稿，PUBLISHED-已发布，ARCHIVED-已归档',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `idx_status` (`status`),
    INDEX `idx_publish_date` (`publish_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公告表';

-- 插入一些示例数据
INSERT INTO `announcements` (`title`, `content`, `publish_date`, `publisher_id`, `publisher_name`, `status`) VALUES
('关于五一劳动节放假通知', '<p>各部门同事：</p><p>根据国务院办公厅通知，结合公司实际情况，现将2024年五一劳动节放假安排通知如下：</p><p>1. 放假时间：2024年5月1日至5月5日，共5天。</p><p>2. 请各部门做好工作交接和值班安排。</p><p>3. 节后上班时间：5月6日（星期一）。</p><p><br></p><p>祝大家节日快乐！</p>', '2024-04-15 10:00:00', 1, '人力资源部', 'PUBLISHED'),

('2024年度第一季度工作总结会议通知', '<p>各部门负责人：</p><p>兹定于2024年4月10日下午14:00在第一会议室召开2024年第一季度工作总结会议。</p><p>请各部门准备：</p><p>1. 第一季度工作完成情况</p><p>2. 第二季度工作计划</p><p>3. 存在的问题和解决方案</p><p><br></p><p>请准时参加！</p>', '2024-04-05 14:30:00', 1, '办公室', 'PUBLISHED'),

('新版人事管理系统上线通知', '<p>各位同事：</p><p>为提升人事管理效率，公司将于2024年4月20日正式启用新版人事管理系统。</p><p>主要更新内容：</p><p>1. 优化请假申请流程</p><p>2. 新增在线培训模块</p><p>3. 完善绩效考核功能</p><p><br></p><p>系统使用培训安排将另行通知。</p>', NOW(), 1, '信息技术部', 'DRAFT'),

('公司周年庆活动预告', '<p>亲爱的同事们：</p><p>公司成立10周年庆典活动将于2024年5月20日举行，具体活动安排如下：</p><p>1. 上午：十周年庆典仪式</p><p>2. 下午：员工才艺表演</p><p>3. 晚上：周年庆晚宴</p><p><br></p><p>欢迎大家积极报名参与！</p>', '2024-04-01 09:00:00', 1, '企业文化部', 'ARCHIVED'); 