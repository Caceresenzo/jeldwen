package jeldwen.backend.common.http;

import org.springframework.http.MediaType;

public class MediaTypes {

	public static final String TEXT_CSV_VALUE = "text/csv";
	public static final MediaType TEXT_CSV = MediaType.valueOf(TEXT_CSV_VALUE);
	
	public static final String TEXT_TEXT_VALUE = "text/text";
	public static final MediaType TEXT_TEXT = MediaType.valueOf(TEXT_TEXT_VALUE);
	
}