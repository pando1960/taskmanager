package com.petermts.tmbackend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.petermts.tmbackend.domain.Status;
import com.petermts.tmbackend.domain.StatusRepository;

@Service
public class StatusService {
	
	private StatusRepository statusRepository;

	public StatusService(StatusRepository statusRepository) {
		super();
		this.statusRepository = statusRepository;
	}
	
	public Status getStatusById(Long statusId) {
		return statusRepository.findById(statusId).get();
	}
	
	public List<Status> getAllStatuses() {
		return statusRepository.findAll();
	}



}
