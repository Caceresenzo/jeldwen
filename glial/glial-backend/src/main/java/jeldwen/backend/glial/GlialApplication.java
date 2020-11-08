package jeldwen.backend.glial;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

@SpringBootApplication(scanBasePackages = "jeldwen")
@EnableAutoConfiguration
@EnableConfigurationProperties
@EnableScheduling
@EnableJpaRepositories("jeldwen")
@ComponentScan("jeldwen")
@EntityScan("jeldwen")
public class GlialApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(GlialApplication.class, args);
	}
	
	@Bean
	public CsvMapper csvMapper() {
		CsvMapper mapper = new CsvMapper();
		mapper.findAndRegisterModules();
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		
		return mapper;
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	@Primary
	public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTimeSerializer localDateTimeSerializer = new LocalDateTimeSerializer(formatter);
		LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer(formatter);
		
		formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateSerializer localDateSerializer = new LocalDateSerializer(formatter);
		LocalDateDeserializer localDateDeserializer = new LocalDateDeserializer(formatter);
		
		JavaTimeModule module = new JavaTimeModule();
		module.addSerializer(LocalDateTime.class, localDateTimeSerializer);
		module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
		module.addSerializer(LocalDate.class, localDateSerializer);
		module.addDeserializer(LocalDate.class, localDateDeserializer);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.registerModule(module);
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		
		return mapper;
	}
	
}