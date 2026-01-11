package com.vit.lmd.vehicleMS.Service;

import com.vit.lmd.vehicleMS.Entity.Vehicle;
import com.vit.lmd.vehicleMS.Entity.VehicleDTO;
import com.vit.lmd.vehicleMS.Enums.VehicleType;
import com.vit.lmd.vehicleMS.Exception.ResourceNotFoundException;

import java.util.List;

public interface VehicleService {

    //get vehicle by id
    public Vehicle getVehicleById(int id) throws ResourceNotFoundException;

    //get all vehicles
    public List<Vehicle> getAllVehicles();

    //get vehicles by type
    public List<Vehicle> getVehiclesByType(VehicleType type) throws ResourceNotFoundException;;

    //get vehicles by capacity
    public List<Vehicle> getVehiclesByCapacity(int capacity) throws ResourceNotFoundException;

    //add vehicle details
    public Vehicle addVehicle(Vehicle vehicle);

    //update vehicle details
    public Vehicle updateVehicle(int id, Vehicle vehicle) throws ResourceNotFoundException;

    //delete vehicle by id
    public void deleteVehicleById(int id) throws ResourceNotFoundException;

    //get vehicles by driver id
    public Vehicle getVehiclesByDriverId(int driverId) throws ResourceNotFoundException;

    //assign available vehicle
    public VehicleDTO assignAvailableVehicleToDriver(int driverId) throws ResourceNotFoundException;

}
