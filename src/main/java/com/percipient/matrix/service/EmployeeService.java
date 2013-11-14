package com.percipient.matrix.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.percipient.matrix.dao.EmployeeCostCenterRepository;
import com.percipient.matrix.dao.EmployeeRepository;
import com.percipient.matrix.dao.GroupRepository;
import com.percipient.matrix.dao.TimesheetRepository;
import com.percipient.matrix.domain.Employee;
import com.percipient.matrix.domain.EmployeeCostCenter;
import com.percipient.matrix.domain.Timesheet;
import com.percipient.matrix.security.Group;
import com.percipient.matrix.security.GroupMember;
import com.percipient.matrix.security.User;
import com.percipient.matrix.session.AppConfig;
import com.percipient.matrix.util.DateUtil;
import com.percipient.matrix.view.EmployeePtoConfigView;
import com.percipient.matrix.view.EmployeeView;

public interface EmployeeService {

    public EmployeeView getEmployeeByUserName(String userName);

    public List<EmployeeView> getEmployees();

    public EmployeeView getEmployee(Integer employeeId);

    public void saveEmployee(EmployeeView employeeView);

    public void deleteEmployee(EmployeeView employeeView);

    public List<EmployeeView> getEmployeesByGroup(String group);

    public Map<Integer, EmployeeView> getReportees(EmployeeView employee);

}

@Service
class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeCostCenterRepository employeeCostCenterRepository;

    @Autowired
    private EmployeePtoConfigService employeePtoConfigService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private TimesheetRepository timesheetRepository;

    @Autowired
    DateUtil dateUtil;

    @Override
    @Transactional
    public EmployeeView getEmployeeByUserName(String userName) {

        Employee employee = employeeRepository.getEmployeeByUserName(userName);
        EmployeeView employeeView = getEmployeeViewFromEmployee(employee);

        return employeeView;
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
    public List<EmployeeView> getEmployeesByGroup(String group) {
        List<Employee> employees = employeeRepository
                .getEmployeesByGroup(StringUtils.capitalize(group));
        List<EmployeeView> employeeViews = new ArrayList<EmployeeView>();
        for (Employee employee : employees) {
            EmployeeView employeeView = getEmployeeViewFromEmployee(employee);
            employeeViews.add(employeeView);
        }
        return employeeViews;
    }

    @Override
    @Transactional
    public void saveEmployee(EmployeeView employeeView) {

        Employee employee = getEmployeeFromEmployeeView(employeeView);
        Integer id = employeeRepository.saveEmployee(employee);
        id = id != null ? id : employee.getId();
        if (id != null) {
            employeeView.setId(id);
        }
        if (employeeView.getCostCodes() != null
                && !employeeView.getCostCodes().isEmpty()) {
            List<EmployeeCostCenter> empCostCenterList = collateUpdatedCostCenterList(employeeView);
            employeeCostCenterRepository.save(empCostCenterList);
        }
        if (StringUtils.isNotBlank(employeeView.getPtosJSONStr())) {
            List<EmployeePtoConfigView> ptoConfigViewList = new ArrayList<EmployeePtoConfigView>();
            try {
                ptoConfigViewList = new ObjectMapper().readValue(
                        employeeView.getPtosJSONStr(),
                        new TypeReference<List<EmployeePtoConfigView>>() {
                        });
            } catch (Exception e) {
                throw new RuntimeException("couldn't convert"
                        + employeeView.getPtosJSONStr() + "to JSON", e);
            }
            employeePtoConfigService.savePtoConfigForEmployee(id,
                    ptoConfigViewList);
        }
    }

    @Override
    @Transactional
    public void deleteEmployee(EmployeeView employeeView) {

        Employee employee = new Employee();
        employee.setId(employeeView.getId());
        List<Timesheet> timesheets = timesheetRepository
                .getTimesheets(employeeView.getId());
        timesheetRepository.delete(timesheets);
        employeeCostCenterRepository.deleteAllForEmployee(employeeView.getId());
        employeeRepository.deleteEmployee(employee);
    }

    @Override
    @Transactional
    public Map<Integer, EmployeeView> getReportees(EmployeeView employeeView) {

        Map<Integer, EmployeeView> reporteeMap = new HashMap<Integer, EmployeeView>();
        List<Employee> employees = new ArrayList<Employee>();

        if (employeeView.getGroupName().equalsIgnoreCase(
                AppConfig.EMPLOYEE_GROUP_NAME_ADMINISTRATOR)) {
            employees = employeeRepository.getEmployees();
        } else if (employeeView.getGroupName().equalsIgnoreCase(
                AppConfig.EMPLOYEE_GROUP_NAME_MANAGER)) {
            employees = employeeRepository.getEmployeesByManager(employeeView
                    .getId());
        }
        for (Employee employee : employees) {
            EmployeeView reportee = getEmployeeViewFromEmployee(employee);
            reporteeMap.put(reportee.getId(), reportee);
        }
        return reporteeMap;
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
        employee.setStartDate(dateUtil.getAsDate(employeeView.getStartDate()));
        if (StringUtils.isNotBlank(employeeView.getEndDate())) {
            employee.setEndDate(dateUtil.getAsDate(employeeView.getEndDate()));
        }

        employee.setPhone(employeeView.getPhone());
        employee.setEmail(employeeView.getEmail());
        employee.setAddress(employeeView.getAddress());
        employee.setUserName(employeeView.getUserName());
        employee.setManagerId(employeeView.getManagerId());

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
        employeeView
                .setStartDate(dateUtil.getAsString(employee.getStartDate()));
        if (null != employee.getEndDate()) {
            employeeView
                    .setEndDate(dateUtil.getAsString(employee.getEndDate()));
        }
        employeeView.setPhone(employee.getPhone());
        employeeView.setEmail(employee.getEmail());
        employeeView.setAddress(employee.getAddress());
        employeeView.setUserName(user.getUserName());
        employeeView.setActive(user.getEnabled());
        employeeView.setGroupName(group.getName());
        employeeView.setManagerId(employee.getManagerId());
        return employeeView;
    }

    private List<EmployeeCostCenter> collateUpdatedCostCenterList(
            EmployeeView employeeView) {
        List<EmployeeCostCenter> empCostCenterList = new ArrayList<EmployeeCostCenter>();
        List<EmployeeCostCenter> existingEmpCostCenterList = employeeCostCenterRepository
                .getAllForEmployee(employeeView.getId());

        List<String> costCodes = employeeView.getCostCodes();
        for (String costCode : costCodes) {
            EmployeeCostCenter empCC = employeeCostCenterRepository
                    .getEmployeeCostCenter(employeeView.getId(), costCode);
            if (empCC == null) {
                empCC = new EmployeeCostCenter();
                empCC.setCostCode(costCode);
                empCC.setEmployeeId(employeeView.getId());
                empCC.setActive(true);
            } else {
                existingEmpCostCenterList.remove(empCC);
            }
            empCostCenterList.add(empCC);
        }
        // deactivate the remaining, these were unchecked
        if (!existingEmpCostCenterList.isEmpty()) {
            employeeCostCenterRepository.deactivate(existingEmpCostCenterList);
        }

        return empCostCenterList;
    }

}
