package com.vit.lmd.tripMS.ExternalService;


import com.vit.lmd.tripMS.Entity.DriverDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="DRIVER-SERVICE")
public interface driverMS {

    @GetMapping("/drivers/{driverId}")
    DriverDTO getDriverById(@PathVariable("driverId") int driverId);

    //assign available driver
    @PostMapping("/drivers/assign-available/{tripId}")
    DriverDTO assignAvailableDriver(@PathVariable int tripId);

}
