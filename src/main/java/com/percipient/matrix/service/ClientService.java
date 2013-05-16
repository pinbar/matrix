package com.percipient.matrix.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.percipient.matrix.dao.ClientRepository;
import com.percipient.matrix.domain.Client;
import com.percipient.matrix.view.ClientView;

public interface ClientService {

    public List<ClientView> getClients();

    public ClientView getClient(Integer clientId);

    public void saveClient(ClientView clientView);

    public void deleteClient(ClientView clientView);

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

    @Override
    @Transactional
    public ClientView getClient(Integer clientId) {
        Client client = clientRepository.getClient(clientId);
        ClientView clientView = getClientViewFromClient(client);
        return clientView;
    }

    @Override
    @Transactional
    public void saveClient(ClientView clientView) {
        Client client = getClientFromClientView(clientView);
        clientRepository.save(client);
    }

    @Override
    @Transactional
    public void deleteClient(ClientView clientView) {
        Client client = getClientFromClientView(clientView);
        clientRepository.deleteClient(client);
    }

    private Client getClientFromClientView(ClientView clientView) {
        Client client = null;
        if (clientView.getId() != null) {
            client = clientRepository.getClient(clientView.getId());
        }
        if (client == null) {
            client = new Client();
        }
        client.setName(clientView.getName());
        client.setPhone(clientView.getPhone());
        client.setEmail(clientView.getEmail());
        client.setAddress(clientView.getAddress());
        client.setPrimaryContact(clientView.getPrimaryContact());
        return client;
    }

    private ClientView getClientViewFromClient(Client client) {

        ClientView clientView = new ClientView();
        clientView.setId(client.getId());
        clientView.setName(client.getName());
        clientView.setPhone(client.getPhone());
        clientView.setEmail(client.getEmail());
        clientView.setAddress(client.getAddress());
        clientView.setPrimaryContact(client.getPrimaryContact());
        return clientView;
    }
}
