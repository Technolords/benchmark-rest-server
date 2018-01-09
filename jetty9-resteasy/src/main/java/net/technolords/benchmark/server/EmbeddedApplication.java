package net.technolords.benchmark.server;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.technolords.benchmark.server.api.CountriesResource;

public class EmbeddedApplication extends Application {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    public Set<Object> getSingletons() {
        HashSet<Object> objects = new HashSet<>();
        try {
            objects.add(new CountriesResource());
        } catch (IOException e) {
            LOGGER.error("Failed to create countries resource", e);
        }
        return objects;
    }
}
