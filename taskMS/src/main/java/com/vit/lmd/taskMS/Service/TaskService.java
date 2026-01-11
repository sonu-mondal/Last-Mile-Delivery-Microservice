package com.vit.lmd.taskMS.Service;

import com.vit.lmd.taskMS.Entity.Task;
import com.vit.lmd.taskMS.Exception.ResourceNotFoundException;

import java.util.List;

public interface TaskService {

    //get all tasks
    public List<Task> getAllTasks();

    //get task by taskId
    public Task getTaskById(int taskId) throws ResourceNotFoundException;

    //get tasks by driverId
    public List<Task> getTasksByDriverId(int driverId) throws ResourceNotFoundException;

    //get tasks by tripId
    public List<Task> getTasksByTripId(int tripId) throws ResourceNotFoundException;

    //create task
    public Task createTask(Task task);

    //update task
    public Task updateTask(int taskId, Task task) throws ResourceNotFoundException;

    //delete task by taskId
    public void deleteTaskById(int taskId) throws ResourceNotFoundException;

    //delete all tasks
    public void deleteAllTasks() throws ResourceNotFoundException;

    //get tasks by taskStatus
    public List<Task> getTasksByTaskStatus(String taskStatus) throws ResourceNotFoundException;


}
