package com.fmi.rent_a_car.controllers;

import com.fmi.rent_a_car.entities.Offer;
import com.fmi.rent_a_car.http.AppResponse;
import com.fmi.rent_a_car.services.OfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class OfferController {

    private OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping("/offers")
    public ResponseEntity<?> addOffer(@RequestBody Offer offer) {
       if (!offerService.createOffer(offer)) {
           return AppResponse.error()
                   .withMessage("Problem creating the offer")
                   .build();
       }

        return AppResponse.success()
                .withMessage("Offer added successfully")
                .build();
    }

    @GetMapping("/offers/by-client/{clientId}")
    public ResponseEntity<Object> getOffersByClientId(@PathVariable int clientId) {
        List<Map<String, Object>> offers = offerService.getOffersByClientId(clientId);

        return AppResponse.success()
                .withData(offers)
                .build();
    }

    @GetMapping("/offers/{id}")
    public ResponseEntity<Object> getOfferById(@PathVariable int id) {
        List<Map<String, Object>> offer = offerService.getOfferById(id);

        return AppResponse.success()
                .withDataAsArray(offer)
                .build();
    }

    @DeleteMapping("/offers/{id}")
    public ResponseEntity<?> deleteOffer(@PathVariable int id) {
        if (!offerService.deleteOffer(id)) {
            return AppResponse.error()
                    .withMessage("Failed to delete offer")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Offer deleted")
                .build();
    }

    @PutMapping("/offers/{id}")
    public ResponseEntity<?> acceptOffer(@PathVariable int id) {
        if (!offerService.acceptOffer(id)) {
            return AppResponse.error()
                    .withMessage("Failed to accept offer")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Offer accepted")
                .build();
    }
}
