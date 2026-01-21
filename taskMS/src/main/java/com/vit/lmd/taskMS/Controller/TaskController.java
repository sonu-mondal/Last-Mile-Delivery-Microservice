package com.vit.lmd.taskMS.Controller;


import com.vit.lmd.taskMS.Entity.Task;
import com.vit.lmd.taskMS.Entity.TaskDTO;
import com.vit.lmd.taskMS.Enums.TaskStatus;
import com.vit.lmd.taskMS.Exception.ResourceNotFoundException;
import com.vit.lmd.taskMS.Service.TaskServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskServiceImpl taskServiceImpl;

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAllTasks() {
        logger.info("Fetching all tasks");
        List<Task> tasks = taskServiceImpl.getAllTasks();
        logger.info("Total tasks found: {}", tasks.size());
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable("taskId") int taskId) throws ResourceNotFoundException {
        logger.info("Fetching task with id: {}", taskId);
        Task task = taskServiceImpl.getTaskById(taskId);
        logger.info("Task found: {}", task);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<Task>> getTasksByStopId(@PathVariable("driverId") int driverId) throws ResourceNotFoundException {
        logger.info("Fetching tasks for driver with id: {}", driverId);
        List<Task> tasks = taskServiceImpl.getTasksByDriverId(driverId);
        logger.info("Total tasks found for driver {}: {}", driverId, tasks.size());
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<List<Task>> getTasksByTripId(@PathVariable("tripId") int tripId) throws ResourceNotFoundException {
        logger.info("Fetching tasks for trip with id: {}", tripId);
        List<Task> tasks = taskServiceImpl.getTasksByTripId(tripId);
        logger.info("Total tasks found for trip {}: {}", tripId, tasks.size());
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

 /*   @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task savedTask = taskServiceImpl.createTask(task);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }*/

    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO taskDTO) {
        logger.info("Creating task with data: {}", taskDTO);
        Task task = new Task();
       // task.setDescription(taskDTO.getDescription());
        task.setDriverId(taskDTO.getDriverId());
        task.setTripId(taskDTO.getTripId());
        task.setTaskStatus(TaskStatus.valueOf(taskDTO.getTaskStatus()));
        Task savedTask = taskServiceImpl.createTask(task);
        logger.info("Task created successfully: {}", savedTask);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable("taskId") int taskId, @RequestBody Task task) throws ResourceNotFoundException {
        logger.info("Updating task with id: {}", taskId);
        Task updatedTask = taskServiceImpl.updateTask(taskId, task);
        logger.info("Task updated successfully: {}", updatedTask);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<String> deleteTaskById(@PathVariable("taskId") int taskId) throws ResourceNotFoundException {
        logger.info("Deleting task with id: {}", taskId);
        taskServiceImpl.deleteTaskById(taskId);
        logger.info("Task deleted successfully with id: {}", taskId);
        return new ResponseEntity<>("Task deleted successfully having taskId: " + taskId, HttpStatus.OK);
    }
    @DeleteMapping("/delete/all")
    public ResponseEntity<String> deleteAllTasks() throws ResourceNotFoundException {
        logger.info("Deleting all tasks");
        taskServiceImpl.deleteAllTasks();
        logger.info("All tasks deleted successfully");
        return new ResponseEntity<>("All tasks deleted successfully", HttpStatus.OK);
    }

    //get tasks by taskStatus
    @GetMapping("/status")
    public ResponseEntity<List<Task>> getTasksByTaskStatus(@RequestParam("taskStatus") String taskStatus) throws ResourceNotFoundException {
        logger.info("Fetching tasks with status: {}", taskStatus);
        List<Task> tasks = taskServiceImpl.getTasksByTaskStatus(taskStatus);
        logger.info("Total tasks found with status {}: {}", taskStatus, tasks.size());
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }
}
