package jeldwen.beacon.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.greenrobot.eventbus.Subscribe;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jeldwen.beacon.message.model.config.BeaconConfig;
import jeldwen.beacon.message.model.config.ProductFamilyConfig;
import jeldwen.beacon.message.model.config.StopReasonConfig;
import jeldwen.beacon.message.model.message.event.impl.rhythm.RhythmSyncEventMessage;
import jeldwen.beacon.message.model.message.request.impl.productfamily.FamilyChangeRequest;
import jeldwen.beacon.message.model.message.request.impl.sensor.ForceSensorTriggerRequest;
import jeldwen.beacon.message.model.message.request.impl.stopreason.ReportRequest;
import jeldwen.beacon.message.model.message.request.impl.workstation.WorkstationCloseRequest;
import jeldwen.beacon.message.model.message.request.impl.workstation.WorkstationOpenRequest;
import jeldwen.beacon.message.model.message.response.impl.config.ConfigResponseMessage;
import jeldwen.beacon.message.model.message.response.impl.productfamily.FamilyChangedResponse;
import jeldwen.beacon.message.model.message.response.impl.stopreason.ReportedResponse;
import jeldwen.beacon.message.model.message.response.impl.workstation.WorkstationStateResponse;
import jeldwen.beacon.message.service.IBeaconMessageService;
import jeldwen.beacon.model.event.SocketConnectedEvent;
import jeldwen.beacon.service.ICycleService;
import jeldwen.beacon.service.IHourPerHourService;

@Service
public class CycleServiceImpl implements ICycleService, DisposableBean {
	
	@Autowired
	private IBeaconMessageService beaconMessageService;
	
	@Autowired
	private IGraphicalUserInterfaceService graphicalUserInterfaceService;
	
	@Autowired
	private IHourPerHourService hourPerHourService;
	
	/* Variables */
	private BeaconConfig config;
	private boolean opened = true;
	private ProductFamilyConfig activeProductFamily;
	private StopReasonConfig currentStopReason;
	private LocalDateTime start;
	
	@PostConstruct
	private void initialize() {
		beaconMessageService.register(this);
	}
	
	@Override
	public void destroy() throws Exception {
		beaconMessageService.unregister(this);
	}
	
	@Override
	public boolean open() {
		if (!opened) {
			opened = true;
		}
		
		notifyState();
		
		return opened;
	}
	
	@Override
	public boolean close() {
		if (opened) {
			invalidate();
			
			opened = false;
		}
		
		notifyState();
		
		return !opened;
	}
	
	@Override
	public boolean isUsable() {
		return opened && config != null;
	}
	
	@Override
	public boolean isOpen() {
		return opened;
	}
	
	@Override
	public boolean isClose() {
		return !opened;
	}
	
	@Override
	public boolean next() {
		if (isUsable()) {
			hourPerHourService.current().incrementProduced();
			start = LocalDateTime.now();
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean useProductFamily(ProductFamilyConfig productFamily) {
		if (activeProductFamily == null || productFamily.getId() != activeProductFamily.getId()) {
			this.activeProductFamily = productFamily;
			this.start = LocalDateTime.now();
		}
		
		graphicalUserInterfaceService.notify(new FamilyChangedResponse().setActiveFamilyId(activeProductFamily.getId()));
		pulseCycle();

		return false;
	}
	
	@Override
	public boolean report(StopReasonConfig stopReason) {
		if (opened) {
			if (this.currentStopReason != null) {
			}
			
			this.currentStopReason = stopReason;
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean isStop() {
		if (start != null && activeProductFamily != null) {
			return start.plusSeconds(activeProductFamily.getCycleTime()).isBefore(LocalDateTime.now());
		}
		
		return false;
	}
	
	@Override
	public void setConfig(BeaconConfig config) {
		this.config = config;
		
		invalidate();
	}
	
	@Scheduled(cron = "* * * * * *")
	@Override
	public void pulseCycle() {
		if (start != null) {
			if (isOpen()) {
				hourPerHourService.current().incrementOpen();
			}
			
			if (isStop()) {
				hourPerHourService.current().incrementStop();
			}
			
			graphicalUserInterfaceService.notify(new RhythmSyncEventMessage()
					.setSeconds(seconds())
					.setCurrentHourPerHour(hourPerHourService.currentDescriptor()));
		}
	}
	
	@Override
	public void signal() {
		next();
		notifyState(false);
	}
	
	@Subscribe
	public void onConfig(ConfigResponseMessage request) {
		if (request.isSuccess()) {
			setConfig(request.getBeaconConfig());
			notifyState();
		}
	}
	
	@Subscribe
	public void onSocketConnectedEvent(SocketConnectedEvent request) {
		notifyState();
	}
	
	@Subscribe
	public void onWorkstationOpenRequest(WorkstationOpenRequest request) {
		open();
	}
	
	@Subscribe
	public void onWorkstationCloseRequest(WorkstationCloseRequest request) {
		close();
	}
	
	@Subscribe
	public void onFamilyChangeRequest(FamilyChangeRequest request) {
		long familyId = request.getFamilyId();
		
		if (config != null) {
			Optional<ProductFamilyConfig> optionel = config.getProductFamilies()
					.stream()
					.filter((family) -> family.getId() == familyId)
					.findFirst();
			
			if (optionel.isPresent()) {
				useProductFamily(optionel.get());
				
				return;
			}
		}
		
		graphicalUserInterfaceService.notify(FamilyChangedResponse.zero());
	}
	
	@Subscribe
	public void onReportRequest(ReportRequest request) {
		graphicalUserInterfaceService.notify(new ReportedResponse());
		this.start = LocalDateTime.now();
		notifyState();
	}
	
	@Subscribe
	public void onForceSensorTrigger(ForceSensorTriggerRequest request) {
		signal();
	}
	
	@Override
	public void notifyState() {
		notifyState(false);
	}
	
	@Override
	public void notifyState(boolean withHistory) {
		graphicalUserInterfaceService.notify(new WorkstationStateResponse()
				.setOpened(opened)
				.setBeaconConfig(config)
				.setActiveFamilyId(activeProductFamily != null ? activeProductFamily.getId() : 0)
				.setSeconds(seconds())
				.setCurrentHourPerHour(hourPerHourService.currentDescriptor())
				.setHourPerHourHistory(withHistory ? hourPerHourService.history() : null));
	}
	
	private long seconds() {
		if (start == null) {
			return -1;
		}
		
		return Duration.between(start, LocalDateTime.now()).getSeconds();
	}
	
	private void invalidate() {
		this.activeProductFamily = null;
		this.start = null;
	}
	
}