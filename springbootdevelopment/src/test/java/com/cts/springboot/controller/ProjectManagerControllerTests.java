package com.cts.springboot.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.cts.springboot.entity.ParentTask;
import com.cts.springboot.entity.Project;
import com.cts.springboot.entity.Task;
import com.cts.springboot.entity.User;
import com.cts.springboot.service.ProjectManagerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(ProjectManagerController.class)
@ActiveProfiles("test")
public class ProjectManagerControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProjectManagerService pmService;

	@Test
	public void getUsers_ReturnsUsers() throws Exception {
		List<User> users = new ArrayList<User>();
		users.add(new User(1L, "Raghu", "Devaraj", "326452"));
		users.add(new User(2L, "Sugriev", "Prathap", "823426"));
		when(this.pmService.getUsers()).thenReturn(users);
		this.mockMvc.perform(get("/projectManager/users")).andExpect(status().isOk()).andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.data[:1].firstName").value("Raghu"));
	}

	@Test
	public void addUser_ReturnsMessage() throws Exception {
		when(this.pmService.addUser(any(User.class))).thenReturn("User added sucessfully.");
		this.mockMvc
				.perform(post("/projectManager/addUser").content(asJsonString(new User())).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.message").value("User added sucessfully."));

	}

	@Test
	public void updateUser_ReturnsMessage() throws Exception {
		when(this.pmService.updateUser(any(User.class))).thenReturn("User updated sucessfully.");
		this.mockMvc
				.perform(put("/projectManager/updateUser").content(asJsonString(new User())).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.message").value("User updated sucessfully."));
	}

	@Test
	public void updateUser_InvalidUser_Returns404() throws Exception {
		when(this.pmService.updateUser(any(User.class))).thenReturn("invalid");
		this.mockMvc
				.perform(put("/projectManager/updateUser").content(asJsonString(new User())).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(jsonPath("$.error").value("Invalid User ID."));
	}

	@Test
	public void deleteUser_ReturnsMessage() throws Exception {
		when(this.pmService.deleteUser(anyInt())).thenReturn("User deleted sucessfully.");
		this.mockMvc.perform(delete("/projectManager/deleteUser/{userID}", 1)).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("User deleted sucessfully."));
	}

	@Test
	public void deleteUser_InvalidUserID_Returns404() throws Exception {
		when(this.pmService.deleteUser(anyInt())).thenReturn("invalid");
		this.mockMvc.perform(delete("/projectManager/deleteUser/{userID}", 1)).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.error").value("Invalid User ID."));
	}

	@Test
	public void getProjects_ReturnsProjects() throws Exception {
		List<Project> projects = new ArrayList<Project>();
		projects.add(new Project(1L, "Anthem", null, null, 3));
		when(this.pmService.getProjects()).thenReturn(projects);
		this.mockMvc.perform(get("/projectManager/projects")).andExpect(status().isOk()).andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.data[:1].projectName").value("Anthem"));
	}

	@Test
	public void addProject_ReturnsMessage() throws Exception {
		when(this.pmService.addProject(any(Project.class))).thenReturn("Project added sucessfully.");
		this.mockMvc
				.perform(post("/projectManager/addProject").content(asJsonString(new User())).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.message").value("Project added sucessfully."));

	}
	
	@Test
	public void updateProject_ReturnsMessage() throws Exception {
		when(this.pmService.updateProject(any(Project.class))).thenReturn("Project updated sucessfully.");
		this.mockMvc
				.perform(put("/projectManager/updateProject").content(asJsonString(new Project())).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.message").value("Project updated sucessfully."));
	}

	@Test
	public void updateProject_InvalidProject_Returns404() throws Exception {
		when(this.pmService.updateProject(any(Project.class))).thenReturn("invalid");
		this.mockMvc
				.perform(put("/projectManager/updateProject").content(asJsonString(new Project())).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(jsonPath("$.error").value("Invalid Project ID."));
	}
	
	@Test
	public void suspendProject_ReturnsMessage() throws Exception {
		when(this.pmService.suspendProject(anyInt())).thenReturn("Project suspended sucessfully.");
		this.mockMvc.perform(put("/projectManager/suspendProject", 1).content(asJsonString(1)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Project suspended sucessfully."));
	}

	@Test
	public void suspendProject_InvalidProjectID_Returns404() throws Exception {
		when(this.pmService.suspendProject(anyInt())).thenReturn("invalid");
		this.mockMvc.perform(put("/projectManager/suspendProject", 1).content(asJsonString(1)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.error").value("Invalid Project ID."));
	}
	
	@Test
	public void getParentTasks_ReturnsParentTasks() throws Exception {
		List<ParentTask> parentTasks = new ArrayList<ParentTask>();
		parentTasks.add(new ParentTask(1L, "Design Add Task Page"));
		when(this.pmService.getParentTasks()).thenReturn(parentTasks);
		this.mockMvc.perform(get("/projectManager/parentTasks")).andExpect(status().isOk()).andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.data[:1].parentTaskName").value("Design Add Task Page"));
	}
	
	@Test
	public void addParentTask_ReturnsMessage() throws Exception {
		when(this.pmService.addParentTask(any(ParentTask.class))).thenReturn("Parent task added sucessfully.");
		this.mockMvc
				.perform(post("/projectManager/addParentTask").content(asJsonString(new ParentTask())).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.message").value("Parent task added sucessfully."));

	}
	
	@Test
	public void getTasks_ReturnsTasks() throws Exception {
		List<Task> tasks = new ArrayList<Task>();
		tasks.add(new Task(1L, "Reset button should clear all the form values.", 5, new Date(), null, "A"));
		when(this.pmService.getTasks(anyLong())).thenReturn(tasks);
		this.mockMvc.perform(get("/projectManager/tasks/2")).andExpect(status().isOk()).andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.data[:1].taskName").value("Reset button should clear all the form values."));
	}
	
	@Test
	public void addTask_ReturnsMessage() throws Exception {
		when(this.pmService.addTask(any(Task.class))).thenReturn("Task added sucessfully.");
		this.mockMvc
				.perform(post("/projectManager/addTask").content(asJsonString(new Task())).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.message").value("Task added sucessfully."));

	}
	
	@Test
	public void updateTask_ReturnsMessage() throws Exception {
		when(this.pmService.updateTask(any(Task.class))).thenReturn("Task updated sucessfully.");
		this.mockMvc
				.perform(put("/projectManager/updateTask").content(asJsonString(new Task())).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.message").value("Task updated sucessfully."));
	}

	@Test
	public void updateTask_InvalidTask_Returns404() throws Exception {
		when(this.pmService.updateTask(any(Task.class))).thenReturn("invalid");
		this.mockMvc
				.perform(put("/projectManager/updateTask").content(asJsonString(new Task())).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(jsonPath("$.error").value("Invalid Task ID."));
	}
	
	@Test
	public void endTask_ReturnsMessage() throws Exception {
		when(this.pmService.endTask(anyInt())).thenReturn("Task ended sucessfully.");
		this.mockMvc
				.perform(put("/projectManager/endTask").content(asJsonString(1)).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.message").value("Task ended sucessfully."));
	}

	@Test
	public void endTask_InvalidTask_Returns404() throws Exception {
		when(this.pmService.endTask(anyInt())).thenReturn("invalid");
		this.mockMvc
				.perform(put("/projectManager/endTask").content(asJsonString(1)).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(jsonPath("$.error").value("Invalid Task ID."));
	}
	
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
