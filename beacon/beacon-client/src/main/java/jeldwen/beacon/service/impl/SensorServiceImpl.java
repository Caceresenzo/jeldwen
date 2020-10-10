package jeldwen.beacon.service.impl;

import javax.annotation.PostConstruct;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.wiringpi.GpioInterrupt;
import com.pi4j.wiringpi.GpioInterruptEvent;
import com.pi4j.wiringpi.GpioInterruptListener;

import jeldwen.beacon.service.ICycleService;
import jeldwen.beacon.service.ISensorService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SensorServiceImpl implements ISensorService, DisposableBean, GpioInterruptListener {
	
	@Autowired
	private ICycleService cycleService;
	
	/* Variables */
	private GpioController gpio;
	
	@PostConstruct
	private void initialize() {
		try {
			gpio = GpioFactory.getInstance();
		} catch (/* UnsatisfiedLinkError */ Throwable exception) {
			log.error("Failed to setup GPIO wirings.");
		}

		GpioInterrupt.addListener(this);
	}
	
	@Override
	public void destroy() throws Exception {
		if (gpio != null) {
			gpio.shutdown();
		}

		GpioInterrupt.removeListener(this);
	}
	
	@Override
	public void trigger() {
		cycleService.signal();
	}

	@Override
	public void pinStateChange(GpioInterruptEvent event) {
		throw new NotYetImplementedException();
	}
	
}