package net.technolords.benchmark.server;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.technolords.benchmark.server.api.CountriesResource;

public class DropwizardMain extends Application<ZeroConfig> {

    public void configureAndRun() throws Exception {
        String[] args = new String[] { "server", "src/main/resources/config.yml" };
        run(args);
    }

    @Override
    public String getName() {
        return "jersey-dropwizard";
    }

    @Override
    public void initialize(Bootstrap<ZeroConfig> bootstrap) {
    }

    @Override
    public void run(ZeroConfig zeroConfig, Environment environment) throws Exception {
        final CountriesResource countriesResource = new CountriesResource();
        environment.jersey().register(countriesResource);
    }

    public static void main(String[] args) throws Exception {
        DropwizardMain dropwizardMain = new DropwizardMain();
        dropwizardMain.configureAndRun();
    }

}
