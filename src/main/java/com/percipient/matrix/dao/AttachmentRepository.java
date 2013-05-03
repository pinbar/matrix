package com.percipient.matrix.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.percipient.matrix.domain.TimesheetAttachment;

public interface AttachmentRepository {

    public TimesheetAttachment getTimesheetAttachment(Integer attachmentId);

    public void saveAttachment(TimesheetAttachment attachment);

    public List<TimesheetAttachment> getAllAttachmentsForTimesheet(
            Integer timesheetId);

    public void deleteAllAttachmentsForTimesheet(Integer timesheetId);

    public Boolean deleteTimesheetAttachment(Integer attachmentId);
}

@Repository
class AttachmentRepositoryImpl implements AttachmentRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public TimesheetAttachment getTimesheetAttachment(Integer attachmentId) {
        return (TimesheetAttachment) sessionFactory.getCurrentSession().get(
                TimesheetAttachment.class, attachmentId);
    }

    @Override
    public void saveAttachment(TimesheetAttachment attachment) {
        sessionFactory.getCurrentSession().saveOrUpdate(attachment);
    }

    @Override
    public List<TimesheetAttachment> getAllAttachmentsForTimesheet(
            Integer timesheetId) {
        String query = "from TimesheetAttachment as tsa where tsa.timesheetId = :timesheetId";
        return (List<TimesheetAttachment>) sessionFactory.getCurrentSession()
                .createQuery(query).setParameter("timesheetId", timesheetId)
                .list();
    }

    @Override
    public void deleteAllAttachmentsForTimesheet(Integer timesheetId) {
        String query = "delete from TimesheetAttachment as tsa where tsa.timesheetId = :timesheetId";
        sessionFactory.getCurrentSession().createQuery(query)
                .setParameter("timesheetId", timesheetId).list();
    }

    @Override
    public Boolean deleteTimesheetAttachment(Integer attachmentId) {
        String query = "delete from TimesheetAttachment as tsa where tsa.id = :id";
        int deleted = sessionFactory.getCurrentSession().createQuery(query)
                .setParameter("id", attachmentId).executeUpdate();
        return deleted > 0 ? true : false;
    }
}
