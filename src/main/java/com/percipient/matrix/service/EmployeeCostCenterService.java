package com.percipient.matrix.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.percipient.matrix.dao.CostCenterRepository;
import com.percipient.matrix.dao.EmployeeCostCenterRepository;
import com.percipient.matrix.domain.CostCenter;
import com.percipient.matrix.domain.EmployeeCostCenter;
import com.percipient.matrix.util.DateUtil;
import com.percipient.matrix.view.CostCenterView;

public interface EmployeeCostCenterService {

    List<String> getCostCodesForEmployee(Integer employeeId);

    public void saveCostCodesForEmployee(Integer employeeId,
            List<String> costCodeList);

    public void deleteCostCodesForEmployee(Integer employeeId,
            List<String> costCodeList);

    public List<CostCenterView> getCostCenterViewListForEmployees(
            Integer employeeId);
}

@Service
class EmployeeCostCenterServiceImpl implements EmployeeCostCenterService {

    @Autowired
    EmployeeCostCenterRepository employeeCostCenterRepository;

    @Autowired
    CostCenterService costCenterService;

    @Autowired
    DateUtil dateUtil;

    @Override
    @Transactional
    public List<String> getCostCodesForEmployee(Integer employeeId) {

        List<String> empCostCodes = new ArrayList<String>();
        List<EmployeeCostCenter> empCCList = employeeCostCenterRepository
                .getAllForEmployee(employeeId);
        if (empCCList != null) {
            for (EmployeeCostCenter empCC : empCCList) {
                empCostCodes.add(empCC.getCostCode());
            }
        }
        return empCostCodes;
    }

    @Override
    @Transactional
    public void saveCostCodesForEmployee(Integer employeeId,
            List<String> costCodeList) {
        List<EmployeeCostCenter> empCCList = getEmployeeCostCenterList(
                employeeId, costCodeList);
        employeeCostCenterRepository.save(empCCList);
    }

    @Override
    @Transactional
    public void deleteCostCodesForEmployee(Integer employeeId,
            List<String> costCodeList) {
        List<EmployeeCostCenter> empCCList = getEmployeeCostCenterList(
                employeeId, costCodeList);
        employeeCostCenterRepository.delete(empCCList);
    }

    @Override
    @Transactional
    public List<CostCenterView> getCostCenterViewListForEmployees(
            Integer employeeId) {
        List<String> costCodes = getCostCodesForEmployee(employeeId);
        List<CostCenterView> costCenterViewList = costCenterService
                .getCCViewListFromCostCodes(costCodes);
        return costCenterViewList;
    }

    @Override
    @Transactional
    public List<CostCenterView> getCostCenterViewListForEmployees(
            Integer employeeId) {
        List<String> costCodes = getCostCodesForEmployee(employeeId);
        List<CostCenterView> costCenterViewList = costCenterService
                .getCCViewListFromCostCodes(costCodes);
        return costCenterViewList;
    }

    private List<EmployeeCostCenter> getEmployeeCostCenterList(
            Integer employeeId, List<String> costCodeList) {
        List<EmployeeCostCenter> empCCList = new ArrayList<EmployeeCostCenter>();
        EmployeeCostCenter empCC;
        for (String costCode : costCodeList) {
            empCC = new EmployeeCostCenter();
            empCC.setEmployeeId(employeeId);
            empCC.setCostCode(costCode);

            empCCList.add(empCC);
        }
        return empCCList;
    }

}
