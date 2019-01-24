package com.cts.springboot.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cts.springboot.entity.Project;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class ProjectRepositoryTests {

	@Autowired
	private ProjectRepository repository;

	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	@Test
	public void save_ReturnsProject() throws Exception {
		Project project = new Project(null, "Anthem Broker Portal", df.parse("2019-02-01"), df.parse("2019-10-30"), 3);
		project.setUser(1L);

		Project savedProject = repository.save(project);
		Project pp = repository.findById(savedProject.getProjectID()).orElse(null);
		assertThat(pp).isEqualTo((savedProject));

	}

	@Test
	public void findAll_ReturnsProjects() throws Exception {
		Project project1 = new Project(null, "Anthem Broker Portal", df.parse("2019-02-01"), df.parse("2019-10-30"), 3);
		Project project2 = new Project(null, "Anthem Shopper", df.parse("2019-05-01"), df.parse("2019-08-30"), 2);

		project1.setUser(2L);
		project2.setUser(3L);

		repository.save(project1);
		repository.save(project2);

		List<Project> projectetails = this.repository.findAll();
		assertThat(projectetails.get(0).getProjectName()).isEqualTo("Anthem Broker Portal");
	}

	@Test
	public void update_ReturnsProject() throws Exception {
		Project project = new Project(null, "Anthem Broker Portal", df.parse("2019-02-01"), df.parse("2019-10-30"), 3);
		project.setUser(2L);
		Project savedProject = repository.save(project);

		savedProject.setUser(3L);
		Project updatedProject = repository.save(savedProject);

		assertThat(this.repository.findById(updatedProject.getProjectID()).orElse(null)).isEqualTo(updatedProject);
	}

}
