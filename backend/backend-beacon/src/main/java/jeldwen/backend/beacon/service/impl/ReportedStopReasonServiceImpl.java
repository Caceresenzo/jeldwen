package jeldwen.backend.beacon.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jeldwen.backend.beacon.dto.ReportedStopReasonSearchBody;
import jeldwen.backend.beacon.dto.ReportedStopReasonSearchResult;
import jeldwen.backend.beacon.entity.Beacon;
import jeldwen.backend.beacon.entity.ProductFamily;
import jeldwen.backend.beacon.entity.ReportedStopReason;
import jeldwen.backend.beacon.entity.StopReason;
import jeldwen.backend.beacon.model.descriptor.SimpleBeaconDescriptor;
import jeldwen.backend.beacon.model.descriptor.SimpleProductFamilyDescriptor;
import jeldwen.backend.beacon.model.descriptor.SimpleReportedStopReasonDescriptor;
import jeldwen.backend.beacon.model.descriptor.SimpleStopReasonDescriptor;
import jeldwen.backend.beacon.repository.ReportedStopReasonRepository;
import jeldwen.backend.beacon.service.IBeaconService;
import jeldwen.backend.beacon.service.IProductFamilyService;
import jeldwen.backend.beacon.service.IReportedStopReasonService;
import jeldwen.backend.beacon.service.IStopReasonService;
import jeldwen.backend.common.util.MoreCollectors;
import jeldwen.backend.common.util.TypeAwareMapper;
import jeldwen.beacon.message.model.message.request.impl.report.StopReasonReportRequest;

@Service
public class ReportedStopReasonServiceImpl implements IReportedStopReasonService {
	
	@Autowired
	private ReportedStopReasonRepository repository;
	
	@Autowired
	private IStopReasonService stopReasonService;
	
	@Autowired
	private IProductFamilyService productFamilyService;
	
	@Autowired
	private IBeaconService beaconService;
	
	/* Mappers */
	private final TypeAwareMapper<ReportedStopReason, SimpleReportedStopReasonDescriptor> mapper;
	
	public ReportedStopReasonServiceImpl() {
		this.mapper = new TypeAwareMapper<ReportedStopReason, SimpleReportedStopReasonDescriptor>() {
		};
	}
	
	@Override
	public void store(Beacon beacon, StopReasonReportRequest request) {
		StopReason stopReason = null;
		if (request.getStopReasonId() != null) {
			stopReason = stopReasonService.find(request.getStopReasonId());
		}
		
		ProductFamily productFamily = productFamilyService.find(request.getProductFamilyId());
		
		repository.save(new ReportedStopReason()
				.setBeacon(beacon)
				.setStopReason(stopReason)
				.setMessage(request.getMessage())
				.setProductFamily(productFamily)
				.setDuration(request.getDuration())
				.setAt(request.getAt()));
	}
	
	@Override
	public ReportedStopReasonSearchResult search(Long beaconId, ReportedStopReasonSearchBody body) {
		Beacon beacon = null;
		if (beaconId != null) {
			beacon = beaconService.find(beaconId);
		}
		
		List<ReportedStopReason> reportedStopReasons = repository.search(beacon, body.getStartDate(), body.getEndDate());
		
		return new ReportedStopReasonSearchResult()
				.setBeacons(reportedStopReasons.stream()
						.map(ReportedStopReason::getBeacon)
						.filter(MoreCollectors.distinctByKey(Beacon::getId))
						.map(beaconService::toDto)
						.collect(Collectors.toMap(SimpleBeaconDescriptor::getId, Function.identity())))
				.setProductFamilies(reportedStopReasons.stream()
						.map(ReportedStopReason::getProductFamily)
						.filter(Objects::nonNull)
						.filter(MoreCollectors.distinctByKey(ProductFamily::getId))
						.map(productFamilyService::toDto)
						.collect(Collectors.toMap(SimpleProductFamilyDescriptor::getId, Function.identity())))
				.setStopReasons(reportedStopReasons.stream()
						.map(ReportedStopReason::getStopReason)
						.filter(Objects::nonNull)
						.filter(MoreCollectors.distinctByKey(StopReason::getId))
						.map(stopReasonService::toDto)
						.collect(Collectors.toMap(SimpleStopReasonDescriptor::getId, Function.identity())))
				.setReportedStopReasons(mapper.toDtos(reportedStopReasons));
	}
	
}