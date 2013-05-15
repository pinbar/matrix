package com.percipient.matrix.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.percipient.matrix.dao.EmployeeRepository;
import com.percipient.matrix.dao.GroupRepository;
import com.percipient.matrix.dao.TimesheetRepository;
import com.percipient.matrix.domain.Employee;
import com.percipient.matrix.domain.Timesheet;
import com.percipient.matrix.security.Group;
import com.percipient.matrix.security.GroupMember;
import com.percipient.matrix.security.User;
import com.percipient.matrix.session.UserInfo;
import com.percipient.matrix.view.EmployeeView;

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
    private GroupRepository groupRepository;

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

        Employee employee = null;
        if (employeeView.getId() != null) {
            employee = employeeRepository.getEmployee(employeeView.getId());
        }
        if (employee == null) {
            employee = new Employee();
            User user = new User();
            user.setEnabled(true);
            user.setPassword(employeeView.getUserName() + "01");
            employee.setUser(user);
            employee.setGroupMember(new GroupMember());
        }
        employee.setFirstName(employeeView.getFirstName());
        employee.setLastName(employeeView.getLastName());
        employee.setPhone(employeeView.getPhone());
        employee.setEmail(employeeView.getEmail());
        employee.setAddress(employeeView.getAddress());
        employee.setUserName(employeeView.getUserName());

        employee.getUser().setUserName(employeeView.getUserName());

        Group group = groupRepository.getGroupByName(employeeView
                .getGroupName());
        employee.getGroupMember().setGroupId(group.getId());
        employee.getGroupMember().setUserName(employeeView.getUserName());

        return employee;
    }

    private EmployeeView getEmployeeViewFromEmployee(Employee employee) {

        User user = employee.getUser();
        GroupMember groupMember = employee.getGroupMember();
        Group group = groupRepository.getGroup(groupMember.getGroupId());

        EmployeeView employeeView = new EmployeeView();
        employeeView.setId(employee.getId());
        employeeView.setFirstName(employee.getFirstName());
        employeeView.setLastName(employee.getLastName());
        employeeView.setPhone(employee.getPhone());
        employeeView.setEmail(employee.getEmail());
        employeeView.setAddress(employee.getAddress());
        employeeView.setUserName(user.getUserName());
        employeeView.setActive(user.getEnabled());
        employeeView.setGroupName(group.getName());

        return employeeView;
    }
}
