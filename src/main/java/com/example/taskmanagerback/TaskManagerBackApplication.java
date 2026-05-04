package com.example.taskmanagerback;

import com.example.taskmanagerback.config.keycloak.KeycloakProperties;
import com.example.taskmanagerback.config.minio.MinioProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableJpaRepositories
@EnableMethodSecurity
@EnableConfigurationProperties({MinioProperties.class, KeycloakProperties.class})
public class TaskManagerBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagerBackApplication.class, args);
	}

}
