package net.technolords.benchmark.server;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JerseyMain {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public void configureAndRun() throws IOException, InterruptedException {
        LOGGER.info("Starting server...");
        HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(this.createBaseURI(), this.createResourceConfig());
        httpServer.start();
        this.registerShutdownHook(httpServer);
        Thread.currentThread().join(); // Prevents termination
    }

    private URI createBaseURI() {
        return UriBuilder.fromUri("http://localhost/").port(9090).build();
    }

    private ResourceConfig createResourceConfig() {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages("net.technolords.benchmark.server.api");
        return resourceConfig;
    }

    private void registerShutdownHook(HttpServer httpServer) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("Terminating server...");
            httpServer.shutdown();
        }));
    }

    public static void main(String[] args) throws Exception {
        JerseyMain jerseyMain = new JerseyMain();
        jerseyMain.configureAndRun();
    }
}
