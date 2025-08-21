package com.petermts.tmbackend.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petermts.tmbackend.domain.Status;
import com.petermts.tmbackend.domain.Task;
import com.petermts.tmbackend.service.StatusService;
import com.petermts.tmbackend.service.TaskService;

import io.swagger.v3.oas.annotations.Hidden;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
	
	private TaskService taskService;
	private StatusService statusService;
	
	public TaskController(TaskService taskService, StatusService statusService) {
		super();
		this.taskService = taskService;
		this.statusService = statusService;
	}

//	@Hidden
	@GetMapping("/all")
	public List<Task> getAllTasks() {
		//Fetch and return all tasks.
		return this.taskService.getAllTasks();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getTask(@PathVariable("id") Long taskId) {
		
		//Fetch and return single task.
		try {
			return ResponseEntity.status(HttpStatus.OK).body(this.taskService.getTaskById(taskId));
		} 
		catch (Exception e) {
			return ResponseEntity.badRequest().body("Failed to find task: " + taskId + " - " + e.getMessage());		
		}
	}
	
	@GetMapping("/status/{id}")
	public ResponseEntity<?> getTasksByStatusId(@PathVariable("id") Long taskStatusId) {
		
		Status status = null;
		
		//Fetch and return single task.
		try {
			
			// find status required.
			status = statusService.getStatusById(taskStatusId);
			
			return  ResponseEntity.status(HttpStatus.OK).body(this.taskService.getTasksByStatus(taskStatusId));
		} 
		catch (Exception e) {
			String description = status == null ? "not found " : status.getStatus();
			return ResponseEntity.badRequest().body("Failed to find task by status: " + description + " - " + e.getMessage());		
		}
	}

	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createTask(@RequestBody Task task) {

		try {
			
			this.taskService.createTask(task);
			
			ObjectMapper mapper = new ObjectMapper();
	        JsonNode json = mapper.readTree("{\"message\": \"Successfully added new task.\"}");

			return ResponseEntity.status(HttpStatus.OK).body(json);
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().body("Failed to add new task: " + e.getMessage());
		}
	}
	
	@PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateTask(@RequestBody Task task) {

		ObjectMapper mapper = new ObjectMapper();
		JsonNode json = null;

		try {
			
			this.taskService.updateTask(task);

	        json = mapper.readTree("{\"message\": \"Successfully updated task.\"}");

			return ResponseEntity.status(HttpStatus.OK).body(json);
		}
		catch (Exception e) {
			
			try {
				String message = "Failed to update task: " + e.getMessage();
				
				json = mapper.readTree("{\"message\": \"" + message + "\"}");
			} 
			catch (JsonProcessingException ex) {
				logger.error("Unable to parse Json to produce error message: " + ex.getMessage());
			}
			
			return ResponseEntity.badRequest().body(json);
		}
	}
	
	@DeleteMapping("/remove/{id}")
	ResponseEntity<?> removeTask(@PathVariable("id") String id) {

		ObjectMapper mapper = new ObjectMapper();
		JsonNode json = null;

		try {
			
			Task task = this.taskService.getTaskById(Long.valueOf(id));
			
			this.taskService.removeTask(task);

		    json = mapper.readTree("{\"message\": \"Successfully removed task.\"}");

		    return ResponseEntity.status(HttpStatus.OK).body(json);
		}
		catch (Exception e) {

			try {
				String message = "Failed to remove task: " + e.getMessage();
				
				json = mapper.readTree("{\"message\": \"" + message + "\"}");
			} 
			catch (JsonProcessingException ex) {
				logger.error("Unable to parse Json to produce error message: " + ex.getMessage());
			}
			
			return ResponseEntity.badRequest().body(json);
		}
	}
	
	


}
