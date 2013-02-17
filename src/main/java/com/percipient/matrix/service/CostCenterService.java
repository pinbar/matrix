package com.percipient.matrix.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.percipient.matrix.dao.CostCenterRepository;
import com.percipient.matrix.display.CostCenterView;
import com.percipient.matrix.domain.CostCenter;

public interface CostCenterService {

	public List<CostCenterView> getCostCenters();

}

@Service
class CostCenterServiceImpl implements CostCenterService {

	@Autowired
	private CostCenterRepository costCenterRepository;

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

	private CostCenterView getCostCenterViewFromCostCenter(CostCenter costCenter) {

		CostCenterView costCenterView = new CostCenterView();
		costCenterView.setCostCode(costCenter.getCostCode());
		costCenterView.setName(costCenter.getName());
		return costCenterView;
	}

}
