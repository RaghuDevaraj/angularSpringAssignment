package com.cts.springboot.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cts.springboot.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{	

	
    @Modifying(flushAutomatically=true)
	@Query("update Task t set t.status='S' WHERE t.project.projectID = :projectID")
	int suspendTasksByProject(@Param("projectID") Long projectID);
	
    @Modifying
	@Query("update Task t set t.status='C' WHERE t.taskID = :taskID")
	int endTaskByID(@Param("taskID") Long taskID);
    
    @Query("SELECT t from Task t WHERE t.project.projectID = :projectID")
    List<Task> findByProject(@Param("projectID") Long projectID);
}
