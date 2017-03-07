package net.technolords.benchmark.server;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.JettyWebXmlConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.technolords.benchmark.config.ConfigurationManager;

public class ServletMain {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static final String CONTEXT_PATH = "/";

    public void configureAndRun() throws Exception {
        LOGGER.info("Starting server...");
        Server server = new Server(this.createThreadPool());
        server.addConnector(this.createServerConnector(server));
        server.setStopAtShutdown(true);
        server.setHandler(this.createWebAppContext());
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

    private WebAppContext createWebAppContext() {
        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", ".*");
        webAppContext.setResourceBase(".");
        webAppContext.setContextPath(CONTEXT_PATH);
        webAppContext.setConfigurations(new Configuration[] {
                new AnnotationConfiguration(),
                new WebInfConfiguration(),
                new WebXmlConfiguration(),
                new MetaInfConfiguration(),
                new FragmentConfiguration(),
                new EnvConfiguration(),
                new PlusConfiguration(),
                new JettyWebXmlConfiguration()
        });
        webAppContext.setConfigurationDiscovered(true);
        return webAppContext;
    }

    public static void main(String[] args) throws Exception {
        ServletMain servletManager = new ServletMain();
        servletManager.configureAndRun();
    }
}
