package net.technolords.benchmark.server;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.undertow.Undertow;
import io.undertow.util.Headers;
import net.technolords.benchmark.resource.ResourceManager;

public class UndertowMain {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static final String JSON_CONTENT_TYPE = "application/json";
    private static String BUFFERED_RESPONSE;

    public void configureAndRun() {
        LOGGER.info("Creating server...");
        try {
            BUFFERED_RESPONSE = ResourceManager.getCountriesAsJsonString();
        } catch (IOException e) {
            LOGGER.error("Unable to create server", e);
        }
        Undertow server = Undertow
                .builder()
                .addHttpListener(9090, "localhost")
                .setHandler(httpServerExchange -> {
                    httpServerExchange.getResponseHeaders().put(Headers.CONTENT_TYPE, JSON_CONTENT_TYPE);
                    httpServerExchange.getResponseSender().send(BUFFERED_RESPONSE);
                })
                .build();
        server.start();
    }

    public static void main(String[] args) throws Exception {
        UndertowMain undertowMain = new UndertowMain();
        undertowMain.configureAndRun();
    }
}
