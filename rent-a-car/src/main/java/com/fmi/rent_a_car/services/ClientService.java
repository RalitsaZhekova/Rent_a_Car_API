package com.fmi.rent_a_car.services;

import com.fmi.rent_a_car.entities.Client;
import com.fmi.rent_a_car.repositories.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public boolean createClient(Client client) {
        if (client.getAge() < 18 ) {
            throw new IllegalArgumentException("Client must be at least 18 years old");
        }

        return clientRepository.addClient(client);
    }

}
