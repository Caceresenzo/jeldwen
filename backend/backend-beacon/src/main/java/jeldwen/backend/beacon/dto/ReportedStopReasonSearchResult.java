package jeldwen.backend.beacon.dto;

import java.util.List;
import java.util.Map;

import jeldwen.backend.beacon.model.descriptor.SimpleBeaconDescriptor;
import jeldwen.backend.beacon.model.descriptor.SimpleProductFamilyDescriptor;
import jeldwen.backend.beacon.model.descriptor.SimpleReportedStopReasonDescriptor;
import jeldwen.backend.beacon.model.descriptor.SimpleStopReasonDescriptor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ReportedStopReasonSearchResult {

	private Map<Long, SimpleBeaconDescriptor> beacons;
	private Map<Long, SimpleProductFamilyDescriptor> productFamilies;
	private Map<Long, SimpleStopReasonDescriptor> stopReasons;
	private List<SimpleReportedStopReasonDescriptor> reportedStopReasons;
	
}