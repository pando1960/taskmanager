package com.petermts.tmbackend.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TaskControllerIntegrationTest {
	
	@Autowired
	private MockMvcTester  mockMvcTester ;

	@Test 
	@DisplayName("fetch task by id")
	void getTaskByIdSuccessful() throws Exception {
	
		MvcTestResult testResult = mockMvcTester.get().uri("/api/tasks/1").exchange();
		
		var expectedJsonResult =  new ClassPathResource("/task-id-success-response.json", TaskControllerIntegrationTest.class);
	
		assertThat(testResult)
			.hasStatusOk()
			.bodyJson()
			.isLenientlyEqualTo(expectedJsonResult);
	}
	 
	@Test 
	@DisplayName("fetch all tasks")
	void getAllTasksSuccessful() throws Exception {
	
		MvcTestResult testResult = mockMvcTester.get().uri("/api/tasks/all").exchange();
				
		var expectedJsonResult =  new ClassPathResource("/tasks-all-success-response.json", TaskControllerIntegrationTest.class);
	
		assertThat(testResult)
			.hasStatusOk()
			.bodyJson()
			.isLenientlyEqualTo(expectedJsonResult);
	}
	
	@Test 
	@DisplayName("create task")
	void createTaskSuccessful() throws Exception {
	
		var requestBody =  new ClassPathResource("/task-create-success-request.json", TaskControllerIntegrationTest.class);

		MvcTestResult testResult = mockMvcTester
			.post()
			.uri("/api/tasks/create")
			.contentType(MediaType.APPLICATION_JSON)
			.content(requestBody.getContentAsString(StandardCharsets.UTF_8))
			.exchange();				
							
		var expectedJsonResult =  new ClassPathResource("/task-create-success-response.json", TaskControllerIntegrationTest.class);
	
		assertThat(testResult)
			.hasStatusOk()
			.bodyJson()
			.isLenientlyEqualTo(expectedJsonResult);
	}
	
	@Test 
	@DisplayName("update task")
	void updateTaskSuccessful() throws Exception {
	
		var requestBody =  new ClassPathResource("/task-update-success-request.json", TaskControllerIntegrationTest.class);

		MvcTestResult testResult = mockMvcTester
			.put()
			.uri("/api/tasks/update")
			.contentType(MediaType.APPLICATION_JSON)
			.content(requestBody.getContentAsString(StandardCharsets.UTF_8))
			.exchange();				
							
		var expectedJsonResult =  new ClassPathResource("/task-update-success-response.json", TaskControllerIntegrationTest.class);
	
		assertThat(testResult)
			.hasStatusOk()
			.bodyJson()
			.isLenientlyEqualTo(expectedJsonResult);
	}
	
	@Test 
	@DisplayName("remove task")
	void removeTaskSuccessful() throws Exception {
	
		MvcTestResult testResult = mockMvcTester
			.delete()
			.uri("/api/tasks/remove/{id}", 1)
			.exchange();				
							
		var expectedJsonResult =  new ClassPathResource("/task-remove-success-response.json", TaskControllerIntegrationTest.class);
	
		assertThat(testResult)
			.hasStatusOk()
			.bodyJson()
			.isLenientlyEqualTo(expectedJsonResult);
	}

}
