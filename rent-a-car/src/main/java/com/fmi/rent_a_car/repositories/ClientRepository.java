package com.fmi.rent_a_car.repositories;

import com.fmi.rent_a_car.entities.Client;
import com.fmi.rent_a_car.mappers.ClientRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ClientRepository {

    private JdbcTemplate db;

    public ClientRepository(JdbcTemplate db) {
        this.db = db;
    }

    public boolean addClient(Client client) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO td_clients (name, address, phone, age, has_accidents) VALUES (?, ?, ?, ?, ?)");

        int createdClient = db.update(sql.toString(),
                                client.getName(),
                                client.getAddress(),
                                client.getPhone(),
                                client.getAge(),
                                client.getHasAccidents()
        );

        return createdClient > 0;
    }

    public Client findById(int id) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM td_clients WHERE id = ? AND is_active = 1");

        var clientCollection = db.query(sql.toString(), new ClientRowMapper(), id);
        if (clientCollection.isEmpty()) {
            return null;
        }

        return clientCollection.get(0);
    }

}
