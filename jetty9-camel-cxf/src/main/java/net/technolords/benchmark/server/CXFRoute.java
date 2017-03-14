package net.technolords.benchmark.server;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.ws.rs.core.MediaType;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.technolords.benchmark.resource.ResourceManager;

public class CXFRoute extends RouteBuilder {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static final String ROUTE_ID = "routeCXF";
    private static final String CXF_MAIN = "cxfrs:bean:";
    private static final String QUESTION_SIGN = "?";
    private static final String EQUAL_SIGN = "=";
    private static final String CONTENT_TYPE = "Content-Type";
    private static String BUFFERED_RESPONSE;

    public CXFRoute() {
        try {
            BUFFERED_RESPONSE = ResourceManager.getCountriesAsJsonString();
        } catch (IOException e) {
            LOGGER.error("Failed to buffer", e);
        }
    }

    @Override
    public void configure() throws Exception {
        from(this.generateCXFEndpoint())
                .routeId(ROUTE_ID)
                .id(ROUTE_ID)
                .process(exchange -> {
                    exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpURLConnection.HTTP_OK);
                    exchange.getIn().setHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON);
                    exchange.getIn().setBody(BUFFERED_RESPONSE);
                });
    }

    protected String generateCXFEndpoint() {
        StringBuilder buffer = new StringBuilder();
        // cxfrs:bean:restServer?bindingStyle=SimpleConsumer
        buffer.append(CXF_MAIN).append("restServer");
        buffer.append(QUESTION_SIGN).append("bindingStyle").append(EQUAL_SIGN).append("SimpleConsumer");
        return buffer.toString();
    }
}
