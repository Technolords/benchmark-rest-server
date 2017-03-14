package net.technolords.benchmark.server;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import net.technolords.benchmark.config.ConfigurationManager;
import net.technolords.benchmark.resource.ResourceManager;

// See: http://vertx.io/docs/vertx-web/java/
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
        Vertx vertx = Vertx
                .vertx(this.createVertxOptions());
        HttpServer httpServer = vertx
                .createHttpServer();
        Router router = Router.router(vertx);
        router.route().handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response.setChunked(true)
                    .putHeader("Content-Type", "application/json")
                    .write(BUFFERED_RESPONSE)
                    .end();
        });
        int port = ConfigurationManager.getPort();
        httpServer
                .requestHandler(router::accept)
                .listen(port);
    }

    private VertxOptions createVertxOptions() {
        VertxOptions vertxOptions = new VertxOptions();
        int poolSize = ConfigurationManager.getPoolSize();
        vertxOptions.setWorkerPoolSize(poolSize);
        return vertxOptions;
    }

    public static void main(String[] args) {
        VertxMain vertxMain = new VertxMain();
        vertxMain.configureAndRun();
    }
}
