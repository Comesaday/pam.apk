-- 插入数据字典
INSERT INTO `avt_dict` VALUES (1, NULL, NULL, b'0', b'0', NULL, NULL, 'MATTERS', NULL, '事项', NULL, '流程办理事项', 'TEXT');
INSERT INTO `avt_dict` VALUES (2, NULL, NULL, b'0', b'0', NULL, NULL, 'LEAVE', NULL, '请假', 1, '请假事项', 'TEXT');
INSERT INTO `avt_dict` VALUES (3, NULL, NULL, b'0', b'0', NULL, NULL, 'XZSP', NULL, '审批', 1, '审批事项', 'TEXT');
INSERT INTO `avt_dict` VALUES (4, NULL, NULL, b'0', b'0', NULL, NULL, 'ELEMENTS', NULL, '数据元', NULL, '流程元素', 'TEXT');
INSERT INTO `avt_dict` VALUES (5, NULL, NULL, b'0', b'0', NULL, NULL, 'NAME', NULL, '姓名', 4, '姓名', 'TEXT');
INSERT INTO `avt_dict` VALUES (6, NULL, NULL, b'0', b'0', NULL, NULL, 'DAYS', NULL, '天数', 4, '天数', 'TEXT');

-- 插入事项信息
INSERT INTO `avt_matter_info` VALUES (1, '2021-03-19 11:07:37', NULL, b'0', b'0', '2021-04-15 15:41:53', NULL, 'ASK_FOR_LEAVE', '员工请假', NULL, 4, 'LEAVE', '37504', '1');
INSERT INTO `avt_matter_info` VALUES (2, NULL, NULL, b'0', b'0', NULL, NULL, 'ASK_FOR_PERMIT', '申请居住证', NULL, 0, 'XZSP', NULL, '');

-- 插入事项表单数据
INSERT INTO `avt_matter_field_setting` VALUES (1, NULL, NULL, b'0', b'0', NULL, NULL, 'NAME', 5, '姓名', 1, b'1', 1, 'TEXT');
INSERT INTO `avt_matter_field_setting` VALUES (2, NULL, NULL, b'0', b'0', NULL, NULL, 'DAYS', 6, '天数', 1, b'1', 2, 'TEXT');

-- 插入用户信息
insert into avt_user_info(id, user_name, password) values(1, '11', '11');
insert into avt_user_info(id, user_name, password) values(2, '22', '22');
insert into avt_user_info(id, user_name, password) values(3, '33', '33');
insert into avt_user_info(id, user_name, password) values(4, '44', '44');

-- 插入用户组
insert into avt_user_group(id, code, name) values(1, 'GROUP_LEADER', '组长');
insert into avt_user_group(id, code, name) values(2, 'MANAGER', '经理');

-- 用户分配组
insert into avt_user_group_relation(id, user_id, group_id) values(1, 1, 1);
insert into avt_user_group_relation(id, user_id, group_id) values(2, 2, 1);
insert into avt_user_group_relation(id, user_id, group_id) values(3, 3, 2);
insert into avt_user_group_relation(id, user_id, group_id) values(4, 4, 2);

-- 设置事项环境审批人
INSERT INTO `avt_matter_user_setting` VALUES (1, NULL, NULL, NULL, NULL, NULL, NULL, 'ZZSP', '组长审批', 1, NULL, NULL, b'0', 1, '组长', NULL);
INSERT INTO `avt_matter_user_setting` VALUES (2, NULL, NULL, NULL, NULL, NULL, NULL, 'JLSP', '经理审批', 1, NULL, NULL, b'0', 1, '经理', NULL);