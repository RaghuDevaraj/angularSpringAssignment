package com.cts.springboot.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.cts.springboot.entity.User;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTests {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	@Test
	public void findAll_ReturnsUsers() throws Exception {
		List<User> users = new ArrayList<User>();
		users.add(new User(1L, "Raghu", "Devaraj","326452"));
		users.add(new User(2L, "Sugriev", "Prathap","754477"));
		List<User> savedUsers = testEntityManager.persistFlushFind(users);
		List<User> userDetails = this.repository.findAll();
		assertThat(userDetails.get(0).getFirstName()).isEqualTo(savedUsers.get(0).getFirstName());
		assertThat(userDetails.get(1).getFirstName()).isEqualTo(savedUsers.get(1).getFirstName());
	}
}
