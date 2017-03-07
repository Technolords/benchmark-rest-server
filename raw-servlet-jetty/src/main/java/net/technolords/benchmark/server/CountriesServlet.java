package net.technolords.benchmark.server;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.technolords.benchmark.resource.ResourceManager;

public class CountriesServlet extends HttpServlet {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static final String JSON_CONTENT_TYPE = "application/json";
    private static String BUFFERED_RESPONSE;

    @Override
    public void init() throws ServletException {
        LOGGER.info("Init: buffering response...");
        try {
            BUFFERED_RESPONSE = ResourceManager.getCountriesAsJsonString();
        } catch (IOException e) {
            throw new ServletException(e);
        }
        super.init();
    }

    @Override
    public void destroy() {
        LOGGER.info("Destroy: closing...");
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("About to generate a response...");
        response.setContentType(JSON_CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);
        this.populateResponse(response.getWriter());
    }

    private void populateResponse(Writer writer) throws IOException {
        writer.write(BUFFERED_RESPONSE);
        writer.flush();
    }
}
