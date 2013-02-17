package com.percipient.matrix.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.percipient.matrix.dao.ClientRepository;
import com.percipient.matrix.display.ClientView;
import com.percipient.matrix.domain.Client;
import com.percipient.matrix.domain.Employee;


public interface ClientService {

	public List<ClientView> getClients();

}

@Service
class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientRepository clientRepository;

	@Override
	@Transactional
	public List<ClientView> getClients() {
		List<Client> clients = clientRepository.getClients();

		List<ClientView> clientViews = new ArrayList<ClientView>();
		for (Client client : clients) {
			ClientView clientView = getClientViewFromClient(client);
			clientViews.add(clientView);
		}
		return clientViews;
	}

	private ClientView getClientViewFromClient(Client client) {

		ClientView clientView = new ClientView();
		clientView.setId(client.getId());
		clientView.setName(client.getName());
		clientView.setEmployees(getEmplyees(client));
		return clientView;
	}
	
	private Set<String> getEmplyees(Client client) {
		Set<String> employees = new HashSet<String>();
		for(Employee employee : client.getEmployees()) {
			employees.add(employee.getFirstName());
		}
		return employees;
	}

}
