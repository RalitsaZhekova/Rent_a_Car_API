package com.fmi.rent_a_car.controllers;

import com.fmi.rent_a_car.entities.Car;
import com.fmi.rent_a_car.http.AppResponse;
import com.fmi.rent_a_car.services.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CarController {

    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/cars")
    public ResponseEntity<?> addCar(@RequestBody Car car) {
        if (!carService.addCar(car)) {
            return AppResponse.success()
                    .withMessage("Problem creating the car")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Car created successfully")
                .build();
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<?> getCarbyId(@PathVariable int id) {
        Car car = carService.getCar(id);
        if(car == null) {
            return AppResponse.error()
                    .withMessage("Car not found")
                    .build();
        }

        return AppResponse.success()
                .withDataAsArray(car)
                .build();
    }

    @GetMapping("/cars/by-client/{clientID}")
    public ResponseEntity<?> getAllCarsByClientID(@PathVariable int clientID) {
        List<Car> carCollection = carService.getAllCarsByClientID(clientID);
        return AppResponse.success()
                .withData(carCollection)
                .build();
    }

    @PutMapping("/cars")
    public ResponseEntity<?> updateCar(@RequestBody Car car) {
        if (!carService.updateCar(car)) {
            return AppResponse.error()
                    .withMessage("Cannot update car")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Car updated successfully")
                .build();
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable int id) {
        if (!carService.deleteCar(id)) {
            return AppResponse.error()
                    .withMessage("Cannot delete car")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Car deleted successfully")
                .build();
    }

}
