package jeldwen.beacon.message.model.message.response.impl.productfamily;

import jeldwen.beacon.message.model.config.ProductFamilyConfig;
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
	
	public static final FamilyChangedResponse of(ProductFamilyConfig productFamily) {
		return new FamilyChangedResponse().setActiveFamilyId(productFamily.getId());
	}
	
	public static final FamilyChangedResponse nulled() {
		return new FamilyChangedResponse().setActiveFamilyId(null);
	}
	
}