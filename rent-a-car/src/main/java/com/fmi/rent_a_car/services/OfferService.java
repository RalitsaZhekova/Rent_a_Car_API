package com.fmi.rent_a_car.services;

import com.fmi.rent_a_car.entities.Car;
import com.fmi.rent_a_car.entities.Client;
import com.fmi.rent_a_car.entities.Offer;
import com.fmi.rent_a_car.repositories.CarRepository;
import com.fmi.rent_a_car.repositories.ClientRepository;
import com.fmi.rent_a_car.repositories.OfferRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class OfferService {

    private OfferRepository offerRepository;
    private CarRepository carRepository;
    private ClientRepository clientRepository;

    public OfferService(OfferRepository offerRepository, CarRepository carRepository, ClientRepository clientRepository) {
        this.offerRepository = offerRepository;
        this.carRepository = carRepository;
        this.clientRepository = clientRepository;
    }

    public boolean createOffer(Offer offer) {

        Client client = clientRepository.findById(offer.getClientId());
        if (client == null) {
            throw new IllegalArgumentException("Client not found");
        }

        Car car = carRepository.getCar(offer.getCarId());
        if (car == null) {
            throw new IllegalArgumentException("Car not found");
        }

        String clientCity = getCityFromAddress(client.getAddress());
        if (!clientCity.equalsIgnoreCase(car.getCity())) {
            throw new IllegalArgumentException("Cannot rent a car from a different city");
        }

        float totalPrice = calculatePrice(
                car.getPricePerDay(),
                offer.getStartDate(),
                offer.getEndDate(),
                client.getHasAccidents()
        );

        offer.setTotalPrice(totalPrice);

        return offerRepository.createOffer(offer) > 0;

    }

    public List<Map<String, Object>> getOffersByClientId(int clientId) {
        List<Map<String, Object>> offers = offerRepository.getAllOffersByClientId(clientId);

        if (offers.isEmpty()) {
            throw new IllegalArgumentException("No offers found for client");
        }

        return offers;
    }

    public List<Map<String, Object>> getOfferById(int offerId) {
        List<Map<String, Object>> offer = offerRepository.findOfferById(offerId);

        if (offer.isEmpty()) {
            throw new IllegalArgumentException("Offer not found");
        }

        return offer;
    }

    public boolean deleteOffer(int id) {
        int rowsUpdated = offerRepository.deleteOffer(id);
        return rowsUpdated == 1;
    }

    public boolean acceptOffer(int id) {
        int rowsUpdated = offerRepository.acceptOffer(id);
        return rowsUpdated == 1;
    }

    private String getCityFromAddress(String address) {
        List<String> availableCities = List.of("Sofia", "Plovdiv", "Burgas", "Varna");
        for (String city : availableCities) {
            if (address.contains(city)) {
                return city;
            }
        }
        return null;
    }

    private float calculatePrice(float pricePerDay, LocalDate startDate, LocalDate endDate, int hasAccidents) {

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and End date must be specified");
        }

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before Start date");
        }

        int rentalDays = (int) DAYS.between(startDate, endDate) + 1;
        float basePrice = pricePerDay * rentalDays;

        int weekendDays = countWeekends(startDate, endDate);
        float weekendCharge = pricePerDay * (weekendDays * 0.1f);

        float totalPrice = basePrice + weekendCharge;

        if (hasAccidents > 0) {
            totalPrice += 200f;
        }

        return totalPrice;

    }

    private int countWeekends(LocalDate startDate, LocalDate endDate) {

        return (int) startDate.datesUntil(endDate.plusDays(1))
                .filter(date ->
                    date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    date.getDayOfWeek() == DayOfWeek.SUNDAY
                ).count();

    }

}
