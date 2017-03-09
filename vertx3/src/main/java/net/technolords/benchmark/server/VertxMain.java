package net.technolords.benchmark.server;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import net.technolords.benchmark.resource.ResourceManager;

public class VertxMain {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static String BUFFERED_RESPONSE;

    public void configureAndRun() {
        LOGGER.info("Starting server...");
        try {
            BUFFERED_RESPONSE = ResourceManager.getCountriesAsJsonString();
        } catch (IOException e) {
            LOGGER.error("Failed to buffer response", e);
        }
        Vertx
                .vertx(this.createVertxOptions())
                .createHttpServer()
                .requestHandler(httpServerRequest -> httpServerRequest.response()
                        .setChunked(true)
                        .putHeader("Content-Type", "application/json")
                        .write(BUFFERED_RESPONSE)
                        .end())
                .listen(9090);
    }

    public VertxOptions createVertxOptions() {
        VertxOptions vertxOptions = new VertxOptions();
        vertxOptions.setWorkerPoolSize(512);
        return vertxOptions;
    }

    public static void main(String[] args) {
        VertxMain vertxMain = new VertxMain();
        vertxMain.configureAndRun();
    }

    // http://vertx.io/docs/vertx-core/java/
    // http://vertx.io/docs/vertx-rx/java/
}
