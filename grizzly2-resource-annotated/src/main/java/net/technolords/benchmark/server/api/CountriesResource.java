package net.technolords.benchmark.server.api;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.technolords.benchmark.resource.ResourceManager;

@Path(CountriesResource.PATH)
public class CountriesResource {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    public static final String PATH = "/countries";
    private static String BUFFERED_RESPONSE;

    public CountriesResource() throws IOException {
        BUFFERED_RESPONSE = ResourceManager.getCountriesAsJsonString();
    }

    @GET
    @Produces (MediaType.APPLICATION_JSON)
    public String getCountries() {
        LOGGER.info("getCountries called...");
        return BUFFERED_RESPONSE;
    }
}
