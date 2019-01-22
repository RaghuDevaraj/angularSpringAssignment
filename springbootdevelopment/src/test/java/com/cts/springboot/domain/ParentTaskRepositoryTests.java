package com.cts.springboot.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cts.springboot.entity.ParentTask;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class ParentTaskRepositoryTests {
	
	@Autowired
	private ParentTaskRepository repository;

	@Test
	public void save_ReturnsParentTask() throws Exception {
		ParentTask parentTask = new ParentTask(null,"Create Add User Component");
		
		ParentTask savedParentTask = repository.save(parentTask);
		assertThat(savedParentTask.getParentTaskID()).isNotNull();
		assertThat(savedParentTask.getParentTaskName()).isEqualTo(parentTask.getParentTaskName());
	}

	@Test
	public void findAll_ReturnsProjects() throws Exception {
		ParentTask parentTask1 = new ParentTask(null,"Create Add Project Component");
		ParentTask parentTask2 = new ParentTask(null,"Create Add Task Component");

		repository.save(parentTask1);
		repository.save(parentTask2);

		List<ParentTask> parentTaskDetails = this.repository.findAll();
		assertThat(parentTaskDetails.get(0).getParentTaskName()).isEqualTo("Create Add Project Component");
	}
}
