package net.technolords.benchmark.server;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.technolords.benchmark.config.ConfigurationManager;
import net.technolords.benchmark.resource.ResourceManager;

public class JettyRoute extends RouteBuilder {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static final String ROUTE_ID = "routeJetty";
    private static final String CONTENT_TYPE = "Content-Type";
    private static String BUFFERED_RESPONSE;

    public JettyRoute() {
        try {
            BUFFERED_RESPONSE = ResourceManager.getCountriesAsJsonString();
        } catch (IOException e) {
            LOGGER.error("Failed to buffer", e);
        }
    }

    @Override
    public void configure() throws Exception {
        from(this.generateJettyEndpoint())
                .routeId(ROUTE_ID)
                .id(ROUTE_ID)
                .process(exchange -> {
                    exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpURLConnection.HTTP_OK);
                    exchange.getIn().setHeader(CONTENT_TYPE, "application/json");
                    exchange.getIn().setBody(BUFFERED_RESPONSE);
                });
    }

    protected String generateJettyEndpoint() {
        StringBuilder buffer = new StringBuilder();
        // jetty:http://0.0.0.0:9090/?matchOnUriPrefix=true
        int port = ConfigurationManager.getPort();
        buffer.append("jetty:http://0.0.0.0:").append(port).append("/");
        buffer.append("?matchOnUriPrefix=true");
        return buffer.toString();
    }
}
