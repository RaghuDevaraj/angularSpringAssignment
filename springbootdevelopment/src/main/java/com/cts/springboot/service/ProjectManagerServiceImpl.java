package com.cts.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.springboot.domain.UserRepository;
import com.cts.springboot.entity.ParentTask;
import com.cts.springboot.entity.Project;
import com.cts.springboot.entity.Task;
import com.cts.springboot.entity.User;

@Service
public class ProjectManagerServiceImpl implements ProjectManagerService {
	
	@Autowired
	private UserRepository userRepository;

	public ProjectManagerServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<User> getUsers() {
		return userRepository.findAll();
	}

	public String addUser(User user) {

		return null;
	}

	public String updateUser(User user) {

		return null;
	}

	public String deleteUser(int userID) {

		return null;
	}

	public List<Project> getProjects() {

		return null;
	}

	public String addProject(Project project) {

		return null;
	}

	public String updateProject(Project project) {

		return null;
	}

	public String suspendProject(int projectID) {

		return null;
	}

	public List<ParentTask> getParentTasks() {

		return null;
	}

	public String addParentTask(ParentTask parentTask) {

		return null;
	}

	public List<Task> getTasks() {

		return null;
	}

	public String addTask(Task task) {

		return null;
	}

	public String updateTask(Task task) {

		return null;
	}

	public String endTask(int taskID) {

		return null;
	}

}
