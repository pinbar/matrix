package com.percipient.matrix.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.percipient.matrix.domain.Employee;
import com.percipient.matrix.domain.Timesheet;
import com.percipient.matrix.domain.TimesheetItem;

public interface TimesheetRepository {

    public List<Timesheet> getTimesheetsByStatus(String status);
    
    public Timesheet getTimesheet(Integer id);

    public Timesheet getTimesheet(Employee employee, Date weekEnding);

    public List<Timesheet> getTimesheets(Employee employee);

    public void save(Timesheet ts);

    public void delete(Timesheet ts);

    public void delete(List<Timesheet> timesheetList);

    public TimesheetItem getTimesheetItem(Integer id);

    public void deleteTimesheetItems(Set<TimesheetItem> tmpItemSet);
}

@Repository
class TimesheetRepositoryImpl implements TimesheetRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Timesheet> getTimesheetsByStatus(String status) {
        String query = "from Timesheet as timesheet where timesheet.status = :status";
        return (List<Timesheet>) sessionFactory.getCurrentSession()
                .createQuery(query)
                .setParameter("status", status).list();
    }
    
    @Override
    public Timesheet getTimesheet(Integer id) {
        return (Timesheet) sessionFactory.getCurrentSession().get(
                Timesheet.class, id);
    }

    @Override
    public Timesheet getTimesheet(Employee employee, Date weekEnding) {
        String query = "from Timesheet as timesheet where timesheet.employeeId = :employeeId and timesheet.weekEnding = :weekEnding";
        return (Timesheet) sessionFactory.getCurrentSession()
                .createQuery(query)
                .setParameter("employeeId", employee.getId())
                .setParameter("weekEnding", weekEnding).uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Timesheet> getTimesheets(Employee employee) {

        String query = "from Timesheet as timesheet where timesheet.employeeId = :employeeId";
        return (List<Timesheet>) sessionFactory.getCurrentSession()
                .createQuery(query)
                .setParameter("employeeId", employee.getId()).list();
    }

    @Override
    public void save(Timesheet ts) {
        sessionFactory.getCurrentSession().saveOrUpdate(ts);
    }

    @Override
    public void delete(Timesheet ts) {
        sessionFactory.getCurrentSession().delete(ts);
    }

    @Override
    public void delete(List<Timesheet> timesheetList) {
        for (Timesheet ts : timesheetList) {
            sessionFactory.getCurrentSession().delete(ts);
        }
    }

    @Override
    public TimesheetItem getTimesheetItem(Integer id) {
        return (TimesheetItem) sessionFactory.getCurrentSession().get(
                TimesheetItem.class, id);
    }

    @Override
    public void deleteTimesheetItems(Set<TimesheetItem> timesheetItemList) {
        for (TimesheetItem tsItem : timesheetItemList) {
            sessionFactory.getCurrentSession().delete(tsItem);
        }
    }
}
