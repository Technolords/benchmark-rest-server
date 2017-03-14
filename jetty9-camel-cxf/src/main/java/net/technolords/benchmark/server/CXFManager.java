package net.technolords.benchmark.server;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

import net.technolords.benchmark.config.ConfigurationManager;
import net.technolords.benchmark.server.api.CountriesResource;

public class CXFManager {

    public static JAXRSServerFactoryBean createFactory() {
        JAXRSServerFactoryBean jaxrsServerFactoryBean = new JAXRSServerFactoryBean();
        jaxrsServerFactoryBean.setResourceClasses(CountriesResource.class);
        jaxrsServerFactoryBean.setAddress(generateAddress());
        return jaxrsServerFactoryBean;
    }

    private static String generateAddress() {
        StringBuilder buffer = new StringBuilder();
        int port = ConfigurationManager.getPort();
        buffer.append("http://0.0.0.0:").append(port).append("/");
        return buffer.toString();
    }
}
