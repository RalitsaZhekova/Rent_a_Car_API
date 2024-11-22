package com.fmi.rent_a_car.services;

import com.fmi.rent_a_car.entities.Car;
import com.fmi.rent_a_car.entities.Client;
import com.fmi.rent_a_car.repositories.CarRepository;
import com.fmi.rent_a_car.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private CarRepository carRepository;
    private ClientRepository clientRepository;

    private final List<String> availableCities = List.of("Sofia", "Plovdiv", "Burgas", "Varna");

    public CarService(CarRepository carRepository, ClientRepository clientRepository) {
        this.carRepository = carRepository;
        this.clientRepository = clientRepository;
    }

    public boolean addCar(Car car) {
        if (!availableCities.contains(car.getCity())) {
            throw new IllegalArgumentException("Cars can only be added to available cities: Sofia, Plovdiv, Burgas or Varna");
        }

        return carRepository.addCar(car);
    }

    public Car getCar(int id) {
        return carRepository.getCar(id);
    }

    public List<Car> getAllCarsByClientID(int clientID) {
        Client client = clientRepository.findById(clientID);

        if (client == null) {
            throw new IllegalArgumentException("Client not found");
        }

        String city = getCityFromAddress(client.getAddress());

        if (city == null) {
            throw new IllegalArgumentException("No cars available for your city");
        }

        return carRepository.findAllByCity(city);
    }

    public boolean updateCar(Car car) {
        if (!availableCities.contains(car.getCity())) {
            throw new IllegalArgumentException("Cars can only be updated with the available cities: Sofia, Plovdiv, Burgas or Varna");
        }

        int rowsUpdated = carRepository.updateCar(car);
        return rowsUpdated == 1;
    }

    public boolean deleteCar(int id) {
        int rowsUpdated = carRepository.deleteCar(id);
        return rowsUpdated == 1;
    }

    private String getCityFromAddress(String address) {
        for (String city : availableCities) {
            if (address.contains(city)) {
                return city;
            }
        }
        return null;
    }

}
