package com.vit.lmd.tripMS.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tripId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String pickupAddress;
    @Transient
    //@JsonIgnore
    //@ElementCollection
    private List<StopDTO> stops;
    private int vehicleId;
    private int driverId;

    @Override
    public String toString() {
        return "Trip{" +
                "tripId=" + tripId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", pickupAddress='" + pickupAddress + '\'' +
                ", stops=" + stops +
                ", vehicleId=" + vehicleId +
                ", driverId=" + driverId +
                '}';
    }
}
