package com.percipient.matrix.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.percipient.matrix.domain.EmployeePtoConfig;

public interface EmployeePtoConfigRepository {

    public EmployeePtoConfig getForEmployeeAndCostCode(Integer employeeId,
            String codeCode);

    public List<EmployeePtoConfig> getAllForEmployee(Integer employeeId);

    public void save(EmployeePtoConfig employeePtoConfig);

    public void save(List<EmployeePtoConfig> empPtoConfigList);

    public void delete(EmployeePtoConfig employeePtoConfig);

    public void delete(List<EmployeePtoConfig> empPtoConfigList);

    public void deleteAllForEmployee(Integer employeeId);

}

@Repository
class EmployeePtoConfigRepositoryImpl implements EmployeePtoConfigRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public EmployeePtoConfig getForEmployeeAndCostCode(Integer employeeId,
            String costCode) {
        String query = "from EmployeePtoConfig as epc where epc.employeeId = :employeeId and epc.costCode = :costCode";
        return (EmployeePtoConfig) sessionFactory.getCurrentSession()
                .createQuery(query).setParameter("employeeId", employeeId)
                .setParameter("costCode", costCode).uniqueResult();
    }

    @Override
    public List<EmployeePtoConfig> getAllForEmployee(Integer employeeId) {
        String query = "from EmployeePtoConfig as epc where epc.employeeId = :employeeId";
        return (List<EmployeePtoConfig>) sessionFactory.getCurrentSession()
                .createQuery(query).setParameter("employeeId", employeeId)
                .list();
    }

    @Override
    public void save(EmployeePtoConfig employeePtoConfig) {
        sessionFactory.getCurrentSession().saveOrUpdate(employeePtoConfig);
    }

    @Override
    public void save(List<EmployeePtoConfig> empPtoConfigList) {
        for (EmployeePtoConfig employeePtoConfig : empPtoConfigList) {
            sessionFactory.getCurrentSession().saveOrUpdate(employeePtoConfig);
        }

    }

    @Override
    public void delete(List<EmployeePtoConfig> employeePtoConfigList) {
        for (EmployeePtoConfig employeePtoConfig : employeePtoConfigList) {
            sessionFactory.getCurrentSession().delete(employeePtoConfig);
        }
    }

    @Override
    public void delete(EmployeePtoConfig employeePtoConfig) {
        sessionFactory.getCurrentSession().delete(employeePtoConfig);
    }

    @Override
    public void deleteAllForEmployee(Integer employeeId) {
        String query = "delete from EmployeePtoConfig as epc where epc.employeeId = :employeeId";
        sessionFactory.getCurrentSession().createQuery(query)
                .setParameter("employeeId", employeeId);
    }

}
