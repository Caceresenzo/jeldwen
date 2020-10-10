package jeldwen.beacon.message.model.message.response.impl.productfamily;

import jeldwen.beacon.message.model.message.response.BaseResponseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FamilyChangedResponse extends BaseResponseMessage {
	
	/* Constants */
	public static final String NAME = "family-changed";
	
	/* Variables */
	private Long activeFamilyId;
	
	/* Constructor */
	public FamilyChangedResponse() {
		setName(NAME);
	}
	
	public static final FamilyChangedResponse zero() {
		return new FamilyChangedResponse().setActiveFamilyId(0l);
	}
	
}