package com.fmi.rent_a_car.repositories;

import com.fmi.rent_a_car.entities.Car;
import com.fmi.rent_a_car.mappers.CarRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarRepository {

    private JdbcTemplate db;

    public CarRepository(JdbcTemplate db) {
        this.db = db;
    }

    public boolean addCar(Car car) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO td_cars (model, price_per_day, city) VALUES (?, ?, ?)");

        int createdCar = db.update(sql.toString(),
                            car.getModel(),
                            car.getPricePerDay(),
                            car.getCity()
        );

        return createdCar > 0;
    }

    public Car getCar(int id) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM td_cars WHERE id = ? AND is_active = 1");

        var carCollection = db.query(sql.toString(), new CarRowMapper(), id);
        if (carCollection.isEmpty()) {
            return null;
        }

        return carCollection.get(0);
    }

    public List<Car> findAllByCity(String city) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM td_cars WHERE city = ? and is_active = 1");

        return db.query(sql.toString(), new CarRowMapper(), city);
    }

    public int updateCar(Car car) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE td_cars SET model = ?, price_per_day = ?, city = ? WHERE id = ? AND is_active = 1");

        return db.update(sql.toString(),
                car.getModel(),
                car.getPricePerDay(),
                car.getCity(),
                car.getId()
        );
    }

    public int deleteCar(int id) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE td_cars SET is_active = 0 WHERE id = ? AND is_active = 1");

        return db.update(sql.toString(), id);
    }

}
