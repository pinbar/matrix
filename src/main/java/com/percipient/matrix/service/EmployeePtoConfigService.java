package com.percipient.matrix.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.percipient.matrix.dao.EmployeePtoConfigRepository;
import com.percipient.matrix.domain.EmployeePtoConfig;
import com.percipient.matrix.view.EmployeePtoConfigView;

public interface EmployeePtoConfigService {

    public List<EmployeePtoConfigView> getPtoConfigForEmployee(
            Integer employeeId);

    public void savePtoConfigForEmployee(Integer employeeId,
            List<EmployeePtoConfigView> ptoConfigList);

    public void deletePtoConfigForEmployee(Integer employeeId);
}

@Service
class EmployeePtoConfigServiceImpl implements EmployeePtoConfigService {

    @Autowired
    EmployeePtoConfigRepository employeePtoConfigRepository;

    @Override
    @Transactional
    public List<EmployeePtoConfigView> getPtoConfigForEmployee(
            Integer employeeId) {

        List<EmployeePtoConfig> list = employeePtoConfigRepository
                .getAllForEmployee(employeeId);
        List<EmployeePtoConfigView> viewList = getViewListFromPtoConfigList(list);

        return viewList;
    }

    @Override
    @Transactional
    public void savePtoConfigForEmployee(Integer employeeId,
            List<EmployeePtoConfigView> ptoConfigList) {
        List<EmployeePtoConfig> list = getEmployeePtoConfigList(employeeId,
                ptoConfigList);
        if (list != null && !list.isEmpty()) {
            employeePtoConfigRepository.save(list);
        }
    }

    @Override
    @Transactional
    public void deletePtoConfigForEmployee(Integer employeeId) {
        employeePtoConfigRepository.deleteAllForEmployee(employeeId);
    }

    private List<EmployeePtoConfig> getEmployeePtoConfigList(
            Integer employeeId, List<EmployeePtoConfigView> viewList) {
        List<EmployeePtoConfig> list = new ArrayList<EmployeePtoConfig>();
        if (viewList != null) {
            for (EmployeePtoConfigView view : viewList) {
                list.add(getPtoConfigFromView(view, employeeId));
            }
        }
        return list;
    }

    private List<EmployeePtoConfigView> getViewListFromPtoConfigList(
            List<EmployeePtoConfig> list) {
        List<EmployeePtoConfigView> viewList = new ArrayList<EmployeePtoConfigView>();
        if (list != null) {
            for (EmployeePtoConfig ptoConfig : list) {
                viewList.add(getViewFromPtoConfig(ptoConfig));
            }
        }
        return viewList;
    }

    private EmployeePtoConfig getPtoConfigFromView(EmployeePtoConfigView view,
            Integer employeeId) {
        EmployeePtoConfig ptoConfig = new EmployeePtoConfig();
        ptoConfig.setEmployeeId(employeeId);
        ptoConfig.setCostCode(view.getCostCode());
        ptoConfig.setYearlyAllocatedHours(view.getYearlyAllocatedHours());
        ptoConfig.setCarryOverAllowedHours(view.getCarryOverAllowedHours());
        return ptoConfig;
    }

    private EmployeePtoConfigView getViewFromPtoConfig(
            EmployeePtoConfig ptoConfig) {
        EmployeePtoConfigView view = new EmployeePtoConfigView();
        view.setCostCode(ptoConfig.getCostCode());
        view.setYearlyAllocatedHours(ptoConfig.getYearlyAllocatedHours());
        view.setCarryOverAllowedHours(ptoConfig.getCarryOverAllowedHours());
        return view;
    }
}
