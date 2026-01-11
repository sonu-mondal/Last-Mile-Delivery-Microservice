package com.vit.lmd.driverMS.Entity;

import com.vit.lmd.driverMS.Enums.DriverStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "driver")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int driverId;
    private String driverName;
    @Enumerated(EnumType.STRING)
    private DriverStatus driverStatus;
    @Transient
    private int vehicleId;

    @Override
    public String toString() {
        return "Driver{" +
                "driverId=" + driverId +
                ", driverName='" + driverName + '\'' +
                ", driverStatus=" + driverStatus +
                ", vehicleId=" + vehicleId +
                '}';
    }
}
