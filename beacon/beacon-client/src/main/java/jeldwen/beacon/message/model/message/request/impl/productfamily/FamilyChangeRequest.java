package jeldwen.beacon.message.model.message.request.impl.productfamily;

import jeldwen.beacon.message.model.message.request.BaseRequestMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FamilyChangeRequest extends BaseRequestMessage {
	
	/* Constants */
	public static final String NAME = "family-change";
	
	/* Variables */
	private long familyId;
	
	/* Constructor */
	public FamilyChangeRequest() {
		setName(NAME);
	}
	
}