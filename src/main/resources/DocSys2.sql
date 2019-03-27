CREATE TABLE `file` (
`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '档案序号',
`code` varchar(255) CHARACTER SET utf8 NULL COMMENT '档案编号',
`state` int(1) NULL COMMENT '档案状态0不在1在档',
`person_id` int(11) NULL COMMENT '人员序号',
`department_id` int(11) NULL COMMENT '存档位置',
`file_age` int(1) NULL COMMENT '档案年龄1一直2早于3晚于4未知',
`file_check` int(1) NULL COMMENT '信息整理0未整理1已整理',
`file_inside` varchar(3000) CHARACTER SET utf8 NULL COMMENT '档案所含内容',
`remark` varchar(3000) CHARACTER SET utf8 NULL COMMENT '档案备注',
PRIMARY KEY (`id`) 
);

CREATE TABLE `person` (
`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '人员序号',
`number` varchar(18) CHARACTER SET utf8 NULL COMMENT '证件号码',
`name` varchar(20) CHARACTER SET utf8 NULL COMMENT '人员姓名',
`sex` int(1) NULL COMMENT '人员性别1男2女',
`phone` varchar(20) CHARACTER SET utf8 NULL COMMENT '联系电话',
`address` varchar(50) CHARACTER SET utf8 NULL COMMENT '联系地址',
PRIMARY KEY (`id`) 
);

CREATE TABLE `user` (
`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户序号',
`name` varchar(5) CHARACTER SET utf8 NULL COMMENT '用户姓名',
`login` varchar(20) CHARACTER SET utf8 NULL COMMENT '登录名称',
`password` varchar(20) CHARACTER SET utf8 NULL COMMENT '登录密码',
`state` int(1) NULL COMMENT '用户状态0禁用1启用',
PRIMARY KEY (`id`) 
);

CREATE TABLE `department` (
`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门序号',
`name` varchar(30) CHARACTER SET utf8 NULL COMMENT '部门名称',
`phone` varchar(20) CHARACTER SET utf8 NULL COMMENT '部门电话',
`address` varchar(50) CHARACTER SET utf8 NULL COMMENT '部门地址',
PRIMARY KEY (`id`) 
);

CREATE TABLE `flow` (
`id` int NOT NULL AUTO_INCREMENT COMMENT '序号',
`time` datetime NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '流转时间',
`type` int(1) NULL COMMENT '流转类型1存档2提档3借档4还档',
`file_id` int(11) NULL COMMENT '档案序号',
`user_id` int(11) NULL COMMENT '用户序号',
`reason` varchar(50) CHARACTER SET utf8 NULL COMMENT '流转原因',
`direction` varchar(50) CHARACTER SET utf8 NULL COMMENT '流转方向',
`delivery` int(1) NULL COMMENT '传递方式1个人2专人3邮寄4其他',
`remark` varchar(999) CHARACTER SET utf8 NULL COMMENT '备注',
PRIMARY KEY (`id`) 
);

CREATE TABLE `func` (
`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '功能序号',
`name` varchar(20) CHARACTER SET utf8 NULL COMMENT '功能名称',
`level` int(1) NULL COMMENT '功能层级123',
PRIMARY KEY (`id`) 
);

CREATE TABLE `power` (
`user_id` int(11) NOT NULL COMMENT '用户序号',
`department_id` int(11) NOT NULL COMMENT '部门序号',
`func_id` int(11) NOT NULL COMMENT '功能序号',
PRIMARY KEY (`user_id`, `department_id`, `func_id`) 
);


ALTER TABLE `file` ADD CONSTRAINT `file_person` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`);
ALTER TABLE `flow` ADD CONSTRAINT `flow_file` FOREIGN KEY (`file_id`) REFERENCES `file` (`id`);
ALTER TABLE `flow` ADD CONSTRAINT `flow_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `power` ADD CONSTRAINT `power_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `power` ADD CONSTRAINT `power_department` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`);
ALTER TABLE `power` ADD CONSTRAINT `power_func` FOREIGN KEY (`func_id`) REFERENCES `func` (`id`);
ALTER TABLE `file` ADD CONSTRAINT `file_department` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`);
