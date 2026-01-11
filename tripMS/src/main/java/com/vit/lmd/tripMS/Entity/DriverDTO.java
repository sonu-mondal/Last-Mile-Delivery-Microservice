package com.vit.lmd.tripMS.Entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO {

    private int driverId;
    private String driverName;
    private String driverStatus;
    private int vehicleId;

}
