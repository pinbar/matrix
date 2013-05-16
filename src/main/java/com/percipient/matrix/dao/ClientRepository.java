package com.percipient.matrix.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.percipient.matrix.domain.Client;

public interface ClientRepository {

    public List<Client> getClients();

    public Client getClientByName(String clientName);

    public void deleteClient(Client client);

    public Client getClient(Integer clientId);

    public void save(Client client);

}

@Repository
class ClientRepositoryImpl implements ClientRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public List<Client> getClients() {
        String query = "from Client";
        return (List<Client>) sessionFactory.getCurrentSession()
                .createQuery(query).list();
    }

    @Override
    public void save(Client client) {
        sessionFactory.getCurrentSession().saveOrUpdate(client);
    }

    @Override
    public Client getClientByName(String clientName) {
        String query = "from Client as client where client.name = :clientName";
        return (Client) sessionFactory.getCurrentSession().createQuery(query)
                .setParameter("clientName", clientName).uniqueResult();
    }

    @Override
    public Client getClient(Integer clientId) {
        return (Client) sessionFactory.getCurrentSession().get(Client.class,
                clientId);
    }

    @Override
    public void deleteClient(Client client) {
        sessionFactory.getCurrentSession().delete(client);
    }

}
