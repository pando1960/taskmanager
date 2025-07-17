package com.petermts.tmbackend.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.petermts.tmbackend.utils.DateUtils;

@DataJpaTest
public class TaskRepositoryIntegrationTest {
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private StatusRepository statusRepository;
	
	@BeforeEach
	void setUp() {
		
		// ensure no tasks already setup.
		List<Task> tasks = taskRepository.findAll();
		
		if (! tasks.isEmpty() ) {
			taskRepository.deleteAll();
		}
	}
	
	@Test
	@DisplayName("Fetch All tasks")
	void getAllTask() {
		
		LocalDateTime ldt1 = LocalDateTime.of(2025, Month.SEPTEMBER, 05, 14, 00);
		Task task1 = new Task("Case 100", "Burglary", getStatusByName("Backlog"), DateUtils.asDate(ldt1));
		
		LocalDateTime ldt2 = LocalDateTime.of(2025, Month.OCTOBER, 05, 14, 00);
		Task task2 = new Task("Case 200", "Theft", getStatusByName("In Progress"), DateUtils.asDate(ldt2));

		LocalDateTime ldt3 = LocalDateTime.of(2025, Month.NOVEMBER, 05, 14, 00);
		Task task3 = new Task("Case 300", "Assault", getStatusByName("Completed"), DateUtils.asDate(ldt3));
		
		List<Task> tasks = new ArrayList<>();
		tasks.add(task1);
		tasks.add(task2);
		tasks.add(task3);
		
		taskRepository.saveAll(tasks);
		taskRepository.deleteAll();
		
		assertThat(taskRepository.findAll()).isEmpty();
	}

	@Test
	@DisplayName("Remove All tasks")
	void removeAllTasks() {
		
		LocalDateTime ldt1 = LocalDateTime.of(2025, Month.SEPTEMBER, 05, 14, 00);
		Task task1 = new Task("Case 100", "Burglary", getStatusByName("Backlog"), DateUtils.asDate(ldt1));
		
		LocalDateTime ldt2 = LocalDateTime.of(2025, Month.OCTOBER, 05, 14, 00);
		Task task2 = new Task("Case 200", "Theft", getStatusByName("In Progress"), DateUtils.asDate(ldt2));

		LocalDateTime ldt3 = LocalDateTime.of(2025, Month.NOVEMBER, 05, 14, 00);
		Task task3 = new Task("Case 300", "Assault", getStatusByName("Completed"), DateUtils.asDate(ldt3));
		
		List<Task> tasks = new ArrayList<>();
		tasks.add(task1);
		tasks.add(task2);
		tasks.add(task3);
		
		taskRepository.saveAll(tasks);
		
		assertThat(taskRepository.findAll()).isNotEmpty();
		assertThat(taskRepository.findAll().size()).isEqualTo(3);
	}

	@Test
	@DisplayName("Add new task")
	void createTask() {
		
		LocalDateTime ldt = LocalDateTime.of(2025, Month.SEPTEMBER, 05, 14, 00);
		
		Task task = new Task("Case 900", "Burglary", getStatusByName("Backlog"), DateUtils.asDate(ldt));
		
		taskRepository.save(task);
		
		assertThat(taskRepository.findByTitle("Case 900")).isNotEmpty();
		assertThat(taskRepository.findByTitle("Case 900").get(0).getDescription()).isEqualTo("Burglary");
	}
	
	@Test
	@DisplayName("Update existing task")
	void updateTask() {
		
		LocalDateTime ldt = LocalDateTime.of(2025, Month.SEPTEMBER, 05, 14, 00);
		
		Task task = new Task("Case 900", "Burglary", getStatusByName("Backlog"), DateUtils.asDate(ldt));
		
		taskRepository.save(task);
		
		Task updatedTask = taskRepository.findByTitle("Case 900").get(0);
		updatedTask.setDescription("Grand Theft Auto");
		
		taskRepository.save(updatedTask);
		
		assertThat(taskRepository.findByTitle("Case 900")).isNotEmpty();
		assertThat(taskRepository.findByTitle("Case 900").get(0).getDescription()).isEqualTo("Grand Theft Auto");
	}
	
	@Test
	@DisplayName("Remove existing task")
	void removeeTask() {
		
		LocalDateTime ldt = LocalDateTime.of(2025, Month.SEPTEMBER, 05, 14, 00);
		
		Task task = new Task("Case 900", "Burglary", getStatusByName("Backlog"), DateUtils.asDate(ldt));
		
		taskRepository.save(task);
		
		Task removeTask = taskRepository.findByTitle("Case 900").get(0);
		
		taskRepository.delete(removeTask);
		
		assertThat(taskRepository.findByTitle("Case 900")).isEmpty();
	}

	// helper methods.
	private Status getStatusByName(String name) {
		return this.statusRepository.findByStatus(name).get();
	}

}
