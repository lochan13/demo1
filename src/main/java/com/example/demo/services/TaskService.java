package com.example.demo.services;

import com.example.demo.exceptions.TaskNotFoundException;
import com.example.demo.model.Task;
import com.example.demo.repositories.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepo taskRepo;

    @Autowired
    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    @Transactional
    public Task createTask(Task task) {
        logger.info("Creating task: {}", task);
        return taskRepo.save(task);
    }

    @Transactional
    public void deleteTask(Long id) {
        logger.info("Deleting task with id: {}", id);
        taskRepo.deleteById(id);
    }

    @Transactional
    public Task updateTask(Long id, Task taskDetails) {
        logger.info("Updating task with id: {}", id);
        Task task = taskRepo.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found with id " + id));
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        return taskRepo.save(task);
    }

    public List<Task> retrieveAllTasks() {
        logger.info("Retrieving all tasks");
        return taskRepo.findAll();
    }

    public Task retrieveTask(Long id) {
        logger.info("Retrieving task with id: {}", id);
        return taskRepo.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found with id " + id));
    }
}