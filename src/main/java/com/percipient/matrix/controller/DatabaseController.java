package com.percipient.matrix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.percipient.matrix.domain.CostCenter;
import com.percipient.matrix.domain.Employee;
import com.percipient.matrix.domain.Timesheet;
import com.percipient.matrix.domain.TimesheetItem;
import com.percipient.matrix.security.Group;
import com.percipient.matrix.security.GroupAuthority;
import com.percipient.matrix.security.GroupMember;
import com.percipient.matrix.security.User;
import com.percipient.matrix.service.DatabaseService;
import com.percipient.matrix.view.DatabaseView;

@Controller
@RequestMapping(value = "/database")
public class DatabaseController {

    public static final String MODEL_ATTRIBUTE_DB_VIEW = "db";

    @Autowired
    DatabaseService databaseService;

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getGroupList(Model model) {
        DatabaseView dbView = new DatabaseView();

        List<User> users = (List<User>) databaseService.getTableData(User.class
                .getSimpleName());
        dbView.setUsers(users);

        List<Group> groups = (List<Group>) databaseService
                .getTableData(Group.class.getSimpleName());
        dbView.setGroups(groups);

        List<GroupMember> groupMembers = (List<GroupMember>) databaseService
                .getTableData(GroupMember.class.getSimpleName());
        dbView.setGroupMembers(groupMembers);

        List<GroupAuthority> groupAuthorities = (List<GroupAuthority>) databaseService
                .getTableData(GroupAuthority.class.getSimpleName());
        dbView.setGroupAuthorities(groupAuthorities);

        List<Employee> employees = (List<Employee>) databaseService
                .getTableData(Employee.class.getSimpleName());
        dbView.setEmployees(employees);

        List<Timesheet> timesheeets = (List<Timesheet>) databaseService
                .getTableData(Timesheet.class.getSimpleName());
        dbView.setTimesheets(timesheeets);

        List<TimesheetItem> timesheeetItems = (List<TimesheetItem>) databaseService
                .getTableData(TimesheetItem.class.getSimpleName());
        dbView.setTimesheetItems(timesheeetItems);

        List<CostCenter> costCenters = (List<CostCenter>) databaseService
                .getTableData(CostCenter.class.getSimpleName());
        dbView.setCostCenters(costCenters);

        model.addAttribute(MODEL_ATTRIBUTE_DB_VIEW, dbView);
        return "databasePage";
    }
}
