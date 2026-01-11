package com.vit.lmd.vehicleMS.Entity;

import com.vit.lmd.vehicleMS.Enums.VehicleStatus;
import com.vit.lmd.vehicleMS.Enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "vehicle")
public class Vehicle {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vehicleId;
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
    private int vehicleCapacity;
    private int driverId;
    @Enumerated(EnumType.STRING)
    private VehicleStatus vehicleStatus;


}
