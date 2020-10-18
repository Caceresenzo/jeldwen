package jeldwen.backend.core.handler.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jeldwen.backend.common.model.api.impl.ApiError;
import jeldwen.backend.common.util.Is;
import jeldwen.backend.common.util.MoreCollectors;
import lombok.extern.slf4j.Slf4j;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private RequestMappingHandlerMapping handlerMapping;
	
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
		return buildResponseEntity(new ApiError(BAD_REQUEST, exception.getParameterName() + " parameter is missing", exception));
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
		return buildResponseEntity(new ApiError(METHOD_NOT_ALLOWED, exception.getSupportedHttpMethods()
				.stream()
				.map(Object::toString)
				.collect(Collectors.joining(", ", String.format("unsupported '%s' request method; supported: ", exception.getMethod()), "")), exception));
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
		return buildResponseEntity(new ApiError(NOT_ACCEPTABLE, exception.getSupportedMediaTypes()
				.stream()
				.map(Object::toString)
				.collect(Collectors.joining(", ", String.format("unacceptable '%s' media type; supported: ", exception.getMessage()), "")), exception));
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
		return buildResponseEntity(new ApiError(UNSUPPORTED_MEDIA_TYPE, exception.getSupportedMediaTypes()
				.stream()
				.map(Object::toString)
				.collect(Collectors.joining(", ", String.format("unsupported '%s' media type; supported: ", exception.getContentType()), "")), exception));
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
		return buildResponseEntity(new ApiError(UNPROCESSABLE_ENTITY)
				.setMessage("validation error")
				.addValidationErrors(exception.getBindingResult().getFieldErrors())
				.addValidationError(exception.getBindingResult().getGlobalErrors()));
	}
	
	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolation(javax.validation.ConstraintViolationException exception) {
		return buildResponseEntity(new ApiError(UNPROCESSABLE_ENTITY)
				.setMessage("constraint violation")
				.addValidationErrors(exception.getConstraintViolations()));
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException exception) {
		return buildResponseEntity(new ApiError(NOT_FOUND)
				.setMessage(exception.getMessage()));
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ServletWebRequest servletWebRequest = (ServletWebRequest) request;
		log.info("{} to {}", servletWebRequest.getHttpMethod(), servletWebRequest.getRequest().getServletPath());
		
		return buildResponseEntity(new ApiError(BAD_REQUEST, "malformed JSON request", exception));
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
		return buildResponseEntity(new ApiError(INTERNAL_SERVER_ERROR, "error writing JSON output", exception));
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError apiError = new ApiError(BAD_REQUEST);
		
		apiError.setMessage(String.format("no '%s' method for URL: %s", exception.getHttpMethod(), exception.getRequestURL()));
		apiError.setDebugMessage(exception.getMessage());
		apiError.setMetadata(Collections.singletonMap("endpoints", handlerMapping.getHandlerMethods()
				.keySet()
				.stream()
				.filter((info) -> !info.getPatternsCondition().getPatterns().iterator().next().startsWith("/api/external"))
				// .sorted()
				.collect(Collectors.toMap((info) -> info.getPatternsCondition().getPatterns().iterator().next(),
						(info) -> info.getMethodsCondition().getMethods(),
						(a, b) -> {
							Set<RequestMethod> set = new HashSet<>();
							set.addAll(a);
							set.addAll(b);
							return set;
						}))));
		
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception, WebRequest request) {
		String why = null;
		Class<?> type = exception.getRequiredType();
		
		if (Is.aBoolean(type)) {
			why = "must be a boolean";
		} else if (Is.aByte(type)) {
			why = "must be a number (byte)";
		} else if (Is.aShort(type)) {
			why = "must be a number (short)";
		} else if (Is.anInteger(type)) {
			why = "must be a number (integer)";
		} else if (Is.aLong(type)) {
			why = "must be a number (long)";
		} else if (Is.aFloat(type)) {
			why = "must be a decimal (float)";
		} else if (Is.aDouble(type)) {
			why = "must be a decimal (double)";
		} else if (type.isEnum()) {
			why = "must be " + Arrays.asList(type.getEnumConstants())
					.stream()
					.map((constant) -> ((Enum<?>) constant).name())
					.collect(Collectors.collectingAndThen(Collectors.toList(), MoreCollectors.joiningLastDelimiter(", ", " or ")));
		} else if (LocalDate.class.equals(type)) {
			why = "must be a date (yyyy-mm-dd)";
		} else if (LocalTime.class.equals(type)) {
			why = "must be a time (hh:mm:ss)";
		} else if (LocalDateTime.class.equals(type)) {
			why = "must be a date time (yyyy-mm-dd hh:mm:ss)";
		} else {
			why = "must be a " + type.getSimpleName();
		}
		
		return buildResponseEntity(new ApiError(BAD_REQUEST)
				.setMessage(String.format("'%s' %s", exception.getName(), why))
				.setDebugMessage(exception.getMessage()));
	}
	
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getHttpStatus());
	}
	
}