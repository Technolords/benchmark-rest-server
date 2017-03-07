package net.technolords.benchmark.resource;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class ResourceManagerTest {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Test
    public void testGetCountriesAsJsonString() throws IOException {
        LOGGER.info("About to test...");
        String json = ResourceManager.getCountriesAsJsonString();
    }
}