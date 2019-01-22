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

import com.cts.springboot.entity.Project;
import com.cts.springboot.entity.Task;
import com.cts.springboot.entity.User;

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
		Project project = new Project(2L, "Anthem Broker Portal", df.parse("2019-02-01"), df.parse("2019-10-30"), 3);
		User user = new User(1L, "Raghu", "Devaraj", "326452");
		
		task.setProject(project);
		task.setUser(user);

		Task savedTask = this.repository.save(task);
		Task tsk = this.repository.findById(savedTask.getTaskID()).orElse(null);
		assertThat(tsk).isEqualTo((savedTask));
		assertThat(tsk.getProject().getProjectID()).isEqualTo((2L));
		assertThat(tsk.getUser().getEmployeeID()).isEqualTo(("326452"));

	}

	@Test
	public void findAll_ReturnsTasks() throws Exception {
		Task task1 = new Task(null, "Create validation for taskName field", 2, df.parse("2019-02-01"), df.parse("2019-02-03"), "O");
		Task task2 = new Task(null, "Create validation for priority field", 2, df.parse("2019-02-01"), df.parse("2019-02-03"), "O");
		
		Project project = new Project(2L, "Anthem Broker Portal", df.parse("2019-02-01"), df.parse("2019-10-30"), 3);
		User user = new User(2L, "Sugriev", "Prathap", "754477");

		task1.setProject(project);
		task1.setUser(user);
		task2.setProject(project);
		task2.setUser(user);

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
		Project project = new Project(3L, "Anthem Broker Portal", df.parse("2019-02-01"), df.parse("2019-10-30"), 3);
		User user = new User(2L, "Sugriev", "Prathap", "754477");
		task.setUser(user);
		task.setProject(project);
		Task savedTask = this.repository.save(task);

		User user1 = new User(3L, "Raghu", "Devaraj", "326452");
		savedTask.setUser(user1);
		Task updatedTask = this.repository.save(savedTask);

		assertThat(this.repository.findById(updatedTask.getTaskID()).orElse(null)).isEqualTo(updatedTask);
	}
	
	@Test
	public void suspendTasks_ReturnsNothing() throws Exception {
		
		Task task1 = new Task(null, "Create validation for taskName field", 2, df.parse("2019-02-01"), df.parse("2019-02-03"), "O");
		Task task2 = new Task(null, "Create validation for priority field", 2, df.parse("2019-02-01"), df.parse("2019-02-03"), "O");
		Project project = new Project(3L, "Anthem Broker Portal", df.parse("2019-02-01"), df.parse("2019-10-30"), 3);
		
		task1.setProject(project);
		task2.setProject(project);
		
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
