package net.technolords.benchmark.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationManager.class);
    private static final int DEFAULT_PORT = 9090;
    private static final int DEFAULT_POOL_SIZE = 512;
    private static final String PROPERTY_PORT = "port";
    private static final String PROPERTY_POOL_SIZE = "poolSize";

    public static int getPort() {
        String rawPort = System.getProperty(PROPERTY_PORT);
        int port = (rawPort == null ? DEFAULT_PORT : parseIntValue(rawPort, DEFAULT_PORT));
        LOGGER.info("Using port: {}", port);
        return port;
    }

    public static int getPoolSize() {
        String rawPoolSize = System.getProperty(PROPERTY_POOL_SIZE);
        int poolSize = (rawPoolSize == null ? DEFAULT_POOL_SIZE : parseIntValue(rawPoolSize, DEFAULT_POOL_SIZE));
        LOGGER.info("Using pool size: {}", poolSize);
        return poolSize;
    }

    private static int parseIntValue(String rawValue, int defaultValue) {
        try {
            return Integer.parseInt(rawValue);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
