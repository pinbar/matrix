package com.percipient.matrix.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.percipient.matrix.domain.CostCenter;

public interface CostCenterRepository {

    public List<CostCenter> getCostCenters();

    public CostCenter getCostCenter(String costCode);

    public CostCenter getCostCenter(Integer costCenterId);

    public void save(CostCenter costCenter);

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
    public CostCenter getCostCenter(String costCode) {
        String query = "from CostCenter as cc where cc.costCode = :costCode";
        return (CostCenter) sessionFactory.getCurrentSession()
                .createQuery(query).setParameter("costCode", costCode)
                .uniqueResult();
    }

    @Override
    public CostCenter getCostCenter(Integer costCenterId) {
        return (CostCenter) sessionFactory.getCurrentSession().get(
                CostCenter.class, costCenterId);
    }

    @Override
    public void save(CostCenter costCenter) {
        sessionFactory.getCurrentSession().saveOrUpdate(costCenter);
    }

    @Override
    public void deleteCostCenter(CostCenter costCenter) {
        sessionFactory.getCurrentSession().delete(costCenter);
    }

}
