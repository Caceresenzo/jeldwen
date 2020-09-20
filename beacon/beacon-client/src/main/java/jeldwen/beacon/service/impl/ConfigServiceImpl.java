package jeldwen.beacon.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import jeldwen.beacon.message.model.config.BeaconConfig;
import jeldwen.beacon.service.IConfigService;

@Service
public class ConfigServiceImpl implements IConfigService {
	
	@Value("{storage.last-config}")
	private String lastConfigFilePath;
	
	/* Variables */
	private final ObjectMapper objectMapper;
	
	/* Constuctor */
	public ConfigServiceImpl() {
		this.objectMapper = new ObjectMapper();
	}
	
	@Override
	public void store(BeaconConfig beaconConfig) throws Exception {
		objectMapper.writeValue(lastConfigFile(), beaconConfig);
	}
	
	@Override
	public BeaconConfig load() throws Exception {
		return objectMapper.readValue(lastConfigFile(), BeaconConfig.class);
	}
	
	public File lastConfigFile() {
		return new File(lastConfigFilePath);
	}
	
}