-- begin spring security tables
create table users(
    id bigint generated by default as identity(start with 1) primary key,
	username varchar_ignorecase(250) not null unique,
	password varchar_ignorecase(250) not null,
	enabled boolean not null
);
 
create table groups (
	id bigint generated by default as identity(start with 1) primary key,
	group_name varchar_ignorecase(250) unique not null
);

create table group_authorities (
	group_id bigint not null,
	authority varchar(250) not null,
	constraint fk_group_authorities_groups foreign key(group_id) references groups(id)
);

create table group_members (
	id bigint generated by default as identity(start with 1) primary key,
	username varchar(250) not null,
	group_id bigint not null,
	constraint fk_group_members_groups foreign key(group_id) references groups(id)
);
-- end spring security tables

-- begin org chart tables
create table employees (
	id bigint generated by default as identity(start with 1) primary key,
	username varchar(250) not null unique,
	first_name varchar(250) not null, last_name varchar(250) not null, phone varchar(250), email varchar(250), address varchar(250)
);

create table clients (
	id bigint generated by default as identity(start with 1) primary key,
	name varchar(250) unique, phone varchar(250), email varchar(250), address varchar(250),
	primary_contact varchar(250) 
);

create table cost_centers (
    id int generated by default as identity(start with 1) primary key,
    cost_code varchar(250) unique, name varchar(250) not null, client_id bigint not null, 
    constraint fk_cost_center_clients foreign key(client_id) references clients(id)
);

create table employees_cost_centers (
  employee_id bigint not null, cost_code varchar(250) not null, 
  primary key(employee_id, cost_code),
  constraint fk_ecc_eid foreign key(employee_id) references employees(id),
  constraint fk_ecc_cc foreign key(cost_code) references cost_centers(cost_code)
);

-- end org chart tables

-- begin timesheet tables
create table timesheets (
	id int generated by default as identity(start with 1) primary key,
	week_ending date, status varchar(250), employee_id int not null,
	constraint unique_week_employee_timesheet unique(employee_id, week_ending),
	constraint fk_timesheets_employees foreign key(employee_id) references employees(id)
);
	
create table timesheet_items (
	id int generated by default as identity(start with 1) primary key,
	item_date date not null, cost_code varchar(250) not null, hours float not null, timesheet_id int not null,
	constraint unique_date_cc_tsid_ts_item unique(item_date, cost_code, timesheet_id),
	constraint fk_timesheets_items_cost_centers foreign key(cost_code) references cost_centers(cost_code),
	constraint fk_timesheet_items_timesheets foreign key(timesheet_id) references timesheets(id)
);

create table timesheet_attachments (
    id int generated by default as identity(start with 1) primary key,
    file_name varchar(250) not null, size bigint not null, 
    content_type varchar(250) not null, content blob not null, timesheet_id int not null,
    constraint unique_file_name_tsid unique(file_name, timesheet_id),
    constraint fk_timesheet_attachments_timesheets foreign key(timesheet_id) references timesheets(id)
);
	
create table employees_timesheets (
	employee_id int not null, timesheet_id int not null,
	primary key(employee_id, timesheet_id),
	constraint fk_employees_timesheets_employees foreign key(employee_id) references employees(id),
	constraint fk_employees_timesheets_timesheets foreign key(timesheet_id) references timesheets(id)
);
-- end timesheet tables

-- begin status report tables

create table status_reports (
	id int generated by default as identity(start with 1) primary key,
	employee_id int not null, week_ending date not null, description varchar(250),
	constraint unique_week_employee_stat_report unique(employee_id, week_ending),
    constraint fk_status_reports_employees foreign key(employee_id) references employees(id)
);
