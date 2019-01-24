package com.cts.springboot.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cts.springboot.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{

	@Query("SELECT p.projectID, p.projectName, p.startDate, p.endDate, p.priority, p.user.userID, COUNT(t.taskID), "
			+ "(SELECT COUNT(tas) FROM Task tas, Project pro WHERE tas.project.projectID = pro.projectID AND tas.status = 'C' AND pro.projectID = p.projectID) "
			+ "FROM Project p "
			+ "LEFT JOIN Task t on t.project.projectID = p.projectID GROUP BY p.projectID")
	List<Project> getProjectDetails();
}
