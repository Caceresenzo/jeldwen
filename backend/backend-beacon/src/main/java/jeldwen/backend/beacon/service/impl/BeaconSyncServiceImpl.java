package jeldwen.backend.beacon.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jeldwen.backend.beacon.entity.Beacon;
import jeldwen.backend.beacon.entity.Detector;
import jeldwen.backend.beacon.entity.ProductFamily;
import jeldwen.backend.beacon.entity.StopReason;
import jeldwen.backend.beacon.entity.StopReasonEntry;
import jeldwen.backend.beacon.model.BeaconFamilyTaskContext;
import jeldwen.backend.beacon.repository.BeaconRepository;
import jeldwen.backend.beacon.repository.ProductFamilyRepository;
import jeldwen.backend.beacon.repository.StopReasonEntryRepository;
import jeldwen.backend.beacon.repository.StopReasonRepository;
import jeldwen.backend.beacon.server.BeaconSocketServer;
import jeldwen.backend.beacon.service.IBeaconClientService;
import jeldwen.backend.beacon.service.IBeaconSyncService;
import jeldwen.beacon.message.model.IBeaconMessage;
import jeldwen.beacon.message.model.request.impl.auth.AuthRequestMessage;
import jeldwen.beacon.message.model.response.impl.auth.AuthResponseMessage;
import lombok.SneakyThrows;

@Service
public class BeaconSyncServiceImpl implements IBeaconSyncService, DisposableBean {
	
	@Autowired
	private BeaconRepository beaconRepository;
	
	@Autowired
	private StopReasonRepository stopReasonRepository;
	
	@Autowired
	private StopReasonEntryRepository stopReasonEntryRepository;
	
	@Autowired
	private ProductFamilyRepository productFamilyRepository;
	
	@Autowired
	private IBeaconClientService beaconClientService;
	
	/* Variables */
	private final Map<Long, BeaconFamilyTaskContext> started;
	private BeaconSocketServer socketServer;
	
	/* Constructor */
	public BeaconSyncServiceImpl() {
		this.started = new HashMap<>();
	}
	
	@PostConstruct
	@SneakyThrows
	private void postConstruct() {
		socketServer = new BeaconSocketServer(5000);
		socketServer.start();
		
		socketServer.setOnMessage((webSocket, message) -> {
			try {
				IBeaconMessage beaconMessage = beaconClientService.parseMessage(message);
				
				if (beaconMessage instanceof AuthRequestMessage) {
					Detector client = beaconClientService.createIfNotExistsClient((AuthRequestMessage) beaconMessage);
					
					webSocket.send(beaconClientService.stringifyMessage(new AuthResponseMessage()));
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		});
	}
	
	@Override
	public void destroy() throws Exception {
		if (socketServer != null) {
			socketServer.stop();
		}
	}
	
	@Override
	public BeaconFamilyTaskContext start(long beaconId, long productFamilyId) {
		Beacon beacon = beaconRepository.findById(beaconId).orElse(null);
		ProductFamily productFamily = productFamilyRepository.findById(productFamilyId).orElse(null);
		
		if (beacon != null && productFamily != null) {
			BeaconFamilyTaskContext context = started.get(beacon.getId());
			if (context != null) {
				closeBecause(beacon, null);
			}
			
			context = new BeaconFamilyTaskContext(beacon, productFamily);
			
			return context;
		}
		
		return null;
	}
	
	@Override
	public BeaconFamilyTaskContext closeBecause(long beaconId, long stopReasonId) {
		return closeBecause(beaconRepository.findById(beaconId).orElse(null), stopReasonRepository.findById(stopReasonId).orElse(null));
	}
	
	public BeaconFamilyTaskContext closeBecause(Beacon beacon, StopReason stopReason) {
		if (beacon != null) {
			BeaconFamilyTaskContext context = started.get(beacon.getId());
			
			if (context != null) {
				String key = stopReason != null ? stopReason.getKey() : null;
				
				StopReasonEntry entry = stopReasonEntryRepository.findByKeyEqualsAndBeaconEquals(key, beacon).orElseGet(() -> {
					return new StopReasonEntry().setKey(key).setBeacon(beacon);
				});
				
				stopReasonEntryRepository.save(entry
						.incrementUsed()
						.addSecondsToCumulated(context.getDuration()));
				
				return context;
			}
		}
		
		return null;
	}
	
}