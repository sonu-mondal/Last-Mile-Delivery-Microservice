package com.vit.lmd.taskMS.Service;

import com.vit.lmd.taskMS.Entity.Task;
import com.vit.lmd.taskMS.Exception.ResourceNotFoundException;
import com.vit.lmd.taskMS.Repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Override
    public List<Task> getAllTasks() {
        logger.info("Fetching all tasks from the repository");
        List<Task> tasks=taskRepository.findAll();
        logger.info("Total tasks retrieved: {}", tasks.size());
        return tasks;
    }

    @Override
    public Task getTaskById(int taskId) throws ResourceNotFoundException {
        logger.info("Fetching task with id: {}", taskId);
        Task task=taskRepository.findById(taskId).
                orElseThrow(()-> new ResourceNotFoundException("Task not found for taskId: " + taskId));
        logger.info("Task found: {}", task);
        return task;
    }

    @Override
    public List<Task> getTasksByDriverId(int driverId) throws ResourceNotFoundException {
        logger.info("Fetching tasks for driver with id: {}", driverId);
        List<Task> tasks=taskRepository.findByDriverId(driverId);
        if(tasks.isEmpty()){
            logger.info("No tasks found for driverId: {}", driverId);
            throw new ResourceNotFoundException("Tasks not found for driverId: " + driverId);
        }
        logger.info("Total tasks found for driver {}: {}", driverId, tasks.size());
        return tasks;
    }

    @Override
    public List<Task> getTasksByTripId(int tripId) throws ResourceNotFoundException {
        logger.info("Fetching tasks for trip with id: {}", tripId);
       List<Task> tasks=taskRepository.findByTripId(tripId);
       if(tasks.isEmpty()) {
              logger.info("No tasks found for tripId: {}", tripId);
           throw new ResourceNotFoundException("Tasks not found for tripId: " + tripId);
       }
       logger.info("Total tasks found for trip {}: {}", tripId, tasks.size());
       return tasks;
    }

    @Override
    public Task createTask(Task task) {
        logger.info("Creating new task: {}", task);
        Task savedTask=taskRepository.save(task);
        logger.info("Task created successfully with id: {}", savedTask.getTaskId());
        return savedTask;
    }

    @Override
    public Task updateTask(int taskId, Task task) throws ResourceNotFoundException {
        logger.info("Updating task with id: {}", taskId);
       Task existingTask=taskRepository.findById(taskId).
               orElseThrow(()-> new ResourceNotFoundException("Task not found for taskId: " + taskId));
      // existingTask.setDescription(task.getDescription());
       existingTask.setDriverId(task.getDriverId());
       existingTask.setTripId(task.getTripId());
       existingTask.setTaskStatus(task.getTaskStatus());
       Task updatedTask=taskRepository.save(existingTask);
       logger.info("Task updated successfully: {}", updatedTask);
       return updatedTask;
    }

    @Override
    public void deleteTaskById(int taskId) throws ResourceNotFoundException {
        logger.info("Deleting task with id: {}", taskId);
        Task existingTask=taskRepository.findById(taskId).
                orElseThrow(()-> new ResourceNotFoundException("Task not found for taskId: " + taskId));
        logger.info("Task found: {}", existingTask);
        taskRepository.delete(existingTask);

    }

    @Override
    public void deleteAllTasks() throws ResourceNotFoundException {
        logger.info("Deleting all tasks from the repository");
        Task existingTask=taskRepository.findAll().stream().findFirst().
                orElseThrow(()-> new ResourceNotFoundException("No tasks available to delete"));
        logger.info("At least one task found: {}", existingTask);
        taskRepository.deleteAll();
    }

    @Override
    public List<Task> getTasksByTaskStatus(String taskStatus) throws ResourceNotFoundException {
        logger.info("Fetching tasks with status: {}", taskStatus);
        List<Task> tasks=taskRepository.findByTaskStatus(taskStatus);
        if(tasks.isEmpty()) {
            logger.info("No tasks found for taskStatus: {}", taskStatus);
            throw new ResourceNotFoundException("Tasks not found for taskStatus: " + taskStatus);
        }
        logger.info("Total tasks found with status {}: {}", taskStatus, tasks.size());
        return tasks;
    }
}
