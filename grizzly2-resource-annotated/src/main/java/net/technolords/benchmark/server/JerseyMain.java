package net.technolords.benchmark.server;

import java.io.IOException;

import org.glassfish.grizzly.http.server.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.ClasspathResourceConfig;

public class JerseyMain {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public void configureAndRun() throws IOException, InterruptedException {
        LOGGER.info("Starting server...");
        String uri = "http://localhost:9090/";
        ClasspathResourceConfig classpathResourceConfig = new ClasspathResourceConfig();
        HttpServer httpServer = GrizzlyServerFactory.createHttpServer(uri, classpathResourceConfig);
        httpServer.start();
        this.registerShutdownHook(httpServer);
        Thread.currentThread().join(); // Prevents termination
    }

    public void registerShutdownHook(HttpServer httpServer) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("Terminating server...");
            httpServer.stop();
        }));
    }

    public static void main(String[] args) throws Exception {
        JerseyMain jerseyMain = new JerseyMain();
        jerseyMain.configureAndRun();
    }
}