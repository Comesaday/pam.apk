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

