package com.percipient.matrix.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.percipient.matrix.domain.CostCenter;

public interface CostCenterRepository {

	public List<CostCenter> getCostCenters();

}

@Repository
class CostCenterRepositoryImpl implements CostCenterRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<CostCenter> getCostCenters() {
		String query = "from CostCenter";
		return (List<CostCenter>) sessionFactory.getCurrentSession()
				.createQuery(query).list();
	}

}
