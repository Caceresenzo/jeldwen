package jeldwen.backend.glial.persistance.duration;

import java.io.IOException;
import java.time.Duration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class DurationSerializer extends StdSerializer<Duration> {
	
	public DurationSerializer() {
		this(null);
	}
	
	public DurationSerializer(Class<Duration> t) {
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