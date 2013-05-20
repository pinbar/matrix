package com.percipient.matrix.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.percipient.matrix.domain.EmployeeCostCenter;

public interface EmployeeCostCenterRepository {

    public EmployeeCostCenter getEmployeeCostCenter(Integer employeeId,
            Integer costCenterId);

    public void save(EmployeeCostCenter EmployeeCostCenterView);

    public List<EmployeeCostCenter> getAllForEmployee(Integer employeeId);

    public void deleteForEmployee(List<EmployeeCostCenter> empCCList);

    public Boolean delete(Integer employeeId, Integer costCenterId);

    public void saveForEmployee(
            List<EmployeeCostCenter> empCCList);
}

@Repository
class EmployeeCostCenterRepositoryImpl implements EmployeeCostCenterRepository {

    @Autowired
    private SessionFactory sessionFactory;
    

    @Override
    public void save(EmployeeCostCenter empCostCenter) {
        sessionFactory.getCurrentSession().saveOrUpdate(empCostCenter);
    }

    @Override
    public List<EmployeeCostCenter> getAllForEmployee(Integer employeeId) {
        String query = "from EmployeeCostCenter as ecc where ecc.employeeId = :employeeId";
        return (List<EmployeeCostCenter>) sessionFactory.getCurrentSession()
                .createQuery(query).setParameter("employeeId", employeeId)
                .list();
    }

    @Override
    public void deleteForEmployee(List<EmployeeCostCenter> empCCList) {
         for (EmployeeCostCenter empCC : empCCList) {
            sessionFactory.getCurrentSession().delete(empCC);
        }
    }

    @Override
    public EmployeeCostCenter getEmployeeCostCenter(Integer employeeId,
            Integer costCenterId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean delete(Integer employeeId, Integer costCenterId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void saveForEmployee(
            List<EmployeeCostCenter> empCCList) {
        for (EmployeeCostCenter empCC : empCCList) {
            sessionFactory.getCurrentSession().saveOrUpdate(empCC);
        }
        
    }
}
