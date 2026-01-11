package com.vit.lmd.tripMS.ExternalService;


import com.vit.lmd.tripMS.Entity.StopDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="STOP-SERVICE")
public interface stopMS {

    //get stop by id
    @GetMapping("/stops/{stopId}")
    StopDTO getStopById(@PathVariable("stopId") int stopId);

    //get stops by trip id
    @GetMapping("/stops/trip/{tripId}")
    List<StopDTO> getStopsByTripId(@PathVariable("tripId") int tripId);

    //get all stops
    @GetMapping("/stops/all")
    StopDTO[] getAllStops();
}
