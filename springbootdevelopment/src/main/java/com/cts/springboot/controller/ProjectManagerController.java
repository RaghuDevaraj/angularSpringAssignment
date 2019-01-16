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

import com.cts.springboot.entity.Response;
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

}
