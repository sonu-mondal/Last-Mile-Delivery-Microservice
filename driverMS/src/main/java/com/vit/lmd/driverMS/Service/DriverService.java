package com.vit.lmd.driverMS.Service;

import com.vit.lmd.driverMS.Entity.Driver;
import com.vit.lmd.driverMS.Enums.DriverStatus;
import com.vit.lmd.driverMS.Exception.ResourceNotFoundException;

import java.util.List;

public interface DriverService {

    //get all drivers
    public List<Driver> getAllDrivers();

    //get driver by id
    public Driver getDriverById(int driverId) throws ResourceNotFoundException;

    //get drivers by vehicle id
    //public Driver getDriversByVehicleId(int vehicleId) throws ResourceNotFoundException;

    //get driver by status
    public List<Driver> getDriversByStatus(DriverStatus status);

    // get available drivers
    public List<Driver> getAvailableDrivers();

    //add driver
    public Driver addDriver(Driver driver);

    //update driver
    public Driver updateDriver(Driver driver);

    //delete driver
    public void deleteDriver(int driverId) throws ResourceNotFoundException;

    //assign available driver
    //public Driver assignAvailableDriver() throws ResourceNotFoundException;

    //release driver
    public Driver releaseDriver(int driverId) throws ResourceNotFoundException;


    //assign vehicle to driver
    //public Driver assignVehicleToDriver(int driverId, int vehicleId) throws ResourceNotFoundException;

    //assign driver the available vehicle
    public Driver assignDriverToAvailableVehicle(int driverId) throws ResourceNotFoundException;

}
