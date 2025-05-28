package com.example.taskmanagerback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TaskManagerBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagerBackApplication.class, args);
	}

}
