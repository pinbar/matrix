package com.percipient.matrix.util;

import java.sql.Blob;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HibernateUtil {
    @Autowired
    private SessionFactory sessionFactory;

    public Blob getBlobFromByteArray(byte[] content) {
        Session session = sessionFactory.getCurrentSession();
        return Hibernate.getLobCreator(session).createBlob(content);
    }
}
