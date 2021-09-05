package com.knoldus.rishi.deserializerpkg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knoldus.rishi.inputmodel.InputUser;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class DeserializerUser implements Deserializer<InputUser> {
    @Override public void close() {
    }
    @Override public void configure(Map<String, ?> arg0, boolean arg1) {
    }
    @Override
    public InputUser deserialize(String arg0, byte[] arg1) {
        ObjectMapper mapper = new ObjectMapper();
        InputUser inputUser = null;
        try {
            inputUser = mapper.readValue(arg1, InputUser.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputUser;
    }
}