package com.percipient.matrix.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.percipient.matrix.dao.ClientRepository;
import com.percipient.matrix.dao.CostCenterRepository;
import com.percipient.matrix.domain.Client;
import com.percipient.matrix.domain.CostCenter;
import com.percipient.matrix.session.AppConfig;
import com.percipient.matrix.view.CostCenterView;

public interface CostCenterService {

    public List<CostCenterView> getCostCenters();

    public Map<String, List<CostCenterView>> getCostCentersGroups();

    public Map<String, List<CostCenterView>> groupCostCenters(
            List<CostCenter> costCenters);

    public List<CostCenterView> getCCViewListFromCostCodes(
            List<String> costCodes);

    public void saveCostCenter(CostCenterView costCenterView);

    public CostCenterView getCostCenter(Integer costCenterId);

    public void deleteGroup(CostCenterView costCenterView);

}

@Service
class CostCenterServiceImpl implements CostCenterService {

    @Autowired
    private CostCenterRepository costCenterRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    @Transactional
    public List<CostCenterView> getCostCenters() {

        List<CostCenter> costCenters = costCenterRepository.getCostCenters();
        List<CostCenterView> costCenterViews = new ArrayList<CostCenterView>();
        for (CostCenter costCenter : costCenters) {
            CostCenterView costCenterView = getCostCenterViewFromCostCenter(costCenter);
            costCenterViews.add(costCenterView);
        }
        return costCenterViews;
    }

    @Override
    @Transactional
    public Map<String, List<CostCenterView>> getCostCentersGroups() {

        List<CostCenter> allCostCenters = costCenterRepository.getCostCenters();
        Map<String, List<CostCenterView>> groupedCostCenters = groupCostCenters(allCostCenters);

        return groupedCostCenters;
    }

    @Override
    @Transactional
    public List<CostCenterView> getCCViewListFromCostCodes(
            List<String> costCodes) {
        List<CostCenterView> costCenterViewList = new ArrayList<CostCenterView>();
        CostCenter costCenter;
        CostCenterView costCenterView;
        for (String costCode : costCodes) {
            costCenter = costCenterRepository.getCostCenter(costCode);
            costCenterView = getCostCenterViewFromCostCenter(costCenter);
            costCenterViewList.add(costCenterView);
        }
        return costCenterViewList;
    }

    @Override
    @Transactional
    public void saveCostCenter(CostCenterView costCenterView) {
        CostCenter costCenter = getCostCenterFromCostCenterView(costCenterView);
        costCenterRepository.save(costCenter);
    }

    @Override
    @Transactional
    public CostCenterView getCostCenter(Integer costCenterId) {
        CostCenter costCenter = costCenterRepository
                .getCostCenter(costCenterId);
        return getCostCenterViewFromCostCenter(costCenter);
    }

    @Override
    @Transactional
    public void deleteGroup(CostCenterView costCenterView) {
        CostCenter costCenter = getCostCenterFromCostCenterView(costCenterView);
        costCenterRepository.deleteCostCenter(costCenter);
    }

    @Override
    @Transactional
    public Map<String, List<CostCenterView>> groupCostCenters(
            List<CostCenter> costCenters) {
        Map<String, List<CostCenterView>> ccGroup = new HashMap<String, List<CostCenterView>>();

        for (CostCenter costCenter : costCenters) {
            CostCenterView costCenterView = new CostCenterView();
            costCenterView.setCostCode(costCenter.getCostCode());
            costCenterView.setName(costCenter.getName());
            costCenterView.setPto(costCenter.getPto());
            String groupName = "";
            if (costCenter.getPto()) {
                groupName = AppConfig.COST_CENTER_GROUP_NAME_PTO;
            } else {
                Client client = clientRepository.getClient(costCenter
                        .getClient().getId());
                groupName = client.getName();
            }
            if (ccGroup.containsKey(groupName)) {
                ccGroup.get(groupName).add(costCenterView);
            } else {
                List<CostCenterView> costCenterViews = new ArrayList<CostCenterView>();
                costCenterViews.add(costCenterView);
                ccGroup.put(groupName, costCenterViews);
            }
        }
        return ccGroup;
    }

    private CostCenterView getCostCenterViewFromCostCenter(CostCenter costCenter) {

        CostCenterView costCenterView = new CostCenterView();
        costCenterView.setId(costCenter.getId());
        costCenterView.setCostCode(costCenter.getCostCode());
        costCenterView.setName(costCenter.getName());
        costCenterView.setPto(costCenter.getPto());
        costCenterView.setClientName(costCenter.getClient().getName());

        return costCenterView;
    }

    private CostCenter getCostCenterFromCostCenterView(
            CostCenterView costCenterView) {

        CostCenter costCenter = null;
        if (costCenterView.getId() != null) {
            costCenter = costCenterRepository.getCostCenter(costCenterView
                    .getId());
        }
        if (costCenter == null) {
            costCenter = new CostCenter();
            Client client = clientRepository.getClientByName(costCenterView
                    .getClientName());
            costCenter.setClient(client);
        }
        costCenter.setCostCode(costCenterView.getCostCode());
        costCenter.setName(costCenterView.getName());
        costCenter.setPto(costCenterView.getPto());

        if (!costCenter.getClient().getName()
                .equalsIgnoreCase(costCenterView.getClientName())) {
            Client client = clientRepository.getClientByName(costCenterView
                    .getClientName());
            costCenter.setClient(client);
        }
        return costCenter;
    }
}