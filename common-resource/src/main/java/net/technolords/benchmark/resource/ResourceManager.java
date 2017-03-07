package net.technolords.benchmark.resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceManager.class);
    private static final String JSON_COUNTRIES = "json/countries.json";
    private static final int BUFFER_SIZE = 1024;

    public static String getCountriesAsJsonString() throws IOException {
        InputStream inputStream = ResourceManager.class.getClassLoader().getResourceAsStream(JSON_COUNTRIES);
        if (inputStream != null) {
            LOGGER.info("Bytes available: {}", inputStream.available());
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString(StandardCharsets.UTF_8.displayName());
        }
        return null;
    }
}
