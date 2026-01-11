package com.vit.lmd.driverMS.Entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private int driverId;
    private String vehicleStatus;
}
