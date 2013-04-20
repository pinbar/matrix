package com.percipient.matrix.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.percipient.matrix.dao.EmployeeRepository;
import com.percipient.matrix.dao.TimesheetRepository;
import com.percipient.matrix.display.EmployeeView;
import com.percipient.matrix.domain.Client;
import com.percipient.matrix.domain.Employee;
import com.percipient.matrix.domain.Timesheet;
import com.percipient.matrix.security.GroupMember;
import com.percipient.matrix.security.User;
import com.percipient.matrix.session.UserInfo;

public interface EmployeeService {

    public void setUserInfo(UserInfo user);

    public List<EmployeeView> getEmployees();

    public EmployeeView getEmployee(Integer employeeId);

    public void saveEmployee(EmployeeView employeeView);

    public void deleteEmployee(EmployeeView employeeView);

}

@Service
class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TimesheetRepository timesheetRepository;

    @Override
    @Transactional
    public void setUserInfo(UserInfo userInfo) {

        Employee employee = employeeRepository.getEmployeeByUserName(userInfo
                .getUserName());
        userInfo.setEmployeeId(employee.getId());
        userInfo.setFirstName(employee.getFirstName());
        userInfo.setLastName(employee.getLastName());
    }

    @Override
    @Transactional
    public List<EmployeeView> getEmployees() {

        List<Employee> employees = employeeRepository.getEmployees();
        List<EmployeeView> employeeViews = new ArrayList<EmployeeView>();
        for (Employee employee : employees) {
            EmployeeView employeeView = getEmployeeViewFromEmployee(employee);
            employeeViews.add(employeeView);
        }
        return employeeViews;
    }

    @Override
    @Transactional
    public EmployeeView getEmployee(Integer employeeId) {

        Employee employee = employeeRepository.getEmployee(employeeId);
        EmployeeView employeeView = getEmployeeViewFromEmployee(employee);

        return employeeView;
    }

    @Override
    @Transactional
    public void saveEmployee(EmployeeView employeeView) {

        Employee employee = getEmployeeFromEmployeeView(employeeView);
        employeeRepository.saveEmployee(employee);
    }

    @Override
    @Transactional
    public void deleteEmployee(EmployeeView employeeView) {

        Employee employee = getEmployeeFromEmployeeView(employeeView);
        List<Timesheet> timesheets = timesheetRepository
                .getTimesheets(employee);
        timesheetRepository.delete(timesheets);
        employeeRepository.deleteEmployee(employee);
    }

    private Employee getEmployeeFromEmployeeView(EmployeeView employeeView) {

        Employee employee = new Employee();
        employee.setId(employeeView.getId());
        employee.setFirstName(employeeView.getFirstName());
        employee.setLastName(employeeView.getLastName());
        employee.setUserName(employeeView.getUserName());

        User user = new User();
        user.setUserName(employeeView.getUserName());
        user.setPassword(employeeView.getPassword());
        user.setEnabled(employeeView.isEnabled());
        employee.setUser(user);

        GroupMember groupMember = getGroupMember(employeeView.getGroupId(),
                employeeView.getUserName());
        employee.setGroupMember(groupMember);

        return employee;
    }

    private EmployeeView getEmployeeViewFromEmployee(Employee employee) {

        User user = employee.getUser();
        GroupMember groupMember = employee.getGroupMember();

        EmployeeView employeeView = new EmployeeView();
        employeeView.setId(employee.getId());
        employeeView.setFirstName(employee.getFirstName());
        employeeView.setLastName(employee.getLastName());
        employeeView.setUserName(user.getUserName());
        employeeView.setPassword(user.getPassword());
        employeeView.setEnabled(user.isEnabled());
        employeeView.setGroupId(groupMember.getGroupId());
        employeeView.setClients(getClients(employee));

        return employeeView;
    }

    private Set<String> getClients(Employee employee) {
        Set<String> clients = new HashSet<String>();
        for (Client client : employee.getClients()) {
            clients.add(client.getName());
        }
        return clients;
    }

    private GroupMember getGroupMember(Integer groupId, String userName) {

        GroupMember groupMember = employeeRepository
                .getGroupMemberByUserName(userName);
        groupMember.setGroupId(groupId);
        groupMember.setUserName(userName);

        return groupMember;
    }
}
