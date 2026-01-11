package com.vit.lmd.vehicleMS.Service;

import com.vit.lmd.vehicleMS.Entity.Vehicle;
import com.vit.lmd.vehicleMS.Entity.VehicleDTO;
import com.vit.lmd.vehicleMS.Enums.VehicleStatus;
import com.vit.lmd.vehicleMS.Enums.VehicleType;
import com.vit.lmd.vehicleMS.Exception.ResourceNotFoundException;
import com.vit.lmd.vehicleMS.Repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public Vehicle getVehicleById(int id) throws ResourceNotFoundException {
        Vehicle vehicle=vehicleRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Vehicle not found with id: "+id));
        return vehicle;
    }

    private static final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);

    @Override
    public List<Vehicle> getAllVehicles() {
        logger.info("Fetching all vehicles from the repository");
        List<Vehicle> vehicles=vehicleRepository.findAll();
        logger.info("Total vehicles retrieved: {}", vehicles.size());
        return vehicles;
    }

     @Override
    public List<Vehicle> getVehiclesByType(VehicleType type) throws ResourceNotFoundException {
        logger.info("Fetching vehicles of type: {}", type);
       List<Vehicle> vehicles=vehicleRepository.findByType(type);
       if(vehicles.isEmpty()){
           logger.info("No vehicles found of type: {}", type);
           throw new ResourceNotFoundException("No vehicles found of type: "+type);
       }
       logger.info("Total vehicles found of type {}: {}", type, vehicles.size());
       return vehicles;
    }


    //find vehicle by capacity greater than or equal to given capacity
    @Override
    public List<Vehicle> getVehiclesByCapacity(int capacity) throws ResourceNotFoundException {
        logger.info("Fetching vehicles with capacity greater than or equal to: {}", capacity);
       List<Vehicle> vehicle=vehicleRepository.findByMinVehicleCapacity(capacity);
       if(vehicle.isEmpty()){
           logger.info("No vehicles found with capacity greater than or equal to: {}", capacity);
           throw new ResourceNotFoundException("No vehicles found with capacity greater than or equal to: "+capacity);
       }
       logger.info("Total vehicles found with capacity greater than or equal to {}: {}", capacity, vehicle.size());
       return vehicle;
    }

    @Override
    public Vehicle addVehicle(Vehicle vehicle) {
        logger.info("Adding new vehicle: {}", vehicle);
        Vehicle newVehicle=vehicleRepository.save(vehicle);
        logger.info("New vehicle added with id: {}", newVehicle.getVehicleId());
        return newVehicle;
    }

    @Override
    public Vehicle updateVehicle(int id, Vehicle vehicle) throws ResourceNotFoundException {
        logger.info("Updating vehicle with id: {}", id);
        Vehicle existingVehicle=vehicleRepository.findById(id).orElseThrow(()
                ->new ResourceNotFoundException("Vehicle not found with id: "+id));
        existingVehicle.setVehicleType(vehicle.getVehicleType());
        existingVehicle.setVehicleCapacity(vehicle.getVehicleCapacity());
        //existingVehicle.setVehicleStatus(vehicle.getVehicleStatus());

        Vehicle updatedVehicle=vehicleRepository.save(existingVehicle);
        logger.info("Vehicle updated successfully: {}", updatedVehicle);
        return updatedVehicle;
    }

    @Override
    public void deleteVehicleById(int id) throws ResourceNotFoundException {
        logger.info("Deleting vehicle with id: {}", id);
        Vehicle existingVehicle=vehicleRepository.findById(id).orElseThrow(()
                ->new ResourceNotFoundException("Vehicle not found with id: "+id));
        logger.info("Vehicle found: {}", existingVehicle);
        vehicleRepository.deleteById(id);
    }

    @Override
    public Vehicle getVehiclesByDriverId(int driverId) throws ResourceNotFoundException {
        logger.info("Fetching vehicle for driverId: {}", driverId);
        Vehicle vehicle=vehicleRepository.findByDriverId(driverId);
        if(vehicle==null){
            logger.info("No vehicle found for driverId: {}", driverId);
            throw new ResourceNotFoundException("No vehicle found for driverId: "+driverId);
        }
        logger.info("Vehicle found for driverId {}: {}", driverId, vehicle);
        return vehicle;
    }

    @Override
    @Transactional
    public VehicleDTO assignAvailableVehicleToDriver(int driverId) throws ResourceNotFoundException {
        logger.info("Assigning available vehicle to driverId: {}", driverId);
        Vehicle vehicle=vehicleRepository.findAvailableVehicles().stream().findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No available vehicle found"));
        vehicle.setDriverId(driverId);
        vehicle.setVehicleStatus(VehicleStatus.OCCUPIED);
        vehicleRepository.save(vehicle);

        //convert Vehicle to VehicleDTO
        logger.info("Vehicle assigned to driverId {}: {}", driverId, vehicle);
        VehicleDTO vehicleDTO=new VehicleDTO();
        vehicleDTO.setVehicleId(vehicle.getVehicleId());
        vehicleDTO.setVehicleType(String.valueOf(vehicle.getVehicleType()));
        vehicleDTO.setVehicleCapacity(vehicle.getVehicleCapacity());
        vehicleDTO.setVehicleStatus(String.valueOf(vehicle.getVehicleStatus()));
        logger.info("Returning VehicleDTO: {}", vehicleDTO);
        return vehicleDTO;
    }


}
