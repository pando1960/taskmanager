package com.petermts.tmbackend.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petermts.tmbackend.domain.Status;
import com.petermts.tmbackend.service.StatusService;

@RestController
@RequestMapping("/api/statuses")
public class StatusController {
	
	private StatusService statusService;
	
	public StatusController(StatusService statusService) {
		super();
		this.statusService = statusService;
	}

	@GetMapping("/all")
	public List<Status> getAllStatuses() {
		//Fetch and return all statuses.
		return this.statusService.getAllStatuses();
	}
	

}
