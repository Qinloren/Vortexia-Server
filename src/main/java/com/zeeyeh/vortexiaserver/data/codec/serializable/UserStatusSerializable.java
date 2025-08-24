package com.zeeyeh.vortexiaserver.data.codec.serializable;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.zeeyeh.vortexiaserver.data.entity.UserStatus;

import java.io.IOException;

public class UserStatusSerializable extends JsonSerializer<UserStatus> {
    @Override
    public void serialize(UserStatus status, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (status == null) {
            jsonGenerator.writeNull();
        } else {
            jsonGenerator.writeNumber(status.getCode());
        }
    }
}
