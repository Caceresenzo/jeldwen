package jeldwen.backend.common.model.api.impl;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import jeldwen.backend.common.model.api.AbstractApiResponse;
import jeldwen.backend.common.model.api.metadata.PageMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ApiAnwser<T> extends AbstractApiResponse {

	/* Variables */
	private T payload;
	
	/* Constructor */
	public ApiAnwser() {
		this((T) null);
	}
	
	/* Constructor */
	public ApiAnwser(T payload) {
		this(HttpStatus.OK, payload);
	}

	/* Constructor */
	public ApiAnwser(HttpStatus status) {
		this(status, null);
	}

	/* Constructor */
	public ApiAnwser(HttpStatus status, T payload) {
		super(status);
		
		this.payload = payload;
	}
	
	public ApiAnwser<T> setPageMetadata(Page<?> page) {
		setMetadata(new PageMetadata(page));
		
		return this;
	}
	
}