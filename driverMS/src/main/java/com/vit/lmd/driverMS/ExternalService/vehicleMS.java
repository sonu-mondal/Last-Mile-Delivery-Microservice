package com.vit.lmd.driverMS.ExternalService;

import com.vit.lmd.driverMS.Entity.VehicleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "VEHICLE-SERVICE")
public interface vehicleMS {

    //assign available vehicle to driver
    @PostMapping("/vehicles/assign/{driverId}")
    VehicleDTO assignAvailableVehicleToDriver(@PathVariable("driverId") int driverId);
}
