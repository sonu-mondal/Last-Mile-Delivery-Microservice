package com.vit.lmd.driverMS.Service;

import com.vit.lmd.driverMS.Entity.Driver;
import com.vit.lmd.driverMS.Entity.DriverDTO;
import com.vit.lmd.driverMS.Entity.VehicleDTO;
import com.vit.lmd.driverMS.Enums.DriverStatus;
import com.vit.lmd.driverMS.Exception.ResourceNotFoundException;
import com.vit.lmd.driverMS.ExternalService.vehicleMS;
import com.vit.lmd.driverMS.Repository.DriverRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private vehicleMS vehicleMS;

    public DriverServiceImpl(DriverRepository driverRepository, vehicleMS vehicleMS) {
        this.driverRepository = driverRepository;
        this.vehicleMS = vehicleMS;
    }
    private static final Logger logger = LoggerFactory.getLogger(DriverServiceImpl.class);



    @Transactional
    public DriverDTO assignDriverForTrip(int tripId) throws ResourceNotFoundException {
        //find an available driver
        logger.info("Assigning available driver for trip id: {}", tripId);
        Driver driver=driverRepository.findAll().stream()
                .filter(d -> d.getDriverStatus()== DriverStatus.AVAILABLE)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No available drivers found"));
        //assign driver status to BUSY
        logger.info("Available driver found with id: {}, setting status to BUSY", driver.getDriverId());
        driver.setDriverStatus(DriverStatus.BUSY);
        Driver updatedDriver=driverRepository.save(driver);
        //call vehicle ms to get vehicle assigned to driver
        logger.info("Calling VehicleMS to assign available vehicle to driver id: {}", driver.getDriverId());
        VehicleDTO vehicleDTO=vehicleMS.assignAvailableVehicleToDriver(driver.getDriverId());
        if(vehicleDTO==null) {
            driver.setDriverStatus(DriverStatus.AVAILABLE);
            driverRepository.save(driver);
            logger.error("No available vehicles found, driver release back to AVAILABLE status");
            throw new ResourceNotFoundException("No available vehicles found, driver release back to AVAILABLE status");
        }
        //assign vehicle to driver for the trip
        logger.info("Vehicle assigned to driver id: {} is vehicle id: {}", driver.getDriverId(), vehicleDTO.getVehicleId());
        driver.setVehicleId(vehicleDTO.getVehicleId());
        driver.setDriverStatus(DriverStatus.BUSY);
        updatedDriver=driverRepository.save(driver);

        //prepare DriverDTO to return
        logger.info("Preparing DriverDTO to return for driver id: {}", updatedDriver.getDriverId());
        DriverDTO driverDTO=new DriverDTO();
       driverDTO.setDriverId(updatedDriver.getDriverId());
       driverDTO.setDriverName(updatedDriver.getDriverName());
       driverDTO.setDriverStatus(String.valueOf(updatedDriver.getDriverStatus()));
       driverDTO.setVehicleId(updatedDriver.getVehicleId());
       logger.info("DriverDTO prepared: {}", driverDTO);
        return driverDTO;
    }


    @Override
    public List<Driver> getAllDrivers() {
        logger.info("Fetching all drivers");
        List<Driver> drivers=driverRepository.findAll();
        logger.info("Total drivers found: {}", drivers.size());
        return drivers;
    }

    @Override
    public Driver getDriverById(int driverId) throws ResourceNotFoundException {
        logger.info("Fetching driver with id: {}", driverId);
        Driver driver=driverRepository.findById(driverId).orElseThrow(()
                -> new ResourceNotFoundException("Driver not found with id: "+driverId));
        logger.info("Driver found: {}", driver);
        return driver;
    }

   /* @Override
    public Driver getDriversByVehicleId(int vehicleId) throws ResourceNotFoundException {
        Driver driver=driverRepository.findByVehicleId(vehicleId).orElseThrow(()
                -> new ResourceNotFoundException("Driver not found with vehicle id: "+vehicleId));
        return driver;
    }*/

    @Override
    public List<Driver> getDriversByStatus(DriverStatus status) {
        logger.info("Fetching drivers with status: {}", status);
        List<Driver> drivers=driverRepository.findAll().stream()
                .filter(driver -> driver.getDriverStatus()==status)
                .toList();
        logger.info("Total drivers found with status {}: {}", status, drivers.size());
        return drivers;
    }

    @Override
    public List<Driver> getAvailableDrivers() {
        logger.info("Fetching available drivers");
       List<Driver> drivers=driverRepository.findAll().stream()
               .filter(driver -> driver.getDriverStatus()==DriverStatus.AVAILABLE)
               .toList();
       logger.info("Total available drivers found: {}", drivers.size());
       return drivers;
    }

    @Override
    public Driver addDriver(Driver driver) {
        logger.info("Adding new driver: {}", driver);
        Driver savedDriver=driverRepository.save(driver);
        logger.info("Driver added successfully with id: {}", savedDriver.getDriverId());
        return savedDriver;
    }

    @Override
    public Driver updateDriver(Driver driver) {
        logger.info("Updating driver with id: {}", driver.getDriverId());
        Driver updatedDriver=driverRepository.save(driver);
        logger.info("Driver updated successfully: {}", updatedDriver);
        return updatedDriver;
    }

    @Override
    public void deleteDriver(int driverId) throws ResourceNotFoundException {
        logger.info("Deleting driver with id: {}", driverId);
        Driver driver=driverRepository.findById(driverId).orElseThrow(()
                -> new ResourceNotFoundException("Driver not found with id: "+driverId));
        logger.info("Driver found: {}, deleting now", driver);
        driverRepository.delete(driver);
    }

   /* @Override
    public Driver assignAvailableDriver() throws ResourceNotFoundException {
       Driver driver=driverRepository.findAll().stream()
               .filter(d -> d.getDriverStatus()==DriverStatus.AVAILABLE)
               .findFirst()
               .orElseThrow(() -> new ResourceNotFoundException("No available drivers found"));
       driver.setDriverStatus(DriverStatus.BUSY);
       Driver updatedDriver=driverRepository.save(driver);
       return updatedDriver;
    }*/

    @Override
    public Driver releaseDriver(int driverId) throws ResourceNotFoundException {
        logger.info("Releasing driver with id: {}", driverId);
        Driver driver=driverRepository.findById(driverId).orElseThrow(()
                -> new ResourceNotFoundException("Driver not found with id: "+driverId));
        driver.setDriverStatus(DriverStatus.AVAILABLE);
        Driver updatedDriver=driverRepository.save(driver);
        logger.info("Driver released successfully: {}", updatedDriver);
        return updatedDriver;
    }

    /*@Override
    public Driver assignVehicleToDriver(int driverId, int vehicleId) throws ResourceNotFoundException {
        Driver driver=driverRepository.findById(driverId).orElseThrow(()
                -> new ResourceNotFoundException("Driver not found with id: "+driverId));
       // driver.setVehicleId(vehicleId);
        Driver updatedDriver=driverRepository.save(driver);
        return updatedDriver;
    }*/

    //assign available vehicle to driver using feign client call to vehicle ms
    @Override
    public Driver assignDriverToAvailableVehicle(int driverId) throws ResourceNotFoundException {
        logger.info("Assigning available vehicle to driver with id: {}", driverId);
        Driver driver=driverRepository.findById(driverId).orElseThrow(()
                -> new ResourceNotFoundException("Driver not found with id: "+driverId));
        //call vehicle ms to get available vehicle
        logger.info("Calling VehicleMS to assign available vehicle to driver id: {}", driverId);
        VehicleDTO vehicleDTO=vehicleMS.assignAvailableVehicleToDriver(driverId);
        if(vehicleDTO==null) {
            logger.error("No available vehicles found");
            throw new ResourceNotFoundException("No available vehicles found");
        }
        //driver.setVehicleId(vehicleDTO.getId());// driver wont save vehicle id in db, it will just call to get vehicle detals
        driver.setDriverStatus(DriverStatus.BUSY);
        Driver updatedDriver=driverRepository.save(driver);
        logger.info("Vehicle assigned to driver id: {} is vehicle id: {}", driverId, vehicleDTO.getVehicleId());
        return updatedDriver;
    }


}
