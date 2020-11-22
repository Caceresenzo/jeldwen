package jeldwen.beacon.service.impl;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.greenrobot.eventbus.Subscribe;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jeldwen.beacon.entity.ReportedStopReason;
import jeldwen.beacon.message.model.config.ProductFamilyConfig;
import jeldwen.beacon.message.model.config.StopReasonConfig;
import jeldwen.beacon.message.model.message.request.impl.report.StopReasonReportRequest;
import jeldwen.beacon.message.model.message.response.impl.report.ReportedStopReasonResponse;
import jeldwen.beacon.message.service.IBeaconMessageService;
import jeldwen.beacon.repository.ReportedStopReasonRepository;
import jeldwen.beacon.service.ISocketService;
import jeldwen.beacon.service.IStopReasonService;

@Service
public class StopReasonServiceImpl implements IStopReasonService, DisposableBean {
	
	/* Constants */
	private static final Object LOCK = new Object();
	
	@Autowired
	private IBeaconMessageService beaconMessageService;
	
	@Autowired
	private ISocketService socketService;
	
	@Autowired
	private ReportedStopReasonRepository reportedStopReasonRepository;
	
	/* Variables */
	private boolean canFlush;
	
	@PostConstruct
	private void initialize() {
		synchronized (LOCK) {
			canFlush = true;
			
			reportedStopReasonRepository.findAll()
					.stream()
					.map(ReportedStopReason::setAsNotSent)
					.forEach(reportedStopReasonRepository::save);
		}
		
		beaconMessageService.register(this);
	}
	
	@Override
	public void destroy() throws Exception {
		beaconMessageService.unregister(this);
	}
	
	@Override
	public void reportNow(String message, ProductFamilyConfig productFamily, long duration) {
		Objects.requireNonNull(productFamily, "productFamily == null");
		
		sendReportedStopReason(new ReportedStopReason()
				.setMessage(message)
				.setProductFamilyId(productFamily.getId())
				.setDuration(duration)
				.setAt(LocalDateTime.now()));
	}
	
	@Override
	public void reportNow(StopReasonConfig stopReason, ProductFamilyConfig productFamily, long duration) {
		Objects.requireNonNull(stopReason, "stopReason == null");
		Objects.requireNonNull(productFamily, "productFamily == null");
		
		sendReportedStopReason(new ReportedStopReason()
				.setStopReasonId(stopReason.getId())
				.setProductFamilyId(productFamily.getId())
				.setDuration(duration)
				.setAt(LocalDateTime.now()));
	}
	
	@Override
	public void sendReportedStopReason(ReportedStopReason reportedStopReason) {
		Objects.requireNonNull(reportedStopReason, "reportedStopReason == null");
		
		if (reportedStopReason.getId() == 0) {
			reportedStopReason = reportedStopReasonRepository.save(reportedStopReason);
		}
		
		boolean sent = true;
		try {
			socketService.send(new StopReasonReportRequest()
					.setId(reportedStopReason.getId())
					.setStopReasonId(reportedStopReason.getStopReasonId())
					.setMessage(reportedStopReason.getMessage())
					.setProductFamilyId(reportedStopReason.getProductFamilyId())
					.setDuration(reportedStopReason.getDuration())
					.setAt(reportedStopReason.getAt()));
		} catch (Exception exception) {
			sent = false;
		}
		
		reportedStopReasonRepository.save(reportedStopReason.setSent(sent));
	}
	
	@Override
	@Scheduled(fixedDelay = 5000)
	public void flush() {
		if (canFlush) {
			reportedStopReasonRepository.findAllBySentFalse().forEach(this::sendReportedStopReason);
		}
	}
	
	@Subscribe
	public void onReportedStopReason(ReportedStopReasonResponse response) {
		reportedStopReasonRepository.deleteById(response.getId());
	}
	
}