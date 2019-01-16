package com.cts.springboot.service;

import java.util.List;

import com.cts.springboot.entity.User;

public interface ProjectManagerService {

	List<User> getUsers();

	String addUser(User user);

	String updateUser(User user);

	String deleteUser(int id);

}
