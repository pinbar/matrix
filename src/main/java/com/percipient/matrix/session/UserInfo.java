package com.percipient.matrix.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.percipient.matrix.view.CostCenterView;
import com.percipient.matrix.view.EmployeeView;

@Component
@Scope(value = "session")
public class UserInfo {

    private EmployeeView employee;
    private Map<String, List<CostCenterView>> costCenters = new HashMap<String, List<CostCenterView>>();
    private Map<Integer, EmployeeView> reportees = new HashMap<Integer, EmployeeView>();

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

    public Map<Integer, EmployeeView> getReportees() {
        return reportees;
    }

    public void setReportees(Map<Integer, EmployeeView> reportees) {
        this.reportees = reportees;
    }

    /** util methods **/

    public Set<Integer> getReporteeIds() {
        return reportees.keySet();
    }

    public List<EmployeeView> getReporteeViews() {
        return new ArrayList<EmployeeView>(reportees.values());
    }

    public List<CostCenterView> getCostCentersFlattened() {
        List<CostCenterView> allCCList = new ArrayList<CostCenterView>();
        for (List<CostCenterView> ccList : costCenters.values()) {
            allCCList.addAll(ccList);
        }
        return allCCList;
    }
}
