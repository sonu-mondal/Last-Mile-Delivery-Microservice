package com.vit.lmd.vehicleMS.Controller;


import com.vit.lmd.vehicleMS.Entity.Vehicle;
import com.vit.lmd.vehicleMS.Entity.VehicleDTO;
import com.vit.lmd.vehicleMS.Enums.VehicleType;
import com.vit.lmd.vehicleMS.Exception.ResourceNotFoundException;
import com.vit.lmd.vehicleMS.Service.VehicleServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleServiceImpl vehicleServiceImpl;

    public VehicleController(VehicleServiceImpl vehicleServiceImpl) {
        this.vehicleServiceImpl = vehicleServiceImpl;
    }

    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable("id") int id) throws ResourceNotFoundException {
        logger.info("Fetching vehicle with id: {}", id);
        Vehicle vehicle=vehicleServiceImpl.getVehicleById(id);
        logger.info("Vehicle found: {}", vehicle);
        return new ResponseEntity<>(vehicle, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        logger.info("Fetching all vehicless");
        List<Vehicle> vehicles= vehicleServiceImpl.getAllVehicles();
        logger.info("Total vehicles found: {}", vehicles.size());
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    @GetMapping("/type")
    public ResponseEntity<List<Vehicle>> getVehiclesByType(@RequestParam VehicleType type) throws IllegalArgumentException, ResourceNotFoundException {
        logger.info("Fetching vehicles of type: {}", type);
        VehicleType vehicleType;
        try {
            vehicleType = VehicleType.valueOf(type.toString().toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid vehicle type provided: {}", type);
            throw new IllegalArgumentException("Invalid vehicle type: " + type +
                    ". Allowed values: " + Arrays.toString(VehicleType.values()));
        }
        List<Vehicle> vehicles = vehicleServiceImpl.getVehiclesByType(vehicleType);
        logger.info("Total vehicles found of type {}: {}", type, vehicles.size());
        return ResponseEntity.ok(vehicles);
    }


    @GetMapping("/capacity")
    public ResponseEntity<List<Vehicle>> getVehiclesByCapacity(@RequestParam int capacity) throws ResourceNotFoundException {
        logger.info("Fetching vehicles with capacity: {}", capacity);
        List<Vehicle> vehicles = vehicleServiceImpl.getVehiclesByCapacity(capacity);
        logger.info("Total vehicles found with capacity {}: {}", capacity, vehicles.size());
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Vehicle> addVehicle(@RequestBody Vehicle vehicle) {
        logger.info("Adding new vehicle: {}", vehicle);
        Vehicle newVehicle = vehicleServiceImpl.addVehicle(vehicle);
        logger.info("Vehicle added successfully: {}", newVehicle);
        return new ResponseEntity<>(newVehicle, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable("id") int id, @RequestBody Vehicle vehicle) throws ResourceNotFoundException {
        logger.info("Updating vehicle with id: {}", id);
        Vehicle updatedVehicle = vehicleServiceImpl.updateVehicle(id, vehicle);
        logger.info("Vehicle updated successfully: {}", updatedVehicle);
        return new ResponseEntity<>(updatedVehicle, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteVehicleById(@PathVariable("id") int id) {
        logger.info("Deleting vehicle with id: {}", id);
        String message;
        try {
            logger.info("Attempting to delete vehicle with id: {}", id);
            vehicleServiceImpl.deleteVehicleById(id);
            message = "Vehicle deleted successfully";
            logger.info("Vehicle deleted successfully with id: {}", id);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            logger.error("Error deleting vehicle with id {}: {}", id, e.getMessage());
            message = e.getMessage();
        }
        logger.info("Returning NOT_FOUND response for vehicle id: {}", id);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    //get vehicles by driver id
    @GetMapping("/driver/{driverId}")
    public ResponseEntity<Vehicle> getVehiclesByDriverId(@PathVariable("driverId") int driverId) throws ResourceNotFoundException {
        logger.info("Fetching vehicle for driver with id: {}", driverId);
        Vehicle vehicle = vehicleServiceImpl.getVehiclesByDriverId(driverId);
        logger.info("Vehicle found for driver {}: {}", driverId, vehicle);
        return new ResponseEntity<>(vehicle, HttpStatus.OK);
    }

    //assign available vehicle to driver
    @PostMapping("/assign/{driverId}")
    public ResponseEntity<VehicleDTO> assignAvailableVehicleToDriver(@PathVariable("driverId") int driverId) throws ResourceNotFoundException {
        logger.info("Assigning available vehicle to driver with id: {}", driverId);
        return new ResponseEntity<>(vehicleServiceImpl.assignAvailableVehicleToDriver(driverId), HttpStatus.OK);
    }

}
