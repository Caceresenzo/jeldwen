package jeldwen.beacon.service.impl;

import javax.annotation.PostConstruct;

import org.greenrobot.eventbus.Subscribe;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jeldwen.beacon.message.model.message.request.impl.config.ConfigRequestMessage;
import jeldwen.beacon.message.model.message.response.impl.auth.AuthResponseMessage;
import jeldwen.beacon.message.model.message.response.impl.config.ConfigResponseMessage;
import jeldwen.beacon.message.service.IBeaconMessageService;
import jeldwen.beacon.service.IConfigService;
import jeldwen.beacon.service.ISocketService;
import jeldwen.beacon.service.ISynchronizationService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SynchronizationServiceImpl implements ISynchronizationService, DisposableBean {
	
	@Autowired
	private IBeaconMessageService beaconMessageService;
	
	@Autowired
	private ISocketService socketService;
	
	@Autowired
	private IConfigService configService;
	
	@PostConstruct
	private void initialize() {
		beaconMessageService.register(this);
	}
	
	@Override
	public void destroy() throws Exception {
		beaconMessageService.unregister(this);
	}
	
	@Subscribe
	public void onAuthenticated(AuthResponseMessage message) {
		if (message.isSuccess()) {
			log.info("Authenticated");
			
			socketService.send(new ConfigRequestMessage());
		} else {
			log.error("Failed to authenticate: {}", message.getReason());
		}
	}
	
	@Subscribe
	public void onConfig(ConfigResponseMessage message) throws Exception {
		if (message.isSuccess()) {
			configService.store(message.getBeaconConfig());
			
			log.info("New config received (forced: {})", message.isForced());
		} else {
			log.error("Failed to receive config from server: {}", message.getReason());
		}
	}
	
}