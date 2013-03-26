package com.percipient.matrix.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.percipient.matrix.domain.Employee;
import com.percipient.matrix.domain.Timesheet;

public interface TimesheetRepository {

public Timesheet getTimesheet(Integer id);

public List<Timesheet> getTimesheets(Employee employee);

public void save(Timesheet ts);

public void delete(Timesheet ts);

}

@Repository
class TimesheetRepositoryImpl implements TimesheetRepository {

@Autowired
private SessionFactory sessionFactory;

@Override
public Timesheet getTimesheet(Integer id) {
    return (Timesheet) sessionFactory.getCurrentSession().get(Timesheet.class,
            id);
}

@Override
@SuppressWarnings("unchecked")
public List<Timesheet> getTimesheets(Employee employee) {
    
    String query = "from Timesheet as timesheet where timesheet.employeeId = :employeeId";
    return (List<Timesheet>) sessionFactory.getCurrentSession()
            .createQuery(query).setParameter("employeeId", employee.getId())
            .list();
}

@Override
public void save(Timesheet ts) {
    sessionFactory.getCurrentSession().saveOrUpdate(ts);
}

@Override
public void delete(Timesheet ts) {
    sessionFactory.getCurrentSession().delete(ts);
}

}
