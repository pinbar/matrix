package com.percipient.matrix.session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.percipient.matrix.view.CostCenterView;
import com.percipient.matrix.view.EmployeeView;

@Component
@Scope(value = "session")
public class UserInfo {

    private EmployeeView employee;
    private Map<String, List<CostCenterView>> costCenters = new HashMap<String, List<CostCenterView>>();

    private Integer employeeId;
    private String userName;
    private String firstName;
    private String lastName;

    public EmployeeView getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeView employee) {
        this.employee = employee;
    }

    public Map<String, List<CostCenterView>> getCostCenters() {
        return costCenters;
    }

    public void setCostCenters(Map<String, List<CostCenterView>> costCenters) {
        this.costCenters = costCenters;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
