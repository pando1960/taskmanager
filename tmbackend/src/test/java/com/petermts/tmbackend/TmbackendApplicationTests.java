package com.petermts.tmbackend;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.petermts.tmbackend.web.TaskController;

@SpringBootTest
class TmbackendApplicationTests {
	
	@Autowired
	private TaskController taskController;

	@Test
	@DisplayName("First example test case")
	void contextLoads() {
		assertThat(taskController).isNotNull();
	}

	
	
	
}
