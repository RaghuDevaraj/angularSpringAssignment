package com.cts.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.springboot.entity.ParentTask;
import com.cts.springboot.entity.Project;
import com.cts.springboot.entity.Response;
import com.cts.springboot.entity.Task;
import com.cts.springboot.entity.User;
import com.cts.springboot.service.ProjectManagerService;

@RestController
public class ProjectManagerController {
	
	@Autowired
	private ProjectManagerService pmService;
	
	@GetMapping("/users")
	public ResponseEntity<Response> getUsers() {
		return ResponseEntity.ok().body(new Response(null, pmService.getUsers(), null));
	}
	
	@PostMapping("/addUser")
	public ResponseEntity<Response> addUser(@RequestBody User user) {
		return ResponseEntity.ok().body(new Response(null, null, pmService.addUser(user)));
	}
	
	@PutMapping("/updateUser")
	public ResponseEntity<Response> updateUser(@RequestBody User user) {
		String output = pmService.updateUser(user);
		if("invalid".equals(output)){
			return ((BodyBuilder) ResponseEntity.notFound()).body(new Response("Invalid User ID.", null, null));
		}
		return ResponseEntity.ok().body(new Response(null, null, output));		
	}
	
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<Response> deleteBookDetails(@PathVariable int id) {
		String output = pmService.deleteUser(id);
		if ("invalid".equals(output)) {
			return ((BodyBuilder) ResponseEntity.notFound()).body(new Response("Invalid User ID.", null, null));
		}
		return ResponseEntity.ok().body(new Response(null, null, output));
	}
	
	@GetMapping("/projects")
	public ResponseEntity<Response> getProjects() {
		return ResponseEntity.ok().body(new Response(null, pmService.getProjects(), null));
	}
	
	@PostMapping("/addProject")
	public ResponseEntity<Response> addProject(@RequestBody Project project) {
		return ResponseEntity.ok().body(new Response(null, null, pmService.addProject(project)));
	}

	@PutMapping("/updateProject")
	public ResponseEntity<Response> updateProject(@RequestBody Project project) {
		String output = pmService.updateProject(project);
		if("invalid".equals(output)){
			return ((BodyBuilder) ResponseEntity.notFound()).body(new Response("Invalid Project ID.", null, null));
		}
		return ResponseEntity.ok().body(new Response(null, null, output));		
	}
	
	@PutMapping("/suspendProject")
	public ResponseEntity<Response> suspendProject(@RequestBody Integer projectID) {
		String output = pmService.suspendProject(projectID);
		if("invalid".equals(output)){
			return ((BodyBuilder) ResponseEntity.notFound()).body(new Response("Invalid Project ID.", null, null));
		}
		return ResponseEntity.ok().body(new Response(null, null, output));		
	}
	
	@GetMapping("/parentTasks")
	public ResponseEntity<Response> getParentTasks() {
		return ResponseEntity.ok().body(new Response(null, pmService.getParentTasks(), null));
	}
	
	@PostMapping("/addParentTask")
	public ResponseEntity<Response> addParentTask(@RequestBody ParentTask parentTask) {
		return ResponseEntity.ok().body(new Response(null, null, pmService.addParentTask(parentTask)));
	}
	
	@GetMapping("/tasks")
	public ResponseEntity<Response> getTasks() {
		return ResponseEntity.ok().body(new Response(null, pmService.getTasks(), null));
	}
	
	@PostMapping("/addTask")
	public ResponseEntity<Response> addTask(@RequestBody Task task) {
		return ResponseEntity.ok().body(new Response(null, null, pmService.addTask(task)));
	}
	
	@PutMapping("/updateTask")
	public ResponseEntity<Response> updateTask(@RequestBody Task task) {
		String output = pmService.updateTask(task);
		if("invalid".equals(output)){
			return ((BodyBuilder) ResponseEntity.notFound()).body(new Response("Invalid Task ID.", null, null));
		}
		return ResponseEntity.ok().body(new Response(null, null, output));		
	}
	
	@PutMapping("/endTask")
	public ResponseEntity<Response> endTask(@RequestBody int taskID) {
		String output = pmService.endTask(taskID);
		if("invalid".equals(output)){
			return ((BodyBuilder) ResponseEntity.notFound()).body(new Response("Invalid Task ID.", null, null));
		}
		return ResponseEntity.ok().body(new Response(null, null, output));		
	}
}
