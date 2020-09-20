package jeldwen.backend.beacon.service;

import java.util.List;

import jeldwen.backend.beacon.dto.DetectorUpdateBody;
import jeldwen.backend.beacon.entity.Detector;
import jeldwen.beacon.message.model.IBeaconMessage;
import jeldwen.beacon.message.model.request.impl.auth.AuthRequestMessage;

public interface IBeaconClientService {
	
	List<Detector> listAll();
	
	Detector find(long id);

	Detector update(long id, DetectorUpdateBody body);
	
	IBeaconMessage parseMessage(String json) throws Exception;
	
	String stringifyMessage(IBeaconMessage message) throws Exception;
	
	Detector createIfNotExistsClient(AuthRequestMessage message);
	
}