package net.technolords.benchmark.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.technolords.benchmark.config.ConfigurationManager;

public class ServletMain {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static final String PATH = "/countries";

    public void configureAndRun() throws Exception {
        LOGGER.info("Starting server...");
        Server server = new Server(this.createThreadPool());
        server.addConnector(this.createServerConnector(server));
        server.setStopAtShutdown(true);
        server.setHandler(this.createServletHandler());
        server.start();
        server.join();
    }

    private QueuedThreadPool createThreadPool() {
        QueuedThreadPool queuedThreadPool = new QueuedThreadPool();
        queuedThreadPool.setMaxThreads(ConfigurationManager.getPoolSize());
        return queuedThreadPool;
    }

    private ServerConnector createServerConnector(Server server) {
        ServerConnector serverConnector = new ServerConnector(server);
        serverConnector.setHost("localhost");
        serverConnector.setPort(ConfigurationManager.getPort());
        serverConnector.setIdleTimeout(30000L);
        return serverConnector;
    }

    private ServletHandler createServletHandler() {
        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(CountriesServlet.class, PATH);
        return servletHandler;
    }

    public static void main(String[] args) throws Exception {
        ServletMain servletManager = new ServletMain();
        servletManager.configureAndRun();
    }
}
