package jeldwen.beacon.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jeldwen.beacon.entity.HourPerHour;
import jeldwen.beacon.model.descriptor.SimpleHourPerHourDescriptor;
import jeldwen.beacon.repository.HourPerHourRepository;
import jeldwen.beacon.service.IBeaconService;
import jeldwen.beacon.service.IHourPerHourService;
import jeldwen.beacon.service.ISocketService;

@Service
public class HourPerHourServiceImpl implements IHourPerHourService, DisposableBean {
	
	/* Constants */
	public static final Object LOCK = new Object();
	
	@Autowired
	private HourPerHourRepository hourPerHourRepository;
	
	@Autowired
	private IBeaconService beaconService;
	
	@Autowired
	private ISocketService socketService;
	
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
			currentHourPerHour = findOrCreateHourlyCycle();
		}
		
		beaconService.notifyState(true);
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
		LocalDate date = LocalDate.now();
		long hour = LocalTime.now().getHour();
		
		return hourPerHourRepository.findAll()
				.stream()
				.filter((hourPerHour) -> {
					if (Objects.equals(hourPerHour.getDate(), date)) {
						return hourPerHour.getHour() < hour;
					}
					
					return hourPerHour.getDate().isAfter(LocalDate.now().minusDays(1));
				})
				.map((hourPerHour) -> new SimpleHourPerHourDescriptor()
						.setDate(hourPerHour.getDate())
						.setHour(hourPerHour.getHour())
						.setOpen(hourPerHour.getOpen())
						.setStop(hourPerHour.getStop())
						.setObjective(hourPerHour.getObjective())
						.setProduced(hourPerHour.getProduced()))
				.sorted(Comparator.<SimpleHourPerHourDescriptor>reverseOrder())
				.limit(12)
				.collect(Collectors.toList());
	}
	
	@Override
	public void report() {
		throw new NotYetImplementedException();
	}
	
}