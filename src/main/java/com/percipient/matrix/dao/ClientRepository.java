package com.percipient.matrix.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.percipient.matrix.domain.Client;

public interface ClientRepository {

	public List<Client> getClients();

}

@Repository
class ClientRepositoryImpl implements ClientRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<Client> getClients() {
		String query = "from Client";
		return (List<Client>) sessionFactory.getCurrentSession().createQuery(query)
				.list();
	}

}
