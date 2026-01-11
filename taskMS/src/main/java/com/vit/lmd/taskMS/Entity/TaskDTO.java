package com.vit.lmd.taskMS.Entity;

import lombok.Data;

@Data
public class TaskDTO {

    private int tripId;
    private int driverId;
   // private String description;
    private String taskStatus;
}
