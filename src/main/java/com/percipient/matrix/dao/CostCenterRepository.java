package com.percipient.matrix.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.percipient.matrix.domain.CostCenter;

public interface CostCenterRepository {

    public List<CostCenter> getCostCenters();

    public void save(CostCenter costCenter);

    public CostCenter getCostCenter(Integer costCenterId);

    public void deleteCostCenter(CostCenter costCenter);

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

    @Override
    public void save(CostCenter costCenter) {
        sessionFactory.getCurrentSession().saveOrUpdate(costCenter);
    }

    @Override
    public CostCenter getCostCenter(Integer costCenterId) {
        return (CostCenter) sessionFactory.getCurrentSession().get(
                CostCenter.class, costCenterId);
    }

    @Override
    public void deleteCostCenter(CostCenter costCenter) {
        sessionFactory.getCurrentSession().delete(costCenter);
    }

}
