package com.fmi.rent_a_car.repositories;

import com.fmi.rent_a_car.entities.Offer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class OfferRepository {

    private JdbcTemplate db;

    private final String baseSQL = """
        SELECT o.id AS offer_id,
           o.start_date,
           o.end_date,
           o.total_price,
           o.is_accepted,
           c.name AS client_name,
           c.address AS client_address,
           c.phone AS client_phone,
           car.model AS car_model
        FROM td_offers o
        JOIN td_clients c ON o.client_id = c.id
        JOIN td_cars car ON o.car_id = car.id
        WHERE o.is_active = 1
    """;

    public OfferRepository(JdbcTemplate db) {
        this.db = db;
    }

    public int createOffer(Offer offer) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO td_offers (start_date, end_date, total_price, client_id, car_id, is_accepted) " +
                "VALUES (?, ?, ?, ?, ?, ?)");

        return db.update(sql.toString(),
                offer.getStartDate(),
                offer.getEndDate(),
                offer.getTotalPrice(),
                offer.getClientId(),
                offer.getCarId(),
                offer.getIsAccepted());
    }

    public List<Map<String, Object>> getAllOffersByClientId(int clientId) {
        StringBuilder sql = new StringBuilder();
        sql.append(baseSQL).append(" AND o.client_id = ?");

        return db.queryForList(sql.toString(), clientId);
    }

    public List<Map<String, Object>> findOfferById(int offerId) {
        StringBuilder sql = new StringBuilder();
        sql.append(baseSQL).append(" AND o.id = ?");

        return db.queryForList(sql.toString(), offerId);
    }

    public int deleteOffer(int offerId) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE td_offers SET is_active = 0 WHERE id = ? AND is_active = 1");

        return db.update(sql.toString(), offerId);
    }

    public int acceptOffer(int offerId) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE td_offers SET is_accepted = 1 WHERE id = ? AND is_accepted = 0 AND is_active = 1");

        return db.update(sql.toString(), offerId);
    }

}
