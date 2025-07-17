package com.petermts.tmbackend.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {
	
	// Fetch all tasks.
	@Query("select t from Task t join fetch t.status")
	List<Task> findAll();
	
//	// Fetch all tasks with statuses to avoid lazy initialisation exception with lazy fetching.
//	@Query("select t from Task t join fetch t.status")
//	List<Task> findAllWithStatuses();
	
	// Fetch tasks by title.
	List<Task> findByTitle(String title);
	
	// Fetch tasks by description.
	List<Task> findByDescription(String description);
	
	// Fetch tasks by title and description.
	List<Task> findByTitleAndDescription(String title, String description);
	
	// Fetch tasks by description order by due date.
	List<Task> findByDescriptionOrderByDueAsc(String description);
	
	// Fetch tasks where a title contains some text using SQL.
	@Query("select t from Task t where t.title like %?1%")
	List<Task> findByTitleContainingText(String text);
	
	// Fetch tasks by status.
	@Query("select t from Task t where t.status.id = ?1")
	List<Task> findByStatusId(Long statusId);

}
