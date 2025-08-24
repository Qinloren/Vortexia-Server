package com.zeeyeh.vortexiaserver.data.codec.serializable;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateTimeSerializable extends JsonSerializer<LocalDateTime> {
    @Override
    public void serialize(LocalDateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (dateTime == null) {
            jsonGenerator.writeNull();
        } else {
            jsonGenerator.writeNumber(dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        }
    }
}
