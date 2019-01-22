package com.cts.springboot.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.cts.springboot.domain.ParentTaskRepository;
import com.cts.springboot.domain.ProjectRepository;
import com.cts.springboot.domain.TaskRepository;
import com.cts.springboot.domain.UserRepository;
import com.cts.springboot.entity.ParentTask;
import com.cts.springboot.entity.Project;
import com.cts.springboot.entity.Task;
import com.cts.springboot.entity.User;

@RunWith(MockitoJUnitRunner.class)
public class ProjectManagerServiceTests {

	@Mock
	private UserRepository userRepository;

	@Mock
	private ProjectRepository projectRepository;

	@Mock
	private ParentTaskRepository parentTaskRepository;

	@Mock
	private TaskRepository taskRepository;

	private ProjectManagerServiceImpl projectManagerService;
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");


	@Before
	public void setUp() throws Exception {
		projectManagerService = new ProjectManagerServiceImpl(userRepository, projectRepository, parentTaskRepository,
				taskRepository);
	}

	@Test
	public void getUsers_returnsUsersInfo() {
		List<User> users = new ArrayList<User>();
		users.add(new User(1L, "Raghu", "Devaraj", "326452"));
		users.add(new User(2L, "Sugriev", "Prathap", "852342"));
		given(userRepository.findAll()).willReturn(users);

		List<User> userDetails = projectManagerService.getUsers();

		assertThat(userDetails.get(0).getFirstName()).isEqualTo("Raghu");
		assertThat(userDetails.get(0).getEmployeeID()).isEqualTo("326452");
	}

	@Test
	public void addUser_returnsMessage() {
		User user = new User(1L, "Raghu", "Devaraj", "326452");
		given(userRepository.saveAndFlush(any(User.class))).willReturn(user);

		String message = projectManagerService.addUser(new User());

		assertThat(message).isEqualTo("User - Raghu Devaraj is added successfully.");
	}

	@Test
	public void updateUser_returnsMessage() {
		User user1 = new User(1L, "Raghu", "Devaraj", "326452");
		User user2 = new User(1L, "Raghu", "Devaraj", "455675");
		given(userRepository.findById(anyLong())).willReturn(Optional.of(user1));
		given(userRepository.saveAndFlush(any(User.class))).willReturn(user2);

		String message = projectManagerService.updateUser(new User(1L, "Raghu", "Devaraj", "326452"));

		assertThat(message).isEqualTo("User - Raghu Devaraj is updated successfully.");
	}

	@Test
	public void deleteUser_returnsMessage() {
		User user = new User(1L, "Raghu", "Devaraj", "326452");
		given(userRepository.findById(anyLong())).willReturn(Optional.of(user));

		String message = projectManagerService.deleteUser(1);

		assertThat(message).isEqualTo("User - Raghu Devaraj is deleted successfully.");
	}

	@Test
	public void getProjects_returnsProjectsInfo() throws Exception {
		List<Project> projects = new ArrayList<Project>();
		projects.add(new Project(1L, "Anthem Broker Portal", df.parse("2019-02-01"), df.parse("2019-10-30"), 3));
		projects.add(new Project(2L, "Anthem Smart Shopper", df.parse("2019-02-01"), df.parse("2019-10-30"), 1));
		given(projectRepository.findAll()).willReturn(projects);

		List<Project> projectDetails = projectManagerService.getProjects();

		assertThat(projectDetails.get(0).getProjectName()).isEqualTo("Anthem Broker Portal");
		assertThat(projectDetails.get(1).getPriority()).isEqualTo(1);
	}

	@Test
	public void addProject_returnsMessage() throws Exception {
		Project project = new Project(1L, "Anthem Broker Portal", df.parse("2019-02-01"), df.parse("2019-10-30"), 3);
		given(projectRepository.saveAndFlush(any(Project.class))).willReturn(project);

		String message = projectManagerService.addProject(project);

		assertThat(message).isEqualTo("Project - Anthem Broker Portal is added successfully.");
	}

	@Test
	public void updateProject_returnsMessage() throws Exception{
		Project project1 = new Project(1L, "Anthem Broker Portal", df.parse("2019-02-01"), df.parse("2019-10-30"), 3);
		Project project2 = new Project(1L, "Anthem Brokers Portal", df.parse("2019-02-01"), df.parse("2019-10-30"), 3);
		given(projectRepository.findById(anyLong())).willReturn(Optional.of(project1));
		given(projectRepository.saveAndFlush(any(Project.class))).willReturn(project2);

		String message = projectManagerService.updateProject(new Project(1L, "Anthem Broker Portal", df.parse("2019-02-01"), df.parse("2019-10-30"), 3));

		assertThat(message).isEqualTo("Project - Anthem Brokers Portal is updated successfully.");
	}
	
	@Test
	public void suspendProject_returnsMessage() throws Exception{
		Project project = new Project(1L, "Anthem Broker Portal", df.parse("2019-02-01"), df.parse("2019-10-30"), 3);
		given(projectRepository.findById(anyLong())).willReturn(Optional.of(project));

		String message = projectManagerService.suspendProject(1);

		assertThat(message).isEqualTo("Project - Anthem Broker Portal is suspended successfully.");
	}
	
	
	@Test
	public void getParentTasks_returnsParentTaskInfo() throws Exception {
		List<ParentTask> parentTasks = new ArrayList<ParentTask>();
		parentTasks.add(new ParentTask(1L, "Create Add User Component"));
		parentTasks.add(new ParentTask(2L, "Create Add Task Component"));
		given(parentTaskRepository.findAll()).willReturn(parentTasks);

		List<ParentTask> parentTaskDetails = projectManagerService.getParentTasks();

		assertThat(parentTaskDetails.get(0).getParentTaskName()).isEqualTo("Create Add User Component");
	}

	@Test
	public void addParentTask_returnsMessage() throws Exception {
		ParentTask parentTask = new ParentTask(1L, "Create Add User Component");
		given(parentTaskRepository.saveAndFlush(any(ParentTask.class))).willReturn(parentTask);

		String message = projectManagerService.addParentTask(parentTask);

		assertThat(message).isEqualTo("ParentTask - Create Add User Component is added successfully.");
	}
	 
	
	
	@Test
	public void getTasks_returnsTasksInfo() throws Exception {
		List<Task> tasks = new ArrayList<Task>();
		tasks.add(new Task(1L, "Create validation for taskName field", 3, df.parse("2019-02-01"), df.parse("2019-02-03"), "O"));
		tasks.add(new Task(2L, "Create validation for priority field", 2, df.parse("2019-02-01"), df.parse("2019-02-03"), "O"));
		given(taskRepository.findAll()).willReturn(tasks);

		List<Task> taskDetails = projectManagerService.getTasks();

		assertThat(taskDetails.get(0).getTaskName()).isEqualTo("Create validation for taskName field");
		assertThat(taskDetails.get(1).getPriority()).isEqualTo(2);
	}

	@Test
	public void addTask_returnsMessage() throws Exception {
		Task task = new Task(1L, "Create validation for taskName field", 3, df.parse("2019-02-01"), df.parse("2019-02-03"), "O");
		given(taskRepository.saveAndFlush(any(Task.class))).willReturn(task);

		String message = projectManagerService.addTask(task);

		assertThat(message).isEqualTo("Task - Create validation for taskName field is added successfully.");
	}

	@Test
	public void updateTask_returnsMessage() throws Exception{
		Task task1 = new Task(1L, "Create validation for taskName field", 3, df.parse("2019-02-01"), df.parse("2019-02-03"), "O");
		Task task2 = new Task(2L, "Create validation for priority field", 2, df.parse("2019-02-01"), df.parse("2019-02-03"), "O");
		given(taskRepository.findById(anyLong())).willReturn(Optional.of(task1));
		given(taskRepository.saveAndFlush(any(Task.class))).willReturn(task2);

		String message = projectManagerService.updateTask(task1);

		assertThat(message).isEqualTo("Task - Create validation for priority field is updated successfully.");
	}
	
	@Test
	public void endTask_returnsMessage() throws Exception{
		Task task = new Task(1L, "Create validation for taskName field", 3, df.parse("2019-02-01"), df.parse("2019-02-03"), "O");
		given(taskRepository.findById(anyLong())).willReturn(Optional.of(task));

		String message = projectManagerService.endTask(1);

		assertThat(message).isEqualTo("Task - Create validation for taskName field is ended successfully.");
	}
}
