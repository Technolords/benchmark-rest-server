package net.technolords.benchmark.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.technolords.benchmark.config.ConfigurationManager;

public class ServletMain {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static final String PATH = "/countries";
    private static final String ROOT = "/";

    public void configureAndRun() throws Exception {
        LOGGER.info("Starting server...");
        Server server = new Server(this.createThreadPool());
        server.addConnector(this.createServerConnector(server));
        server.setStopAtShutdown(true);
        this.initContextHandler(server);
        server.start();
        server.join();
    }

    private QueuedThreadPool createThreadPool() {
        QueuedThreadPool queuedThreadPool = new QueuedThreadPool();
        queuedThreadPool.setMaxThreads(ConfigurationManager.getPoolSize());
        return queuedThreadPool;
    }

    private void initContextHandler(Server server) {
        ServletContextHandler context = new ServletContextHandler(server, ROOT);
        ServletHolder restEasyServlet = new ServletHolder(new HttpServletDispatcher());
        restEasyServlet.setInitParameter("javax.ws.rs.Application", "net.technolords.benchmark.server.EmbeddedApplication");
        context.addServlet(restEasyServlet, PATH);
    }

    private ServerConnector createServerConnector(Server server) {
        ServerConnector serverConnector = new ServerConnector(server);
        serverConnector.setHost("0.0.0.0");
        serverConnector.setPort(ConfigurationManager.getPort());
        serverConnector.setIdleTimeout(30000L);
        return serverConnector;
    }

    public static void main(String[] args) throws Exception {
        ServletMain servletManager = new ServletMain();
        servletManager.configureAndRun();
    }
}
