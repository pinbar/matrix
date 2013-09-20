package com.percipient.matrix.session;

import java.util.ArrayList;
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
    private List<Integer> reporteeIds = new ArrayList<Integer>();

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

    public List<Integer> getReporteeIds() {
        return reporteeIds;
    }

    public void setReporteeIds(List<Integer> reporteeIds) {
        this.reporteeIds = reporteeIds;
    }

    /** util methods **/

    public List<CostCenterView> getCostCentersFlattened() {
        List<CostCenterView> allCCList = new ArrayList<CostCenterView>();
        for (List<CostCenterView> ccList : costCenters.values()) {
            allCCList.addAll(ccList);
        }
        return allCCList;
    }
}
