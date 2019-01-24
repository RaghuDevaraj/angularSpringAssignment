package com.cts.springboot.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.SimpleDateFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cts.springboot.entity.Task;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class TaskRepositoryTests {

	@Autowired
	private TaskRepository repository;	
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	@Test
	public void save_ReturnsTask() throws Exception {
		Task task = new Task(null, "Create validation for taskName field", 2, df.parse("2019-02-01"), df.parse("2019-02-03"), "O");
		
		task.setProject(2L);
		task.setUser(2L);

		Task savedTask = this.repository.save(task);
		Task tsk = this.repository.findById(savedTask.getTaskID()).orElse(null);
		assertThat(tsk).isEqualTo((savedTask));

	}

	@Test
	public void findAll_ReturnsTasks() throws Exception {
		Task task1 = new Task(null, "Create validation for taskName field", 2, df.parse("2019-02-01"), df.parse("2019-02-03"), "O");
		Task task2 = new Task(null, "Create validation for priority field", 2, df.parse("2019-02-01"), df.parse("2019-02-03"), "O");
		
		task1.setProject(2L);
		task1.setUser(2L);
		task2.setProject(2L);
		task2.setUser(2L);

		this.repository.save(task1);
		this.repository.save(task2);		

		/*List<Task> taskDetails = this.repository.findAll();
		assertThat(taskDetails.get(0).getTaskName()).isEqualTo("Create validation for taskName field");
		assertThat(taskDetails.get(0).getUser()).isEqualTo(user);
		assertThat(taskDetails.get(1).getProject()).isEqualTo(project);*/
	}

	@Test
	public void update_ReturnsTask() throws Exception {
		Task task = new Task(null, "Create validation for taskName field", 2, df.parse("2019-02-01"), df.parse("2019-02-03"), "O");
		task.setUser(2L);
		task.setProject(3L);
		Task savedTask = this.repository.save(task);

		savedTask.setUser(2L);
		Task updatedTask = this.repository.save(savedTask);

		assertThat(this.repository.findById(updatedTask.getTaskID()).orElse(null)).isEqualTo(updatedTask);
	}
	
	@Test
	public void suspendTasks_ReturnsNothing() throws Exception {
		
		Task task1 = new Task(null, "Create validation for taskName field", 2, df.parse("2019-02-01"), df.parse("2019-02-03"), "O");
		Task task2 = new Task(null, "Create validation for priority field", 2, df.parse("2019-02-01"), df.parse("2019-02-03"), "O");
		
		task1.setProject(3L);
		task2.setProject(3L);
		
		this.repository.save(task1);
		this.repository.save(task2);
		
		assertThat(this.repository.suspendTasksByProject(3L)).isEqualTo(2);	
	}
	
	@Test
	public void endTask_ReturnsNothing() throws Exception {		
		Task task = new Task(null, "Create validation for taskName field", 2, df.parse("2019-02-01"), df.parse("2019-02-03"), "O");		
		Task savedTask = this.repository.save(task);		
		assertThat(this.repository.endTaskByID(savedTask.getTaskID())).isEqualTo(1);
	}
	
}
