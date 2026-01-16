package com.vit.lmd.stopMS.Controller;


import com.vit.lmd.stopMS.Entity.Stop;
import com.vit.lmd.stopMS.Exception.ResourceNotFoundException;
import com.vit.lmd.stopMS.Service.StopServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stops")
public class StopController {

    @Autowired
    private StopServiceImpl stopServiceImpl;

    private static final Logger logger = LoggerFactory.getLogger(StopController.class);

    @GetMapping("/all")
    public ResponseEntity<List<Stop>> getAllStops() {
        logger.info("Fetching all stops");
        List<Stop> stops = stopServiceImpl.findAllStops();
      //  logger.info("Total stops found: {}", stops.size());
        return new ResponseEntity<>(stops, HttpStatus.OK);
    }

    @GetMapping("trip/{tripId}")
    public ResponseEntity<List<Stop>> getStopsByTripId(@PathVariable("tripId") int tripId) throws ResourceNotFoundException {
        logger.info("Fetching stops for trip with id: {}", tripId);
        List<Stop> stops = stopServiceImpl.findByTripId(tripId);
        logger.info("Total stops found for trip {}: {}", tripId, stops.size());
        logger.info("Total stops found for trip are listed below{}: {}", tripId, stops.size());
        return new ResponseEntity<>(stops, HttpStatus.OK);
    }

    @GetMapping("/stop/{stopId}")
    public ResponseEntity<Stop> getStopByStopId(@PathVariable("stopId") int stopId) throws ResourceNotFoundException {
       logger.info("Fetching stop with id: {}", stopId);
        Stop stop = stopServiceImpl.findByStopId(stopId);
        logger.info("Stop found: {}", stop);
        return new ResponseEntity<>(stop, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Stop> addStop(@RequestBody Stop stop) {
        logger.info("Adding new stop: {}", stop);
        Stop savedStop = stopServiceImpl.addStops(stop);
        logger.info("Stop added successfully with id: {}", savedStop.getStopId());
        return new ResponseEntity<>(savedStop, HttpStatus.CREATED);
    }

    @PutMapping("/update/{stopId}")
    public ResponseEntity<Stop> updateStop(@PathVariable("stopId") int stopId, @RequestBody Stop stop) throws ResourceNotFoundException {
       logger.info("Updating stop with id: {}", stopId);
        Stop updatedStop = stopServiceImpl.updateStops(stopId, stop);
        logger.info("Stop updated successfully: {}", updatedStop);
        return new ResponseEntity<>(updatedStop, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{stopId}")
    public ResponseEntity<String> deleteStopByStopId(@PathVariable("stopId") int stopId) throws ResourceNotFoundException {
       logger.info("Deleting stop with id: {}", stopId);
        Stop existingStop = stopServiceImpl.findByStopId(stopId);
        if (existingStop == null) {
            logger.info("Stop not found for stopId: {}", stopId);
            return new ResponseEntity<>("Stop not found for stopId: " + stopId, HttpStatus.NOT_FOUND);
        }
        stopServiceImpl.deleteByStopId(stopId);
        logger.info("Stop deleted successfully for stopId: {}", stopId);
        return new ResponseEntity<>("Stop deleted successfully having stopId: "+stopId, HttpStatus.OK);
    }

    @DeleteMapping("/delete/trip/{tripId}")
    public ResponseEntity<String> deleteStopsByTripId(@PathVariable("tripId") int tripId) throws ResourceNotFoundException {
       logger.info("Deleting stops for tripId: {}", tripId);
        List<Stop> existingStops = stopServiceImpl.findByTripId(tripId);
        if (existingStops.isEmpty()) {
            logger.info("Stops not found for tripId: {}", tripId);
            return new ResponseEntity<>("Stops not found for tripId: " + tripId, HttpStatus.NOT_FOUND);
        }
        stopServiceImpl.deleteByTripId(tripId);
        logger.info("Stops deleted successfully for tripId: {}", tripId);
        return new ResponseEntity<>("Stops deleted successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<String> deleteAllStops() {
        logger.info("Deleting all stops");
        List<Stop> existingStops = stopServiceImpl.findAllStops();
        if (existingStops.isEmpty()) {
            logger.info("No stops to delete");
            return new ResponseEntity<>("No stops to delete", HttpStatus.NOT_FOUND);
        }
        stopServiceImpl.deleteAll();
        logger.info("All stops deleted successfully");
        return new ResponseEntity<>("All stops deleted successfully", HttpStatus.OK);
    }

}
