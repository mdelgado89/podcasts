package com.starstudios.podcastaudio.jsonparsers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by delgadem on 11/14/14.
 */
public enum MyJsonParser {
    INSTANCE;

    private ObjectMapper mObjectMapper;

    private MyJsonParser() {
        mObjectMapper = new ObjectMapper();
    }

    public ObjectMapper getObjectMapper() {
        return mObjectMapper;
    }

    public Object parseJson(String json, Class clazz) throws IOException {
        return mObjectMapper.readValue(json, clazz);
    }
}
