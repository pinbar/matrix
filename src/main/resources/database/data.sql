--begin security config
INSERT INTO users (username, password, enabled) VALUES ('pb', 'p', true);
INSERT INTO users (username, password, enabled) VALUES ('rp', 'r', true);
INSERT INTO users (username, password, enabled) VALUES ('ap', 'a', true);
INSERT INTO users (username, password, enabled) VALUES ('nb', 'n', true);
INSERT INTO users (username, password, enabled) VALUES ('sc', 's', true);
INSERT INTO users (username, password, enabled) VALUES ('uk', 'u', true);

--INSERT INTO authorities(username, authority) VALUES ('Pinak', 'ROLE_USER');

INSERT INTO groups(group_name) VALUES ('Administrators');
INSERT INTO groups(group_name) VALUES ('Employees');
INSERT INTO groups(group_name) VALUES ('Managers');

INSERT INTO group_authorities(group_id, authority) VALUES (1, 'ROLE_ADMIN');
INSERT INTO group_authorities(group_id, authority) VALUES (2, 'ROLE_USER');
INSERT INTO group_authorities(group_id, authority) VALUES (3, 'ROLE_SUPERVISOR');

INSERT INTO group_members (username, group_id) VALUES ('pb', 1);
INSERT INTO group_members (username, group_id) VALUES ('rp', 2);
INSERT INTO group_members (username, group_id) VALUES ('ap', 3);
INSERT INTO group_members (username, group_id) VALUES ('sc', 2);
INSERT INTO group_members (username, group_id) VALUES ('uk', 2);
INSERT INTO group_members (username, group_id) VALUES ('nb', 3);
--end security config

--begin employee tables
INSERT INTO employees (username, first_name, last_name, start_date) VALUES ('pb', 'Pinak', 'Barve', '2013-02-02');
INSERT INTO employees (username, first_name, last_name, start_date, manager_id) VALUES ('rp', 'Rohit', 'Panwar', '2013-06-04', 3);
INSERT INTO employees (username, first_name, last_name, start_date) VALUES ('ap', 'Amit', 'Pant','2013-03-01');
INSERT INTO employees (username, first_name, last_name, start_date) VALUES ('nb', 'Naga', 'Bheemaneni', '2013-06-05');
INSERT INTO employees (username, first_name, last_name, start_date, manager_id) VALUES ('sc', 'Shree', 'Chhatwal', '2013-05-05', 4);
INSERT INTO employees (username, first_name, last_name, start_date, manager_id) VALUES ('uk', 'Uday', 'Katta', '2013-07-08', 3);

INSERT INTO clients (name) VALUES ('PTO');
INSERT INTO clients (name) VALUES ('Internal');
INSERT INTO clients (name) VALUES ('Nationwide');
INSERT INTO clients (name) VALUES ('Chase');
INSERT INTO clients (name) VALUES ('Limited');
INSERT INTO clients (name) VALUES ('Cardinal');

INSERT INTO cost_centers (cost_code, name, is_pto, client_id) VALUES ('HOL', 'Holiday', true, 1);
INSERT INTO cost_centers (cost_code, name, is_pto, client_id) VALUES ('SIC', 'Sick Leave', true, 1);
INSERT INTO cost_centers (cost_code, name, is_pto, client_id) VALUES ('VAC', 'Vacation', true, 1);
INSERT INTO cost_centers (cost_code, name, is_pto, client_id) VALUES ('ADM', 'Administration', false, 2);
INSERT INTO cost_centers (cost_code, name, is_pto, client_id) VALUES ('NAW', 'Nationwide', false, 3);
INSERT INTO cost_centers (cost_code, name, is_pto, client_id) VALUES ('CCL', 'Chase Through Collabera', false, 4);
INSERT INTO cost_centers (cost_code, name, is_pto, client_id) VALUES ('VIC', 'Victorias Secret', false, 5);
INSERT INTO cost_centers (cost_code, name, is_pto, client_id) VALUES ('BNN', 'Banana Republic', false, 5);
INSERT INTO cost_centers (cost_code, name, is_pto, client_id) VALUES ('CRD', 'Cardinal Health', false, 6);

INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (1, 'HOL');
INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (1, 'SIC');
INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (1, 'VAC');
INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (1, 'NAW');
INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (1, 'VIC');
INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (2, 'HOL');
INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (2, 'SIC');
INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (2, 'VAC');
INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (2, 'NAW');
INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (2, 'CRD');
INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (3, 'HOL');
INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (3, 'SIC');
INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (3, 'VAC');
INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (3, 'NAW');
INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (3, 'CCL');
INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (4, 'HOL');
INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (4, 'VAC');
INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (4, 'NAW');
INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (5, 'HOL');
INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (5, 'VAC');
INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (5, 'NAW');
INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (6, 'HOL');
INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (6, 'VAC');
INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (6, 'NAW');

--INSERT INTO timesheets (id, week_ending, status, employee_id) VALUES (1, '2012-12-30', 'pending', 1111);
--INSERT INTO timesheets (id, week_ending, status, employee_id) VALUES (2, '2012-12-23', 'approved', 1111);

--INSERT INTO timesheet_items (id, item_date, cost_code, hours) VALUES (1, '2012-12-23','PTO', 8);
--INSERT INTO timesheet_items (id, item_date, cost_code, hours) VALUES (2, '2012-12-24','HOL', 8);
--INSERT INTO timesheet_items (id, item_date, cost_code, hours) VALUES (3, '2012-12-25','NAW', 8);
--INSERT INTO timesheet_items (id, item_date, cost_code, hours) VALUES (4, '2012-12-26','NAW', 6);
--INSERT INTO timesheet_items (id, item_date, cost_code, hours) VALUES (5, '2012-12-26','ADM', 1);
--INSERT INTO timesheet_items (id, item_date, cost_code, hours) VALUES (6, '2012-12-27','NAW', 4);

--INSERT INTO employees_timesheets (employee_id, timesheet_id) VALUES (1111, 1);
--INSERT INTO employees_timesheets (employee_id, timesheet_id) VALUES (1111, 2);

--INSERT INTO timesheets_timesheet_items (timesheet_id, timesheet_item_id) VALUES (1, 1);
--INSERT INTO timesheets_timesheet_items (timesheet_id, timesheet_item_id) VALUES (1, 2);
--INSERT INTO timesheets_timesheet_items (timesheet_id, timesheet_item_id) VALUES (1, 3);
--INSERT INTO timesheets_timesheet_items (timesheet_id, timesheet_item_id) VALUES (1, 4);
--INSERT INTO timesheets_timesheet_items (timesheet_id, timesheet_item_id) VALUES (1, 5);
--INSERT INTO timesheets_timesheet_items (timesheet_id, timesheet_item_id) VALUES (1, 6);
--end employee tables
