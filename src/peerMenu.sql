-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('对等成员', '3', '1', 'peer', 'system/peer/index', 1, 0, 'C', '0', '0', 'system:peer:list', '#', 'admin', sysdate(), '', null, '对等成员菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('对等成员查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'system:peer:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('对等成员新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'system:peer:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('对等成员修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'system:peer:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('对等成员删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'system:peer:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('对等成员导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'system:peer:export',       '#', 'admin', sysdate(), '', null, '');