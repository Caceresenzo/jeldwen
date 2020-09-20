package jeldwen.beacon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "jeldwen")
@EnableAutoConfiguration
@EnableConfigurationProperties
@EnableScheduling
@ComponentScan("jeldwen")
@EntityScan("jeldwen")
public class BeaconApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BeaconApplication.class, args);
	}
	
}