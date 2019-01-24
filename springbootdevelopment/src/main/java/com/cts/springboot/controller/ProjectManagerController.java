package com.cts.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.springboot.entity.ParentTask;
import com.cts.springboot.entity.Project;
import com.cts.springboot.entity.Response;
import com.cts.springboot.entity.Task;
import com.cts.springboot.entity.User;
import com.cts.springboot.service.ProjectManagerService;

/**
 * @author 326452 
 * ProjectManagerController to handle the requests for user,
 * 			project and task operations.
 */
@CrossOrigin
@RestController
@RequestMapping("/projectManager")
public class ProjectManagerController {

	/**
	 * Dependency injection of ProjectManagerService
	 */
	@Autowired
	private ProjectManagerService pmService;

	/**
	 * GET Method to retrieve users
	 * 
	 * @return list of users
	 */
	@GetMapping("/users")
	public ResponseEntity<Response> getUsers() {
		return ResponseEntity.ok().body(new Response(null, pmService.getUsers(), null));
	}

	/**
	 * POST Method to save the user
	 * @param user
	 * @return Success Message
	 */
	@PostMapping("/addUser")
	public ResponseEntity<Response> addUser(@RequestBody User user) {
		return ResponseEntity.ok().body(new Response(null, null, pmService.addUser(user)));
	}

	/**
	 * PUT Method to update the user
	 * @param user
	 * @return Success / Error Message
	 */
	@PutMapping("/updateUser")
	public ResponseEntity<Response> updateUser(@RequestBody User user) {
		String output = pmService.updateUser(user);
		if ("invalid".equals(output)) {
			return ((BodyBuilder) ResponseEntity.notFound()).body(new Response("Invalid User ID.", null, null));
		}
		return ResponseEntity.ok().body(new Response(null, null, output));
	}

	/**
	 * DELETE Method to delete the user
	 * @param id
	 * @return Success / Error Message
	 */
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<Response> deleteBookDetails(@PathVariable int id) {
		String output = pmService.deleteUser(id);
		if ("invalid".equals(output)) {
			return ((BodyBuilder) ResponseEntity.notFound()).body(new Response("Invalid User ID.", null, null));
		}
		return ResponseEntity.ok().body(new Response(null, null, output));
	}

	/**
	 * GET Method to retrieve the projects
	 * @return list of projects
	 */
	@GetMapping("/projects")
	public ResponseEntity<Response> getProjects() {
		return ResponseEntity.ok().body(new Response(null, pmService.getProjects(), null));
	}
	
	/**
	 * GET Method to retrieve the projects with task details
	 * @return list of projects
	 */
	@GetMapping("/projectsWithDetails")
	public ResponseEntity<Response> getProjectsWithDetails() {
		return ResponseEntity.ok().body(new Response(null, pmService.getProjectWithDetails(), null));
	}

	/**
	 * POST Method to save the project
	 * @param project
	 * @return Success Message
	 */
	@PostMapping("/addProject")
	public ResponseEntity<Response> addProject(@RequestBody Project project) {		
		return ResponseEntity.ok().body(new Response(null, null, pmService.addProject(project)));
	}

	/**
	 * PUT Method to update the project
	 * @param project
	 * @return Success / Error Message
	 */
	@PutMapping("/updateProject")
	public ResponseEntity<Response> updateProject(@RequestBody Project project) {
		String output = pmService.updateProject(project);
		if ("invalid".equals(output)) {
			return ((BodyBuilder) ResponseEntity.notFound()).body(new Response("Invalid Project ID.", null, null));
		}
		return ResponseEntity.ok().body(new Response(null, null, output));
	}

	/**
	 * PUT Method to update the project tasks to suspended status
	 * @param projectID
	 * @return Success / Error Message
	 */
	@PutMapping("/suspendProject")
	public ResponseEntity<Response> suspendProject(@RequestBody Integer projectID) {
		String output = pmService.suspendProject(projectID);
		if ("invalid".equals(output)) {
			return ((BodyBuilder) ResponseEntity.notFound()).body(new Response("Invalid Project ID.", null, null));
		}
		return ResponseEntity.ok().body(new Response(null, null, output));
	}

	/**
	 * GET Method to retrieve the parent tasks
	 * @return list of parent tasks
	 */
	@GetMapping("/parentTasks")
	public ResponseEntity<Response> getParentTasks() {
		return ResponseEntity.ok().body(new Response(null, pmService.getParentTasks(), null));
	}

	/**
	 * POST Method to save the parent task
	 * @param parentTask
	 * @return Success Message
	 */
	@PostMapping("/addParentTask")
	public ResponseEntity<Response> addParentTask(@RequestBody ParentTask parentTask) {
		return ResponseEntity.ok().body(new Response(null, null, pmService.addParentTask(parentTask)));
	}

	/**
	 * GET Method to retrieve the tasks
	 * @return list of tasks
	 */
	@GetMapping("/tasks/{projectID}")
	public ResponseEntity<Response> getTasks(@PathVariable Long projectID) {
		return ResponseEntity.ok().body(new Response(null, pmService.getTasks(projectID), null));
	}
	
	/**
	 * GET Method to retrieve the task details
	 * @return task
	 */
	@GetMapping("/task/{taskID}")
	public ResponseEntity<Response> getTaskDetails(@PathVariable Long taskID) {
		return ResponseEntity.ok().body(new Response(null, pmService.getTask(taskID), null));
	}

	/**
	 * POST Method to save the task
	 * @param task
	 * @return Success Message
	 */
	@PostMapping("/addTask")
	public ResponseEntity<Response> addTask(@RequestBody Task task) {
		return ResponseEntity.ok().body(new Response(null, null, pmService.addTask(task)));
	}

	/**
	 * PUT Method to update the task
	 * @param task
	 * @return Success / Error Message
	 */
	@PutMapping("/updateTask")
	public ResponseEntity<Response> updateTask(@RequestBody Task task) {
		String output = pmService.updateTask(task);
		if ("invalid".equals(output)) {
			return ((BodyBuilder) ResponseEntity.notFound()).body(new Response("Invalid Task ID.", null, null));
		}
		return ResponseEntity.ok().body(new Response(null, null, output));
	}

	/**
	 * PUT Method to update the status of task to completed
	 * @param taskID
	 * @return Success / Error Message
	 */
	@PutMapping("/endTask")
	public ResponseEntity<Response> endTask(@RequestBody int taskID) {
		String output = pmService.endTask(taskID);
		if ("invalid".equals(output)) {
			return ((BodyBuilder) ResponseEntity.notFound()).body(new Response("Invalid Task ID.", null, null));
		}
		return ResponseEntity.ok().body(new Response(null, null, output));
	}
}
