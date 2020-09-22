package jeldwen.beacon.message.model.message.response.impl.list;

import java.util.List;

import jeldwen.beacon.message.model.message.response.BaseResponseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ConnectedBeaconListResponseMessage extends BaseResponseMessage {
	
	/* Constants */
	public static final String NAME = "connected-beacon-list";
	
	/* Variables */
	private List<String> uniques;
	
	/* Constructor */
	public ConnectedBeaconListResponseMessage() {
		setName(NAME);
	}
	
}