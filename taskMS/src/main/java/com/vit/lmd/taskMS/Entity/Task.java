package com.vit.lmd.taskMS.Entity;


import com.vit.lmd.taskMS.Enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int taskId;
    private int tripId;
    private int driverId;
    //private String description;
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", tripId=" + tripId +
                ", driverId=" + driverId +
               // ", description='" + description + '\'' +
                ", taskStatus=" + taskStatus +
                '}';
    }
}


