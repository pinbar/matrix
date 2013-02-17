package com.percipient.matrix.view;

import java.util.List;

import com.percipient.matrix.domain.CostCenter;
import com.percipient.matrix.domain.Employee;
import com.percipient.matrix.domain.Timesheet;
import com.percipient.matrix.domain.TimesheetItem;
import com.percipient.matrix.security.Group;
import com.percipient.matrix.security.GroupAuthority;
import com.percipient.matrix.security.GroupMember;
import com.percipient.matrix.security.User;

public class DatabaseView {

	private List<User> users;
	private List<Group> groups;
	private List<GroupAuthority> groupAuthorities;
	private List<GroupMember> groupMembers;
	private List<Employee> employees;
	private List<Timesheet> timesheets;
	private List<TimesheetItem> timesheetItems;
	private List<CostCenter> costCenters;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<GroupAuthority> getGroupAuthorities() {
		return groupAuthorities;
	}

	public void setGroupAuthorities(List<GroupAuthority> groupAuthorities) {
		this.groupAuthorities = groupAuthorities;
	}

	public List<GroupMember> getGroupMembers() {
		return groupMembers;
	}

	public void setGroupMembers(List<GroupMember> groupMembers) {
		this.groupMembers = groupMembers;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public List<Timesheet> getTimesheets() {
		return timesheets;
	}

	public void setTimesheets(List<Timesheet> timesheets) {
		this.timesheets = timesheets;
	}

	public List<TimesheetItem> getTimesheetItems() {
		return timesheetItems;
	}

	public void setTimesheetItems(List<TimesheetItem> timesheetItems) {
		this.timesheetItems = timesheetItems;
	}

	public List<CostCenter> getCostCenters() {
		return costCenters;
	}

	public void setCostCenters(List<CostCenter> costCenters) {
		this.costCenters = costCenters;
	}

}
