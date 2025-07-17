package com.petermts.tmbackend.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface StatusRepository extends CrudRepository<Status, Long> {
	
	Optional<Status> findByStatus(String status);

	// Fetch all statuses.
	@Query("select s from Status s order by s.status")
	List<Status> findAll();


}
