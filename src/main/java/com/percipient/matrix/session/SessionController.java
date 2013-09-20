package com.percipient.matrix.session;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.percipient.matrix.service.CostCenterService;
import com.percipient.matrix.service.EmployeeCostCenterService;
import com.percipient.matrix.service.EmployeeService;
import com.percipient.matrix.view.CostCenterView;
import com.percipient.matrix.view.EmployeeView;

@Controller
@Scope("session")
public class SessionController {

    @Autowired
    UserInfo userInfo;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    CostCenterService costCenterService;
    @Autowired
    EmployeeCostCenterService employeeCostCenterService;

    @RequestMapping(value = "/")
    public String home() {

        return "/landing/landingPage";
    }

    @RequestMapping(value = "/start")
    public String setUser(Principal principal) {

        EmployeeView employee = populateEmployee(principal.getName());
        userInfo.setEmployee(employee);
        populateAllCostCentersGrouped(employee);
        populateReporteess(employee);

        return "home";
    }

    private EmployeeView populateEmployee(String userName) {
        EmployeeView employeeView = employeeService
                .getEmployeeByUserName(userName);
        return employeeView;
    }

    private void populateAllCostCentersGrouped(EmployeeView employee) {
        Map<String, List<CostCenterView>> costCenters = new HashMap<String, List<CostCenterView>>();
        if (employee.getGroupName().equalsIgnoreCase(
                AppConfig.EMPLOYEE_GROUP_NAME_ADMINISTRATOR)) {
            costCenters = costCenterService.getCostCentersGroups();
        } else {
            costCenters = employeeCostCenterService
                    .getCostCenterViewListGroupedForEmployee(employee.getId());
        }
        userInfo.setCostCenters(costCenters);
    }

    private void populateReporteess(EmployeeView employeeView) {

        Map<Integer, EmployeeView> reportees = employeeService
                .getReportees(employeeView);
        userInfo.setReportees(reportees);
    }
}
