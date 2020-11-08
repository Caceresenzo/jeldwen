package jeldwen.backend.glial.http;

import org.springframework.http.MediaType;

public class MediaTypes {

	public static final String APPLICATION_CSV_VALUE = "application/csv";
	public static final MediaType APPLICATION_CSV = MediaType.valueOf(APPLICATION_CSV_VALUE);
	
	public static final String APPLICATION_TSV_VALUE = "application/tsv";
	public static final MediaType APPLICATION_TSV = MediaType.valueOf(APPLICATION_TSV_VALUE);
	
	public static final String TEXT_TEXT_VALUE = "text/text";
	public static final MediaType TEXT_TEXT = MediaType.valueOf(TEXT_TEXT_VALUE);
	
}