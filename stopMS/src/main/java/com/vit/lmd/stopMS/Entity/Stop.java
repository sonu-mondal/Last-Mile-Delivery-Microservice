package com.vit.lmd.stopMS.Entity;

import com.vit.lmd.stopMS.Enums.StopType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "stops")
public class Stop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stopId;
    private int tripId;
    private String address;
    private String items;
    @Enumerated(EnumType.STRING)
    private StopType stopType;

    @Override
    public String toString() {
        return "Stop{" +
                "stopId=" + stopId +
                ", tripId=" + tripId +
                ", address='" + address + '\'' +
                ", items='" + items + '\'' +
                ", stopType=" + stopType +
                '}';
    }
}
