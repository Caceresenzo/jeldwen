package jeldwen.beacon.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import jeldwen.beacon.message.model.config.BeaconConfig;
import jeldwen.beacon.service.IConfigService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConfigServiceImpl implements IConfigService {
	
	@Value("${storage.last-config}")
	private String lastConfigFilePath;
	
	/* Variables */
	private final ObjectMapper objectMapper;
	private BeaconConfig current;
	
	/* Constuctor */
	public ConfigServiceImpl() {
		this.objectMapper = new ObjectMapper();
	}
	
	@PostConstruct
	private void initialize() throws IOException {
		File targetFile = lastConfigFile();
		if (targetFile.exists()) {
			if (targetFile.length() == 0) {
				log.warn("No configuration loaded: file empty.");
			} else {
				try {
					current = load();
				} catch (Exception exception) {
					log.warn("Failed to load local config.", exception);
				}
			}
		} else {
			log.warn("No configuration loaded: file does not exists. (creating)");
			
			targetFile.createNewFile();
		}
	}
	
	@Override
	public void store(BeaconConfig beaconConfig) throws Exception {
		objectMapper.writeValue(lastConfigFile(), beaconConfig);
		
		current = beaconConfig;
	}
	
	@Override
	public BeaconConfig load() throws Exception {
		return objectMapper.readValue(lastConfigFile(), BeaconConfig.class);
	}
	
	@Override
	public BeaconConfig current() {
		return Objects.requireNonNull(current, "there is no current config");
	}
	
	public File lastConfigFile() {
		return new File(lastConfigFilePath);
	}
	
	@Override
	public String getUnique() throws SocketException {
		Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
		while (networkInterfaces.hasMoreElements()) {
			NetworkInterface ni = networkInterfaces.nextElement();
			byte[] hardwareAddress = ni.getHardwareAddress();
			if (hardwareAddress != null) {
				String[] hexadecimalFormat = new String[hardwareAddress.length];
				for (int i = 0; i < hardwareAddress.length; i++) {
					hexadecimalFormat[i] = String.format("%02X", hardwareAddress[i]);
				}
				return String.join("-", hexadecimalFormat);
			}
		}
		return null;
	}
	
}