package com.fmi.rent_a_car.mappers;

import com.fmi.rent_a_car.entities.Offer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OfferRowMapper implements RowMapper<Offer> {
    @Override
    public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {

        Offer offer = new Offer();
        offer.setId(rs.getInt("id"));
        offer.setStartDate(rs.getDate("start_date").toLocalDate());
        offer.setEndDate(rs.getDate("end_date").toLocalDate());
        offer.setTotalPrice(rs.getFloat("total_price"));
        offer.setClientId(rs.getInt("client_id"));
        offer.setCarId(rs.getInt("car_id"));
        offer.setIsAccepted(rs.getInt("is_accepted"));

        return offer;

    }
}
