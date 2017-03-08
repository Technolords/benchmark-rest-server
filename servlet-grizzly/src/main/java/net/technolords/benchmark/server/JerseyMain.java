package net.technolords.benchmark.server;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;

public class JerseyMain {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public void configureAndRun() throws IOException {
        LOGGER.info("Starting server...");
        String uri = "http://localhost:9090/countries";
        SelectorThread selectorThread = GrizzlyWebContainerFactory.create(uri, CountriesServlet.class);
        selectorThread.run();
    }

    public static void main(String[] args) throws Exception {
        JerseyMain jerseyMain = new JerseyMain();
        jerseyMain.configureAndRun();
    }
}
