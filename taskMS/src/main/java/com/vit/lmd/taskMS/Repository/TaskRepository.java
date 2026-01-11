package com.vit.lmd.taskMS.Repository;

import com.vit.lmd.taskMS.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    //get tasks by stopId

    @Query("SELECT t FROM Task t WHERE t.driverId =:driverId")
    List<Task> findByDriverId(int driverId);

    //get tasks by tripId
    @Query("SELECT t FROM Task t WHERE t.tripId =:tripId")
    List<Task> findByTripId(int tripId);

    //find by taskStatus
    @Query("SELECT t FROM Task t WHERE t.taskStatus =:taskStatus")
    List<Task> findByTaskStatus(String taskStatus);


}
