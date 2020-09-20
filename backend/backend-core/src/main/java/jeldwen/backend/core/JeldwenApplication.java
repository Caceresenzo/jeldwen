package jeldwen.backend.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "jeldwen")
@EnableAutoConfiguration
@EnableConfigurationProperties
@EnableScheduling
@EnableJpaRepositories("jeldwen")
@ComponentScan("jeldwen")
@EntityScan("jeldwen")
public class JeldwenApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(JeldwenApplication.class, args);
	}
	
}