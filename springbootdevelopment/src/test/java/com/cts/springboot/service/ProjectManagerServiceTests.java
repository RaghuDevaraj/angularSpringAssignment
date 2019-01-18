package com.cts.springboot.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.cts.springboot.domain.UserRepository;
import com.cts.springboot.entity.User;


@RunWith(MockitoJUnitRunner.class)
public class ProjectManagerServiceTests {

	@Mock 
	private UserRepository userRepository;
	
	private ProjectManagerServiceImpl projectManagerService;
	
	@Before
	public void setUp() throws Exception {
		projectManagerService = new ProjectManagerServiceImpl(userRepository);
	}
	
	@Test
	public void getUsers_returnsUsersInfo() {
		List<User> users = new ArrayList<User>();
		users.add(new User(1L,"Raghu","Devaraj","326452"));
		users.add(new User(2L,"Sugriev","Prathap","852342"));
		given(userRepository.findAll()).willReturn(users);

		List<User> userDetails = projectManagerService.getUsers();

		assertThat(userDetails.get(0).getFirstName()).isEqualTo("Raghu");
		assertThat(userDetails.get(0).getEmployeeID()).isEqualTo("326452");
	}
}
