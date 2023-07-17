package com.goit.DevProject;

import com.goit.DevProject.Conf.FlywayConfigurations;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;

@SpringBootApplication
@EnableJpaRepositories("com/goit/DevProject/Repository")
public class DevProjectApplication {

	public static void main(String[] args) throws IOException {
		new FlywayConfigurations().setup().migrate();
		SpringApplication.run(DevProjectApplication.class, args);
	}

}
