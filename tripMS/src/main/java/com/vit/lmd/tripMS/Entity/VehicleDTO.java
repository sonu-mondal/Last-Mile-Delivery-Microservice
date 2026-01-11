package com.vit.lmd.tripMS.Entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {

  //  private int tripId;
    private int vehicleId;
    private String vehicleType;
    private int vehicleCapacity;
    private String vehicleStatus;

}
