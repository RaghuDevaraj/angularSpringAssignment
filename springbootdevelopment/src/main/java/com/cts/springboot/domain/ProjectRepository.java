package com.cts.springboot.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cts.springboot.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{

	/*@Query("SELECT Task t set t.status='S' WHERE t.project.projectID = :projectID")
	List<Project> getProjects();*/
}
