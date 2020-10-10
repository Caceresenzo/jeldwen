package jeldwen.beacon;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import jeldwen.beacon.message.model.message.request.impl.productfamily.FamilyChangeRequest;
import jeldwen.beacon.message.model.message.request.impl.stopreason.ReportRequest;
import jeldwen.beacon.message.model.message.response.impl.productfamily.FamilyChangedResponse;
import jeldwen.beacon.message.model.message.response.impl.stopreason.ReportedResponse;
import jeldwen.beacon.message.service.IBeaconMessageService;

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
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@PostConstruct
	private void initialize() {
		IBeaconMessageService beaconMessageService = applicationContext.getBean(IBeaconMessageService.class);
		
		beaconMessageService.registerRequestClass(FamilyChangeRequest.NAME, FamilyChangeRequest.class);
		beaconMessageService.registerRequestClass(ReportRequest.NAME, ReportRequest.class);
		
		beaconMessageService.registerResponseClass(FamilyChangedResponse.NAME, FamilyChangedResponse.class);
		beaconMessageService.registerResponseClass(ReportedResponse.NAME, ReportedResponse.class);
	}
	
}