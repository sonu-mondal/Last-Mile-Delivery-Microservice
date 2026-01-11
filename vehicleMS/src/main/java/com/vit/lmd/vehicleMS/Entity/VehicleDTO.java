package com.vit.lmd.vehicleMS.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {

    private int vehicleId;
    private String vehicleType;
    private int vehicleCapacity;
    private String vehicleStatus;
}
