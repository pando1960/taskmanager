package com.petermts.tmbackend;

import java.time.LocalDateTime;
import java.time.Month;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.petermts.tmbackend.domain.Status;
import com.petermts.tmbackend.domain.StatusRepository;
import com.petermts.tmbackend.domain.Task;
import com.petermts.tmbackend.domain.TaskRepository;
import com.petermts.tmbackend.utils.DateUtils;

@SpringBootApplication
public class TmbackendApplication implements CommandLineRunner {
	
	private static final Logger logger = LoggerFactory.getLogger(TmbackendApplication.class);
	private final TaskRepository taskRepository;
	private final StatusRepository statusRepository;
	
	public TmbackendApplication(TaskRepository taskRepository, StatusRepository statusRepository) {
		this.taskRepository = taskRepository;
		this.statusRepository = statusRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(TmbackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		// creating statuses.
		this.statusRepository.save(new Status("Backlog"));
		this.statusRepository.save(new Status("In Progress"));
		this.statusRepository.save(new Status("Completed"));
		
		// Fetch all statuses and display.
		for (Status status : this.statusRepository.findAll()) {
			logger.info(status.toString());
		}
		
		// creating tasks.
		LocalDateTime ldt = LocalDateTime.of(2025, Month.AUGUST, 05, 9, 00);
		
		try {
			this.taskRepository.save(
				new Task("Case 100", "Burglary", getStatusByName("Backlog"), DateUtils.asDate(ldt)));
			this.taskRepository.save(
				new Task("Case 101", "Fraud", getStatusByName("In Progress"), DateUtils.asDate(ldt.plusDays(3))));
			this.taskRepository.save(
				new Task("Case 102", "Assault", getStatusByName("In Progress"), DateUtils.asDate(ldt.plusDays(5))));
			this.taskRepository.save(
				new Task("Case 103", "Murder", getStatusByName("Completed"), DateUtils.asDate(ldt.minusMonths(12))));
			this.taskRepository.save(
				new Task("Case 104", null, getStatusByName("Backlog"), DateUtils.asDate(ldt.plusYears(1))));
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		// Fetch all tasks and display.
		for (Task task : taskRepository.findAll()) {
			logger.info(task.toString());
		}
		
		// Fetch all tasks and display.
		for (Task task : taskRepository.findAll()) {
			logger.info(task.toString());
		}
		
	}
	
	private Status getStatusByName(String name) {
		return this.statusRepository.findByStatus(name).get();
	}
}
