package jeldwen.backend.common.model.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonPropertyOrder({ "status", "metadata" })
public abstract class AbstractApiResponse {

	/* Variables */
	@JsonIgnore
	private HttpStatus httpStatus;
	
	@JsonInclude(Include.NON_NULL)
	private Object metadata;

	/* Constructor */
	public AbstractApiResponse(HttpStatus status) {
		this.httpStatus = status;
	}

	/* Constructor */
	public AbstractApiResponse(HttpStatus status, Object metadata) {
		this(status);
		
		this.metadata = metadata;
	}
	
	public String getStatus() {
		return httpStatus.getReasonPhrase();
	}
	
	public ResponseEntity<? extends AbstractApiResponse> toResponseEntity() {
		return new ResponseEntity<>(this, httpStatus);
	}
	
}