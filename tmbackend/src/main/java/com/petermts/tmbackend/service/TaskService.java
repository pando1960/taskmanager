package com.petermts.tmbackend.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.petermts.tmbackend.domain.Task;
import com.petermts.tmbackend.domain.TaskRepository;

import jakarta.transaction.Transactional;

@Service
public class TaskService {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
	
	private TaskRepository taskRepository;
	
	public TaskService(TaskRepository taskRepository) {
		super();
		this.taskRepository = taskRepository;
	}

	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}
	
	public Task getTaskById(Long taskId) {
		return taskRepository.findById(taskId).get();
	}
	
	public List<Task> getTasksByStatus(Long statusId) {
		return taskRepository.findByStatusId(statusId);
	}
	
	@Transactional
	public void createTask(Task task) {
		logger.info("Creating task: " + task.toString());	
		taskRepository.save(task);
	}

	@Transactional
	public void updateTask(Task task) {
		logger.info("Updating task: " + task.toString());	
		taskRepository.save(task);
	}
	
	@Transactional
	public void removeTask(Task task) {
		logger.info("Removing task: " + task.toString());	
		taskRepository.delete(task);
	}
	

}
