package com.percipient.matrix.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.percipient.matrix.dao.EmployeeCostCenterRepository;
import com.percipient.matrix.domain.EmployeeCostCenter;
import com.percipient.matrix.util.DateUtil;
import com.percipient.matrix.view.EmployeeCostCenterView;

public interface EmployeeCostCenterService {

    public List<EmployeeCostCenterView> getEmpCostCentersForEmployee(
            Integer employeeId);
}

@Service
class EmployeeCostCenterServiceImpl implements EmployeeCostCenterService {

    @Autowired
    EmployeeCostCenterRepository employeeCostCenterRepository;

    @Autowired
    DateUtil dateUtil;

    @Override
    @Transactional
    public List<EmployeeCostCenterView> getEmpCostCentersForEmployee(
            Integer employeeId) {

        List<EmployeeCostCenterView> empCCViewList = new ArrayList<EmployeeCostCenterView>();
        List<EmployeeCostCenter> empCCList = employeeCostCenterRepository
                .getAllForEmployee(employeeId);
        if (empCCList != null) {
            for (EmployeeCostCenter empCC : empCCList) {
                EmployeeCostCenterView empCCView = getEmpCCViewFromempCC(empCC);
                empCCViewList.add(empCCView);
            }
        }
        return empCCViewList;
    }

    private EmployeeCostCenterView getEmpCCViewFromempCC(
            EmployeeCostCenter empCC) {

        EmployeeCostCenterView empCCView = new EmployeeCostCenterView();
        empCCView.setCostCode(empCC.getCostCode());
        empCCView.setEmployeeId(empCC.getEmployeeId());
        empCCView.setStartDate(dateUtil.getAsString(empCC.getStartDate()));
        empCCView.setEndDate(dateUtil.getAsString(empCC.getEndDate()));

        return empCCView;
    }

}
