--begin security config
INSERT INTO users (username, password, enabled) VALUES ('pb', 'p', true);
INSERT INTO users (username, password, enabled) VALUES ('rp', 'r', true);
INSERT INTO users (username, password, enabled) VALUES ('ap', 'a', true);
INSERT INTO users (username, password, enabled) VALUES ('tm', 't', true);

--INSERT INTO authorities(username, authority) VALUES ('Pinak', 'ROLE_USER');

INSERT INTO groups(group_name) VALUES ('Administrators');
INSERT INTO groups(group_name) VALUES ('Employees');
INSERT INTO groups(group_name) VALUES ('Manager');

INSERT INTO group_authorities(group_id, authority) VALUES (1, 'ROLE_ADMIN');
INSERT INTO group_authorities(group_id, authority) VALUES (2, 'ROLE_USER');
INSERT INTO group_authorities(group_id, authority) VALUES (3, 'ROLE_SUPERVISOR');

INSERT INTO group_members (username, group_id) VALUES ('pb', 1);
INSERT INTO group_members (username, group_id) VALUES ('rp', 2);
INSERT INTO group_members (username, group_id) VALUES ('ap', 3);
INSERT INTO group_members (username, group_id) VALUES ('tm', 3);
--end security config

--begin employee tables
INSERT INTO employees (username, first_name, last_name) VALUES ('pb', 'Pinak', 'Barve');
INSERT INTO employees (username, first_name, last_name) VALUES ('rp', 'Rohit', 'Panwar');
INSERT INTO employees (username, first_name, last_name) VALUES ('ap', 'Amit', 'Pant');
INSERT INTO employees (username, first_name, last_name) VALUES ('tm', 'Test', 'Manager');

INSERT INTO clients (name) VALUES ('Internal');
INSERT INTO clients (name) VALUES ('Nationwide');
INSERT INTO clients (name) VALUES ('Chase');
INSERT INTO clients (name) VALUES ('Limited');
INSERT INTO clients (name) VALUES ('Cardinal');

INSERT INTO cost_centers (cost_code, name, client_id) VALUES ('ADM', 'Administration', 1);
INSERT INTO cost_centers (cost_code, name, client_id) VALUES ('HOL', 'Holiday', 1);
INSERT INTO cost_centers (cost_code, name, client_id) VALUES ('PTO', 'Paid Time Off', 1);
INSERT INTO cost_centers (cost_code, name, client_id) VALUES ('NAW', 'Nationwide', 2);
INSERT INTO cost_centers (cost_code, name, client_id) VALUES ('CCL', 'Chase Through Collabera', 3);

INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (1, 'HOL');
INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (1, 'NAW');
INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (2, 'HOL');
INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (2, 'PTO');
INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (3, 'HOL');
INSERT INTO employees_cost_centers (employee_id, cost_code) VALUES (3, 'CCL');

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
