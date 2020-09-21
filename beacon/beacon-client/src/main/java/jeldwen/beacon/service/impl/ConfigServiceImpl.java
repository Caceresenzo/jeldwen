package jeldwen.beacon.service.impl;

import java.io.File;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

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