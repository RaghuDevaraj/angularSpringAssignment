package com.cts.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.springboot.domain.ParentTaskRepository;
import com.cts.springboot.domain.ProjectRepository;
import com.cts.springboot.domain.TaskRepository;
import com.cts.springboot.domain.UserRepository;
import com.cts.springboot.entity.ParentTask;
import com.cts.springboot.entity.Project;
import com.cts.springboot.entity.Task;
import com.cts.springboot.entity.User;

@Service
public class ProjectManagerServiceImpl implements ProjectManagerService {

	/**
	 * Dependency Injection of UserRepository
	 */
	@Autowired
	private UserRepository userRepository;

	/**
	 * Dependency Injection of ProjectRepository
	 */
	@Autowired
	private ProjectRepository projectRepository;

	/**
	 * Dependency Injection of ParentTaskRepository
	 */
	@Autowired
	private ParentTaskRepository parentTaskRepository;

	/**
	 * Dependency Injection of TaskRepository
	 */
	@Autowired
	private TaskRepository taskRepository;

	/**
	 * Class Variable
	 */
	String message = "";

	/**
	 * Constructor for test dependency injection
	 * 
	 * @param userRepository
	 * @param projectRepository
	 * @param parentTaskRepository
	 * @param taskRepository
	 */
	public ProjectManagerServiceImpl(UserRepository userRepository, ProjectRepository projectRepository,
			ParentTaskRepository parentTaskRepository, TaskRepository taskRepository) {
		this.userRepository = userRepository;
		this.projectRepository = projectRepository;
		this.parentTaskRepository = parentTaskRepository;
		this.taskRepository = taskRepository;
	}

	/**
	 * Returns List of users
	 */
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	/**
	 * Adds the user and returns success message
	 */
	public String addUser(User user) {
		User savedUser = userRepository.saveAndFlush(user);
		message = "User - " + savedUser.getFirstName() + " " + savedUser.getLastName() + " is added successfully.";
		return message;
	}

	/**
	 * Updates the user and returns success / failure message
	 */
	public String updateUser(User user) {
		User isValidUser = userRepository.findById(user.getUserID()).orElse(null);
		if (isValidUser != null) {
			User updatedUser = userRepository.saveAndFlush(user);
			message = "User - " + updatedUser.getFirstName() + " " + updatedUser.getLastName()
					+ " is updated successfully.";
		} else {
			message = "invalid";
		}
		return message;
	}

	/**
	 * Deletes the user and returns success / failure message
	 */
	public String deleteUser(int userID) {
		User user = userRepository.findById(Long.valueOf(userID)).orElse(null);
		if (user != null) {
			userRepository.delete(user);
			message = "User - " + user.getFirstName() + " " + user.getLastName() + " is deleted successfully.";
		} else {
			message = "invalid";
		}
		return message;
	}

	/**
	 * Returns list of projects
	 */
	public List<Project> getProjects() {
		return projectRepository.findAll();
	}

	/**
	 * Adds the project and returns success message
	 */
	public String addProject(Project project) {
		Project savedProject = projectRepository.saveAndFlush(project);
		message = "Project - " + savedProject.getProjectName() + " is added successfully.";
		return message;
	}

	/**
	 * Updates the project and returns success / failure message
	 */
	public String updateProject(Project project) {
		Project prj = projectRepository.findById(project.getProjectID()).orElse(null);
		if (prj != null) {
			Project updatedProject = projectRepository.saveAndFlush(project);
			message = "Project - " + updatedProject.getProjectName() + " is updated successfully.";
		} else {
			message = "invalid";
		}
		return message;
	}

	/**
	 * Suspends the project and returns success / failure message
	 */
	public String suspendProject(int projectID) {
		Project project = projectRepository.findById(Long.valueOf(projectID)).orElse(null);
		if (project != null) {
			taskRepository.suspendTasksByProject(project.getProjectID());
			message = "Project - " + project.getProjectName() + " is suspended successfully.";
		} else {
			message = "invalid";
		}
		return message;
	}

	/**
	 * Returns list of parent tasks
	 */
	public List<ParentTask> getParentTasks() {
		return parentTaskRepository.findAll();
	}

	/**
	 * Adds the parent task and returns success message
	 */
	public String addParentTask(ParentTask parentTask) {
		ParentTask savedParentTask = parentTaskRepository.saveAndFlush(parentTask);
		message = "ParentTask - " + savedParentTask.getParentTaskName() + " is added successfully.";
		return message;
	}

	/**
	 * Returns list of tasks
	 */
	public List<Task> getTasks() {
		return taskRepository.findAll();
	}

	/**
	 * Adds the task and returns success message
	 */
	public String addTask(Task task) {
		Task savedTask = taskRepository.saveAndFlush(task);
		message = "Task - " + savedTask.getTaskName() + " is added successfully.";
		return message;
	}

	/**
	 * Updates the task and returns success / failure message
	 */
	public String updateTask(Task task) {
		Task tsk = taskRepository.findById(task.getTaskID()).orElse(null);
		if (tsk != null) {
			Task updatedTask = taskRepository.saveAndFlush(task);
			message = "Task - " + updatedTask.getTaskName() + " is updated successfully.";
		} else {
			message = "invalid";
		}
		return message;
	}

	/**
	 * Ends the task and returns success / failure message
	 */
	public String endTask(int taskID) {
		Task task = taskRepository.findById(Long.valueOf(taskID)).orElse(null);
		if (task != null) {
			taskRepository.endTaskByID(Long.valueOf(taskID));
			message = "Task - " + task.getTaskName() + " is ended successfully.";
		} else {
			message = "invalid";
		}
		return message;
	}
}
