package com.vit.lmd.tripMS.Entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripDTO {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String pickupAddress;
    private int vehicleId;
}
