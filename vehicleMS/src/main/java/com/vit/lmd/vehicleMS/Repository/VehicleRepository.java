package com.vit.lmd.vehicleMS.Repository;

import com.vit.lmd.vehicleMS.Entity.Vehicle;
import com.vit.lmd.vehicleMS.Enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    //get vehicle by type
    @Query("SELECT v FROM Vehicle v WHERE v.vehicleType = :type")
    List<Vehicle> findByType(VehicleType type);

    //get vehicle bt capacity greater than or equal to given capacity

    @Query("SELECT v FROM Vehicle v WHERE v.vehicleCapacity >= :vehicleCapacity")
    List<Vehicle> findByMinVehicleCapacity(@Param("vehicleCapacity") int vehicleCapacity);

    //get vehicle by driverId
    @Query("SELECT v FROM Vehicle v WHERE v.driverId = :driverId")
    Vehicle findByDriverId(int driverId);

    //find available vehicles
    //@Query("SELECT v FROM Vehicle v WHERE v.vehicleStatus = 'AVAILABLE'")
    @Query("SELECT v FROM Vehicle v WHERE v.vehicleStatus = com.vit.lmd.vehicleMS.Enums.VehicleStatus.AVAILABLE")
    List<Vehicle> findAvailableVehicles();

}
