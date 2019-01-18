package com.cts.springboot.service;

import java.util.List;

import com.cts.springboot.entity.ParentTask;
import com.cts.springboot.entity.Project;
import com.cts.springboot.entity.Task;
import com.cts.springboot.entity.User;

public interface ProjectManagerService {

	List<User> getUsers();

	String addUser(User user);

	String updateUser(User user);

	String deleteUser(int userID);

	List<Project> getProjects();

	String addProject(Project project);

	String updateProject(Project project);

	String suspendProject(int projectID);

	List<ParentTask> getParentTasks();

	String addParentTask(ParentTask parentTask);

	List<Task> getTasks();

	String addTask(Task task);

	String updateTask(Task task);

	String endTask(int taskID);

}
