package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class ConfigGenerate {
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
    }

    public static Config getConfig() {
        try {
            return mapper.readValue(new File("src/main/resources/config.yaml"), Config.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}