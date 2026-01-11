package com.vit.lmd.tripMS.Entity;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

   // private int taskId;
    private int tripId;
    private int driverId;
   // private String description;
    private String taskStatus;




}
