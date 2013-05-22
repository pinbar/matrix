package com.percipient.matrix.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.percipient.matrix.domain.EmployeeCostCenter;

public interface EmployeeCostCenterRepository {

    public EmployeeCostCenter getEmployeeCostCenter(Integer employeeId,
            String costCode);

    public List<EmployeeCostCenter> getAllForEmployee(Integer employeeId);

    public void save(EmployeeCostCenter employeeCostCenter);

    public void save(List<EmployeeCostCenter> empCCList);


    public void delete(EmployeeCostCenter employeeCostCenter);

    public void delete(List<EmployeeCostCenter> empCCList);

    public void deleteAllForEmployee(Integer employeeId);

}

@Repository
class EmployeeCostCenterRepositoryImpl implements EmployeeCostCenterRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public EmployeeCostCenter getEmployeeCostCenter(Integer employeeId,
            String costCode) {
        String query = "from EmployeeCostCenter as ecc where ecc.employeeId = :employeeId and ecc.costCode = :costCode";
        return (EmployeeCostCenter) sessionFactory.getCurrentSession()
                .createQuery(query).setParameter("employeeId", employeeId)
                .setParameter("costCode", costCode).uniqueResult();
    }

    @Override
    public List<EmployeeCostCenter> getAllForEmployee(Integer employeeId) {
        String query = "from EmployeeCostCenter as ecc where ecc.employeeId = :employeeId";
        return (List<EmployeeCostCenter>) sessionFactory.getCurrentSession()
                .createQuery(query).setParameter("employeeId", employeeId)
                .list();
    }

    @Override
    public void save(EmployeeCostCenter empCostCenter) {
        sessionFactory.getCurrentSession().saveOrUpdate(empCostCenter);
    }

    @Override
    public void save(List<EmployeeCostCenter> empCCList) {
        for (EmployeeCostCenter empCC : empCCList) {
            sessionFactory.getCurrentSession().saveOrUpdate(empCC);
        }

    }

    @Override
    public void delete(List<EmployeeCostCenter> empCCList) {
        for (EmployeeCostCenter empCC : empCCList) {
            sessionFactory.getCurrentSession().delete(empCC);
        }
    }

    @Override
    public void delete(EmployeeCostCenter employeeCostCenter) {
        sessionFactory.getCurrentSession().delete(employeeCostCenter);
    }

    @Override
    public void deleteAllForEmployee(Integer employeeId) {
        String query = "delete from EmployeeCostCenter as ecc where ecc.employeeId = :employeeId";
        sessionFactory.getCurrentSession().createQuery(query)
                .setParameter("employeeId", employeeId);
    }

}
