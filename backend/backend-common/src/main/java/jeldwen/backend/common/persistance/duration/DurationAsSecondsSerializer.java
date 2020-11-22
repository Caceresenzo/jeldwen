package jeldwen.backend.common.persistance.duration;

import java.io.IOException;
import java.time.Duration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class DurationAsSecondsSerializer extends StdSerializer<Duration> {
	
	public DurationAsSecondsSerializer() {
		this(null);
	}
	
	public DurationAsSecondsSerializer(Class<Duration> t) {
		super(t);
	}
	
	@Override
	public void serialize(Duration value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		if (value == null) {
			gen.writeNull();
		} else {
			gen.writeNumber(value.getSeconds());
		}
	}
	
}