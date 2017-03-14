package net.technolords.benchmark.server;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;

import net.technolords.benchmark.config.ConfigurationManager;

public class JerseyMain {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public void configureAndRun() throws IOException {
        LOGGER.info("Starting server...");
        int port = ConfigurationManager.getPort();
        String uri = "http://localhost:" + port + "/countries";
        SelectorThread selectorThread = GrizzlyWebContainerFactory.create(uri, CountriesServlet.class);
        selectorThread.setMaxThreads(ConfigurationManager.getPoolSize());
        selectorThread.run();
    }

    public static void main(String[] args) throws Exception {
        JerseyMain jerseyMain = new JerseyMain();
        jerseyMain.configureAndRun();
    }
}
