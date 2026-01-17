package com.vit.lmd.driverMS.Controller;

import com.vit.lmd.driverMS.Entity.Driver;
import com.vit.lmd.driverMS.Entity.DriverDTO;
import com.vit.lmd.driverMS.Enums.DriverStatus;
import com.vit.lmd.driverMS.Exception.ResourceNotFoundException;
import com.vit.lmd.driverMS.Service.DriverServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    @Autowired
    private DriverServiceImpl driverServiceImpl;

    public DriverController(DriverServiceImpl driverServiceImpl) {
        this.driverServiceImpl = driverServiceImpl;
    }

    private static final Logger logger = LoggerFactory.getLogger(DriverController.class);

    //assignAvailableDriver
  /*  @PostMapping("/assign-available")
    public ResponseEntity<Driver> assignAvailableDriver() throws ResourceNotFoundException {
        Driver assignedDriver = driverServiceImpl.assignAvailableDriver();
        return new ResponseEntity<>(assignedDriver, HttpStatus.OK);
    }*/

    //get all drivers
    @GetMapping("/all")
    public ResponseEntity<List<Driver>> getAllDrivers() {
        logger.info("Fetching all drivers");
        List<Driver> drivers = driverServiceImpl.getAllDrivers();
        logger.info("Total drivers found: {}", drivers.size());
        return new ResponseEntity<>(drivers, HttpStatus.OK);
    }

    //get driver by id
    @GetMapping("/id/{driverId}")
    public ResponseEntity<Driver> getDriverById(@PathVariable("driverId") int driverId) throws ResourceNotFoundException {
        logger.info("Fetching driver with id: {}", driverId);
        Driver driver = driverServiceImpl.getDriverById(driverId);
        logger.info("Driver found: {}", driver);
        return new ResponseEntity<>(driver, HttpStatus.OK);
    }

    //get drivers by vehicle id
   /* @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<Driver> getDriversByVehicleId(@PathVariable("vehicleId") int vehicleId) throws ResourceNotFoundException {
        Driver driver = driverServiceImpl.getDriversByVehicleId(vehicleId);
        return new ResponseEntity<>(driver, HttpStatus.OK);
    }*/

    //get driver by status
    @GetMapping("/status")
    public ResponseEntity<List<Driver>> getDriversByStatus(@RequestParam DriverStatus status) {
        logger.info("Fetching drivers with status: {}", status);
        List<Driver> drivers = driverServiceImpl.getDriversByStatus(status);
        logger.info("Total drivers found with status {}: {}", status, drivers.size());
        return new ResponseEntity<>(drivers, HttpStatus.OK);
    }

    //get available drivers
    @GetMapping("/available")
    public ResponseEntity<List<Driver>> getAvailableDrivers() {
        logger.info("Fetching available drivers");
        List<Driver> drivers = driverServiceImpl.getAvailableDrivers();
        logger.info("Total available drivers found: {}", drivers.size());
        return new ResponseEntity<>(drivers, HttpStatus.OK);
    }

    //add driver
    @PostMapping("/add")
    public ResponseEntity<Driver> addDriver(@RequestBody Driver driver) {
        logger.info("Adding new driver: {}", driver);
        Driver savedDriver = driverServiceImpl.addDriver(driver);
        logger.info("Driver added successfully: {}", savedDriver);
        return new ResponseEntity<>(savedDriver, HttpStatus.CREATED);
    }

    //update driver
   /* @PutMapping("/{driverId}/update")
    public ResponseEntity<Driver> updateDriver(@PathVariable("driverId") int driverId, @RequestBody Driver driver) throws ResourceNotFoundException {
        Driver existingDriver = driverServiceImpl.getDriverById(driverId);
        // Update fields of existingDriver with values from driver
        existingDriver.setDriverName(driver.getDriverName());
        existingDriver.setDriverStatus(driver.getDriverStatus());
        existingDriver.setVehicleId(driver.getVehicleId());
        Driver updatedDriver = driverServiceImpl.updateDriver(existingDriver);
        return new ResponseEntity<>(updatedDriver, HttpStatus.OK);
    }*/

    //delete driver
    @DeleteMapping("/{driverId}/delete")
    public ResponseEntity<String> deleteDriver(@PathVariable("driverId") int driverId) {
        logger.info("Deleting driver with id: {}", driverId);
        String message;
        try {
            logger.info("Attempting to delete driver with id: {}", driverId);
            driverServiceImpl.deleteDriver(driverId);
            message = "Driver deleted successfully";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            logger.error("Error deleting driver with id {}: {}", driverId, e.getMessage());
            message = e.getMessage();
        }
        logger.info("Deletion process completed for driver with id: {}", driverId);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }




    //release driver
    @PostMapping("/{driverId}/release")
    public ResponseEntity<Driver> releaseDriver(@PathVariable("driverId") int driverId) throws ResourceNotFoundException {
        logger.info("Releasing driver with id: {}", driverId);
        Driver releasedDriver = driverServiceImpl.releaseDriver(driverId);
        logger.info("Driver released successfully: {}", releasedDriver);
        logger.info("Driver released successfully1: {}", releasedDriver);
        return new ResponseEntity<>(releasedDriver, HttpStatus.OK);
    }

    //assign vehicle to driver
    /*@PostMapping("/{driverId}/assign-vehicle/{vehicleId}")
    public ResponseEntity<Driver> assignVehicleToDriver(@PathVariable("driverId") int driverId,
                                                        @PathVariable("vehicleId") int vehicleId) throws ResourceNotFoundException {
        Driver updatedDriver = driverServiceImpl.assignVehicleToDriver(driverId, vehicleId);
        return new ResponseEntity<>(updatedDriver, HttpStatus.OK);
    }*/

    //assign available vehicle to driver
    @PostMapping("/assign-available/{tripId}")
    public ResponseEntity<DriverDTO> assignAvailableVehicleToDriver(@PathVariable("tripId") int tripId) throws ResourceNotFoundException {
       logger.info("Assigning available driver for trip id: {}", tripId);
        return new ResponseEntity<>(driverServiceImpl.assignDriverForTrip(tripId), HttpStatus.OK);
    }

}
