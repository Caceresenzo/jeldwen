package jeldwen.beacon.service.impl;

import java.util.Objects;

import javax.annotation.PostConstruct;

import org.greenrobot.eventbus.Subscribe;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jeldwen.beacon.entity.HourPerHour;
import jeldwen.beacon.message.model.IBeaconMessage;
import jeldwen.beacon.message.model.config.BeaconConfig;
import jeldwen.beacon.message.model.config.ProductFamilyConfig;
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
import jeldwen.beacon.service.IBeaconService;
import jeldwen.beacon.service.IGraphicalUserInterfaceService;
import jeldwen.beacon.service.IHourPerHourService;
import jeldwen.beacon.state.IBeaconState;
import jeldwen.beacon.state.impl.OpenedBeaconState;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BeaconServiceImpl implements IBeaconService, DisposableBean {
	
	@Autowired
	private IBeaconMessageService beaconMessageService;
	
	@Autowired
	private IGraphicalUserInterfaceService graphicalUserInterfaceService;
	
	@Autowired
	private IHourPerHourService hourPerHourService;
	
	/* Variables */
	private IBeaconState state;
	private BeaconConfig beaconConfig;
	
	@PostConstruct
	private void initialize() {
		setState(new OpenedBeaconState());
		
		beaconMessageService.register(this);
	}
	
	@Override
	public void destroy() throws Exception {
		beaconMessageService.unregister(this);
	}
	
	@Override
	public IBeaconState setState(IBeaconState newState) {
		Objects.requireNonNull(newState, "newState == null");
		
		if (state != null) {
			state.beforeStateChange(this, newState);
		}
		
		state = newState;
		state.start(this, beaconConfig, hourPerHour());
		notifyState();
		
		log.info("Changed state: {}", state.getName());
		
		return state;
	}
	
	@Override
	public IBeaconState getState() {
		return state;
	}
	
	@Override
	public void open() {
		state.open(this);
	}
	
	@Override
	public void close() {
		state.close(this);
	}
	
	@Override
	@Scheduled(cron = "* * * * * *")
	public void tick() {
		if (isOpenAndConfigured() && state.tick(this, hourPerHour())) {
			graphicalUserInterfaceService.notify(new RhythmSyncEventMessage()
					.setSeconds(state.getSeconds())
					.setCurrentHourPerHour(hourPerHourService.currentDescriptor()));
		}
	}
	
	@Override
	public void notifyState() {
		notifyState(false);
	}
	
	@Override
	public void notifyState(boolean includeHistory) {
		graphicalUserInterfaceService.notify(new WorkstationStateResponse()
				.setOpened(state.isOpen(this))
				.setBeaconConfig(beaconConfig)
				.setActiveFamilyId(state.getActiveProductFamily())
				.setSeconds(state.getSeconds())
				.setCurrentHourPerHour(hourPerHourService.currentDescriptor())
				.setHourPerHourHistory(includeHistory ? hourPerHourService.history() : null));
	}
	
	@Override
	public void notify(IBeaconMessage message) {
		graphicalUserInterfaceService.notify(message);
	}
	
	@Override
	public void signal(boolean forced) {
		if (isOpenAndConfigured() && state.onSensorTrigger(this, hourPerHour(), forced)) {
			next();
		}
	}
	
	private void next() {
		graphicalUserInterfaceService.notify(new RhythmSyncEventMessage()
				.setSeconds(state.getSeconds())
				.setCurrentHourPerHour(hourPerHourService.currentDescriptor()));
	}
	
	@Subscribe
	public void onConfig(ConfigResponseMessage request) {
		if (request.isSuccess()) {
			beaconConfig = request.getBeaconConfig();
			
			if (state.onNewConfig(this, beaconConfig)) {
				notifyState();
			}
		}
	}
	
	@Subscribe
	public void onSocketConnectedEvent(SocketConnectedEvent request) {
		notifyState(true);
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
		if (!isOpenAndConfigured()) {
			return;
		}
		
		ProductFamilyConfig productFamily = beaconConfig.findProductFamily(request.getFamilyId());
		
		if (state.changeProductFamily(this, productFamily)) {
			graphicalUserInterfaceService.notify(FamilyChangedResponse.of(productFamily));
		} else {
			graphicalUserInterfaceService.notify(FamilyChangedResponse.nulled());
		}
	}
	
	@Subscribe
	public void onReportRequest(ReportRequest request) {
		if (!isOpenAndConfigured()) {
			return;
		}
		
		graphicalUserInterfaceService.notify(new ReportedResponse()
				.setSuccess(state.reportStopReason(this, beaconConfig.findStopReason(request.getStopReasonId()))));
	}
	
	@Subscribe
	public void onForceSensorTrigger(ForceSensorTriggerRequest request) {
		if (!isOpenAndConfigured()) {
			return;
		}
		
		if (state.onSensorTrigger(this, hourPerHour(), true)) {
			notifyState();
		}
	}
	
	private boolean isOpenAndConfigured() {
		return state.isOpen(this) && beaconConfig != null;
	}
	
	private HourPerHour hourPerHour() {
		return hourPerHourService.current();
	}
	
}