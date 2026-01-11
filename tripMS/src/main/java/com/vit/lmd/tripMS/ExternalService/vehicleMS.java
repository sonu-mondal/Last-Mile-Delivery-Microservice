package com.vit.lmd.tripMS.ExternalService;


import com.vit.lmd.tripMS.Entity.VehicleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="VEHICLE-SERVICE")
public interface vehicleMS {

    @GetMapping("/vehicles/{vehicleId}")
    VehicleDTO getVehicleById(@PathVariable("vehicleId") int vehicleId);

    //get vehicle by driver id
    @PostMapping("/vehicles/driver/{driverId}")
    VehicleDTO getVehicleByDriverId(@RequestParam int tripId);

}
