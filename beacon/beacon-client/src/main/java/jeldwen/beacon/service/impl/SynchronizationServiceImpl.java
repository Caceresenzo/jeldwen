package jeldwen.beacon.service.impl;

import javax.annotation.PostConstruct;

import org.greenrobot.eventbus.Subscribe;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jeldwen.beacon.message.model.response.impl.auth.AuthResponseMessage;
import jeldwen.beacon.message.service.IBeaconMessageService;
import jeldwen.beacon.service.ISynchronizationService;

@Service
public class SynchronizationServiceImpl implements ISynchronizationService, DisposableBean {
	
	@Autowired
	private IBeaconMessageService beaconMessageService;
	
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
		System.out.println(message);
	}
	
}