package com.fmi.rent_a_car.controllers;

import com.fmi.rent_a_car.entities.Client;
import com.fmi.rent_a_car.http.AppResponse;
import com.fmi.rent_a_car.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/clients")
    public ResponseEntity<?> createClient(@RequestBody Client client) {
        if (clientService.createClient(client)){
            return AppResponse.success()
                    .withMessage("Client created successfully")
                    .build();
        }

        return AppResponse.error()
                .withMessage("Problem creating the client")
                .build();

    }

}
