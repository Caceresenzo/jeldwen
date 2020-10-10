package jeldwen.beacon.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jeldwen.beacon.entity.HourPerHour;
import jeldwen.beacon.message.model.descriptor.SimpleHourPerHourDescriptor;
import jeldwen.beacon.repository.HourPerHourRepository;
import jeldwen.beacon.service.ICycleService;
import jeldwen.beacon.service.IHourPerHourService;

@Service
public class HourPerHourServiceImpl implements IHourPerHourService, DisposableBean {
	
	/* Constants */
	public static final Object LOCK = new Object();
	
	@Autowired
	private HourPerHourRepository hourPerHourRepository;
	
	@Autowired
	private ICycleService cycleService;
	
	/* Variables */
	private HourPerHour currentHourPerHour;
	private boolean finishing;
	
	@PostConstruct
	private void initialize() {
		synchronized (LOCK) {
			currentHourPerHour = findOrCreateHourlyCycle();
		}
	}
	
	@Override
	public void destroy() throws Exception {
		synchronized (LOCK) {
			finishing = true;
			
			saveHourPerHour();
		}
	}
	
	@Scheduled(cron = "0 * * * * *")
	public void minutlyCycle() {
		synchronized (LOCK) {
			if (finishing) {
				return;
			}
			
			saveHourPerHour();
		}
	}
	
	@Scheduled(cron = "1 0 * * * *")
	public void hourlyCycle() {
		synchronized (LOCK) {
			if (finishing) {
				return;
			}
			
			saveHourPerHour();
			findOrCreateHourlyCycle();
		}
		
		cycleService.notifyState(true);
	}
	
	private boolean saveHourPerHour() {
		if (currentHourPerHour != null) {
			return hourPerHourRepository.save(currentHourPerHour) != null;
		}
		
		return false;
	}
	
	private HourPerHour findOrCreateHourlyCycle() {
		LocalDate date = LocalDate.now();
		long hour = LocalTime.now().getHour();
		
		Optional<HourPerHour> optional = hourPerHourRepository.findByDateAndHour(date, hour);
		if (optional.isPresent()) {
			return optional.get();
		}
		
		return hourPerHourRepository.save(new HourPerHour()
				.setDate(date)
				.setHour(hour));
	}
	
	@Override
	public HourPerHour current() {
		synchronized (LOCK) {
			return currentHourPerHour;
		}
	}
	
	@Override
	public SimpleHourPerHourDescriptor currentDescriptor() {
		HourPerHour hourPerHour = current();
		
		return new SimpleHourPerHourDescriptor()
				.setDate(hourPerHour.getDate())
				.setHour(hourPerHour.getHour())
				.setOpen(hourPerHour.getOpen())
				.setStop(hourPerHour.getStop())
				.setObjective(hourPerHour.getObjective())
				.setProduced(hourPerHour.getProduced());
	}

	@Override
	public List<SimpleHourPerHourDescriptor> history() {
		return null;
	}
	
}