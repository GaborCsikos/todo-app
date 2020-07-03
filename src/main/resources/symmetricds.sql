------------------------------------------------------------------------------
-- Node Groups
------------------------------------------------------------------------------
insert into sym_node_group (node_group_id) values ('client');

------------------------------------------------------------------------------
-- Node Group Links
------------------------------------------------------------------------------

-- server sends changes to client when client pulls from server
insert into sym_node_group_link (source_node_group_id, target_node_group_id, data_event_action, is_reversible)
 values ('server', 'client', 'W', 0);

-- client sends changes to server when client pushes to server
insert into sym_node_group_link (source_node_group_id, target_node_group_id, data_event_action, is_reversible)
 values ('client', 'server', 'P', 0);


------------------------------------------------------------------------------
-- Routers
------------------------------------------------------------------------------
insert into sym_channel
(channel_id, processing_order, max_batch_size, enabled, description)
values('gcsikos', 1, 100000, 1, 'Everything');

insert into sym_router
(router_id,source_node_group_id,target_node_group_id,router_type,create_time,last_update_time)
values('server_2_client', 'server', 'client', 'default',current_timestamp, current_timestamp);

-- Default router sends all data from client to server
insert into sym_router
(router_id,source_node_group_id,target_node_group_id,router_type,create_time,last_update_time)
values('client_2_server', 'client', 'server', 'default',current_timestamp, current_timestamp);

insert into SYM_TRIGGER (trigger_id, source_table_name,
          channel_id, last_update_time, create_time)
                  values ('client_2_server', '*', 'gcsikos', current_timestamp, current_timestamp);
insert into SYM_TRIGGER (trigger_id, source_table_name,
          channel_id, last_update_time, create_time)
                  values ('server_2_client', '*', 'gcsikos', current_timestamp, current_timestamp);

insert into SYM_TRIGGER_ROUTER (trigger_id, router_id, initial_load_order, create_time, last_update_time)
values ('client_2_server', 'client_2_server', 100, current_timestamp, current_timestamp);
insert into SYM_TRIGGER_ROUTER (trigger_id, router_id, initial_load_order, create_time, last_update_time)
values ('server_2_client', 'server_2_client', 200, current_timestamp, current_timestamp);


--insert into SYM_TABLE_RELOAD_REQUEST (target_node_id, source_node_id, trigger_id, router_id, create_time, last_update_time)
    -- values ('client', 'server', 'ALL', 'ALL', current_timestamp, current_timestamp);
--USE_VERSION could be instead of USE_CHANGED_DATA
--detect_expression specify the column
-- resolve_row_only should be true
-- https://www.symmetricds.org/doc/3.11/html/user-guide.html#_conflicts
insert into sym_conflict (conflict_id, target_channel_id, source_node_group_id, target_node_group_id, detect_type, resolve_type, ping_back, resolve_changes_only, resolve_row_only, create_time, last_update_time)
values ('conflict-server-client', 'gcsikos', 'server', 'client', 'USE_CHANGED_DATA', 'MANUAL', 'SINGLE_ROW', 0, 1, current_timestamp, current_timestamp);
insert into sym_conflict (conflict_id, target_channel_id, source_node_group_id, target_node_group_id, detect_type,  resolve_type,ping_back, resolve_changes_only, resolve_row_only, create_time, last_update_time)
values ('conflict-client-server', 'gcsikos', 'client', 'server', 'USE_CHANGED_DATA', 'MANUAL', 'SINGLE_ROW', 0, 1, current_timestamp, current_timestamp);

update sym_node_security
set initial_load_enabled = 1
where node_id = 'server';

