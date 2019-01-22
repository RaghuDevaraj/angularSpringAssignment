package com.cts.springboot.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cts.springboot.entity.User;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class UserRepositoryTests {

	@Autowired
	private UserRepository repository;	
	
	@Test
	public void save_ReturnsUser() throws Exception {
		User user = new User(null, "Raghu", "Devaraj","326452");
		User savedUser = repository.save(user);		
		assertThat(repository.findById(savedUser.getUserID()).orElse(null)).isEqualTo((savedUser));		
	}
	
	@Test
	public void findAll_ReturnsUsers() throws Exception {
		
		User user1 = new User(null, "Sugriev", "Prathap","754477");
		User user2 = new User(null, "Raghu", "Devaraj","326452");
		
		repository.save(user1);
		repository.save(user2);
		
		List<User> userDetails = this.repository.findAll();
		assertThat(userDetails).hasSize(3);
		assertThat(userDetails.get(0).getFirstName()).isEqualTo("Sugriev");
	}
	
	@Test 
	public void update_ReturnsUser() throws Exception {
		User user = new User(null, "Sugriev", "Prathap","754477");
		User savedUser = repository.save(user);		
		
		savedUser.setEmployeeID("5345356");
		User updatedUser = repository.save(savedUser);
		
		assertThat(this.repository.findAll()).hasSize(1).contains(updatedUser);
	}
	
	@Test
	public void remove_ReturnsNothing() throws Exception {
		User user = new User(null, "Sugriev", "Prathap","754477");
		User savedUser = repository.save(user);
		
		repository.deleteById(savedUser.getUserID());
		
		assertThat(this.repository.findById(savedUser.getUserID()).orElse(null)).isNull();
	}
	
}
