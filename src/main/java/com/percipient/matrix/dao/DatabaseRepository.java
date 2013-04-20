package com.percipient.matrix.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

public interface DatabaseRepository {

    public List<?> getTableData(String entityName);
}

@Repository
class DatabaseRepositoryImpl implements DatabaseRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public List<?> getTableData(String entityName) {
        String query = "from " + entityName;
        return (List<?>) sessionFactory.getCurrentSession().createQuery(query)
                .list();
    }
}
