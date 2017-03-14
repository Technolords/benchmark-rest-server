package net.technolords.benchmark.server;

import org.apache.camel.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CamelMain extends Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(CamelMain.class);

    @Override
    public void beforeStart() {
        LOGGER.info("Before start called...");
        super.addRouteBuilder(new JettyRoute());
    }

    @Override
    public void afterStart() {
        LOGGER.info("After start called...");
    }

    public void startService() throws Exception {
        super.run();
    }

    public static void main(String[] args) throws Exception {
        CamelMain camelMain = new CamelMain();
        camelMain.startService();
    }
}
