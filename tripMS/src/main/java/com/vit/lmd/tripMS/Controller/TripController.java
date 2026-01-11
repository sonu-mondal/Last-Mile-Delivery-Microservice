package com.vit.lmd.tripMS.Controller;


import com.vit.lmd.tripMS.Entity.Trip;
import com.vit.lmd.tripMS.Entity.TripDTO;
import com.vit.lmd.tripMS.Exception.ResourceNotFoundException;
import com.vit.lmd.tripMS.Service.TripServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private TripServiceImpl tripServiceImpl;

    public TripController(TripServiceImpl tripServiceImpl) {
        this.tripServiceImpl = tripServiceImpl;
    }
    private static final Logger logger = LoggerFactory.getLogger(TripController.class);

    @GetMapping("/{tripId}")
    public ResponseEntity<Trip> getTripById(@PathVariable int tripId) throws ResourceNotFoundException {
        logger.info("Fetching trip with id: {}", tripId);
        Trip trip = tripServiceImpl.getTripById(tripId);
        logger.info("Trip found: {}", trip);
        return new ResponseEntity<>(trip, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Trip>> getAllTrips() throws ResourceNotFoundException {
        logger.info("Fetching all trips");
        List<Trip> trips = tripServiceImpl.getAllTrips();
        logger.info("Total trips found: {}", trips.size());
        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<Trip>> getTripsByDriverId(@PathVariable int driverId) {
        logger.info("Fetching trips for driver with id: {}", driverId);
        List<Trip> trips = tripServiceImpl.getTripsByDriverId(driverId);
        logger.info("Total trips found for driver {}: {}", driverId, trips.size());
        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    @GetMapping("/by-time")
    public ResponseEntity<List<Trip>> getTripsByStartTimeAndEndTime(@RequestParam("startTime") LocalDateTime startTime,
                                                                    @RequestParam("endTime") LocalDateTime endTime) {
        logger.info("Fetching trips between {} and {}", startTime, endTime);
        List<Trip> trips = tripServiceImpl.getTripsByStartTimeAndEndTime(startTime, endTime);
        if (trips == null || trips.isEmpty()) {
            logger.info("No trips found between {} and {}", startTime, endTime);
            return new ResponseEntity<>(trips, HttpStatus.OK);
        }
        logger.info("Total trips found between {} and {}: {}", startTime, endTime, trips.size());
        return ResponseEntity.ok(trips);
    }

    @GetMapping("/by-pickup")
    public ResponseEntity<List<Trip>> getTripsByPickupAddress(@RequestParam("pickupAddress")
                                                                  String pickupAddress) {
        logger.info("Fetching trips with pickup address: {}", pickupAddress);
        List<Trip> trips = tripServiceImpl.getTripsByPickupAddress(pickupAddress);
        if (trips == null || trips.isEmpty()) {
            logger.info("No trips found with pickup address: {}", pickupAddress);
            return new ResponseEntity<>(trips, HttpStatus.OK);
        }
        logger.info("Total trips found with pickup address {}: {}", pickupAddress, trips.size());
        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Trip> createTrip(@RequestBody TripDTO tripDTO) throws ResourceNotFoundException {
        logger.info("Creating new trip with data: {}", tripDTO);
        Trip createdTrip = tripServiceImpl.createTrip(tripDTO);
        logger.info("Trip created successfully: {}", createdTrip);
        return new ResponseEntity<>(createdTrip, HttpStatus.CREATED);
    }

    @PutMapping("/{tripId}/update")
    public ResponseEntity<Trip> updateTrip(@PathVariable("tripId") int tripId,@RequestBody Trip trip) throws ResourceNotFoundException {
        logger.info("Updating trip with id: {}", tripId);
        Trip updatedTrip = tripServiceImpl.updateTrip(tripId, trip);
        logger.info("Trip updated successfully: {}", updatedTrip);
        return new ResponseEntity<>(updatedTrip, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{tripId}")
    public ResponseEntity<String> deleteTrip(@PathVariable int tripId) throws ResourceNotFoundException {
        logger.info("Deleting trip with id: {}", tripId);
        tripServiceImpl.deleteTrip(tripId);
        logger.info("Trip deleted successfully with id: {}", tripId);
        return new ResponseEntity<>("Trip deleted successfully with id: "+tripId, HttpStatus.OK);

    }

    ///UPDATED METHOD TO CREATE TRIP WITHOUT DTO
    @PostMapping
    public ResponseEntity<Trip> createTrip2(@RequestBody Trip trip) throws ResourceNotFoundException {
        logger.info("Creating new trip: {}", trip);
        Trip createdTrip = tripServiceImpl.createTrip1(trip);
        logger.info("Trip created successfully: {}", createdTrip);
        return new ResponseEntity<>(createdTrip, HttpStatus.CREATED);
    }
}
