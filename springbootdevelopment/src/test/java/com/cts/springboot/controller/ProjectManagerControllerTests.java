package com.cts.springboot.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.cts.springboot.entity.User;
import com.cts.springboot.service.ProjectManagerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(ProjectManagerController.class)
public class ProjectManagerControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProjectManagerService pmService;
	
	@Test
	public void getUsers_ReturnsUsers() throws Exception {
		List<User> users = new ArrayList<User>();
		users.add(new User(1L,"Raghu","Devaraj","326452"));
		users.add(new User(2L,"Sugriev","Prathap","823426"));
		when(this.pmService.getUsers()).thenReturn(users);	
		this.mockMvc.perform(get("/users"))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.data").isArray())
						.andExpect(jsonPath("$.data[:1].firstName").value("Raghu"));
	}
	
	@Test
	public void addUser_ReturnsMessage() throws Exception {
		when(this.pmService.addUser(any(User.class))).thenReturn("User added sucessfully.");
		this.mockMvc.perform(post("/addUser")
							.content(asJsonString(new User()))
							.contentType(MediaType.APPLICATION_JSON)
						    .accept(MediaType.APPLICATION_JSON))						
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.message").value("User added sucessfully."));
		
	}
	
	@Test
	public void updateUser_ReturnsMessage() throws Exception {
		when(this.pmService.updateUser(any(User.class))).thenReturn("User updated sucessfully.");
		this.mockMvc.perform(put("/updateUser")
							.content(asJsonString(new User()))
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.message").value("User updated sucessfully."));
	}
	
	@Test
	public void updateUser_InvalidUser_Returns404() throws Exception {
		when(this.pmService.updateUser(any(User.class))).thenReturn("invalid");
		this.mockMvc.perform(put("/updateUser")
							.content(asJsonString(new User()))
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isNotFound())
						.andExpect(jsonPath("$.error").value("Invalid User ID."));
	}
	
	@Test
	public void deleteUser_ReturnsMessage() throws Exception {
		when(this.pmService.deleteUser(anyInt())).thenReturn("User deleted sucessfully.");
		this.mockMvc.perform(delete("/deleteUser/{userID}", 1))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.message").value("User deleted sucessfully."));
	}
	
	@Test
	public void deleteUser_InvalidUserID_Returns404() throws Exception {
		when(this.pmService.deleteUser(anyInt())).thenReturn("invalid");
		this.mockMvc.perform(delete("/deleteUser/{userID}", 1))
						.andExpect(status().isNotFound())
						.andExpect(jsonPath("$.error").value("Invalid User ID."));
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
