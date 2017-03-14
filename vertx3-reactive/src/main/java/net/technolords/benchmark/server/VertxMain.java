package net.technolords.benchmark.server;

import java.io.IOException;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.VertxOptions;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.http.HttpServer;
import io.vertx.rxjava.core.http.HttpServerResponse;
import net.technolords.benchmark.config.ConfigurationManager;
import net.technolords.benchmark.resource.ResourceManager;

// http://vertx.io/docs/vertx-rx/java/
public class VertxMain extends AbstractVerticle {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static String BUFFERED_RESPONSE;

    public void configureAndRun() {
        LOGGER.info("Starting server...");
        Consumer<Vertx> runner = vertx1 -> {
            vertx1.deployVerticle(this.getClass().getName());
        };
        Vertx vertx = Vertx.vertx(this.createVertxOptions());
        runner.accept(vertx);
    }

    @Override
    public void start() throws Exception {
        LOGGER.info("Verticle started....");
        try {
            BUFFERED_RESPONSE = ResourceManager.getCountriesAsJsonString();
        } catch (IOException e) {
            LOGGER.error("Failed to buffer response", e);
        }
        int port = ConfigurationManager.getPort();
        HttpServer httpServer = vertx.createHttpServer();
        httpServer
                .requestStream()
                .toObservable()
                .subscribe(httpServerRequest -> {
                    HttpServerResponse response = httpServerRequest.response();
                    response
                            .setChunked(true)
                            .putHeader("Content-Type", "application/json")
                            .end(BUFFERED_RESPONSE);
                });
        httpServer.listen(port);
    }

    private VertxOptions createVertxOptions() {
        VertxOptions vertxOptions = new VertxOptions();
        int poolSize = ConfigurationManager.getPoolSize();
        vertxOptions.setWorkerPoolSize(poolSize);
        return vertxOptions;
    }

    public static void main(String[] args) throws Exception {
        VertxMain vertxMain = new VertxMain();
        vertxMain.configureAndRun();
    }

}
