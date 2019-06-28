package ru.khmelev.tm.config;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.khmelev.tm.endpoint.AuthenticationEndpoint;
import ru.khmelev.tm.endpoint.ProjectEndpoint;
import ru.khmelev.tm.endpoint.TaskEndpoint;
import ru.khmelev.tm.endpoint.UserEndpoint;

import javax.xml.ws.Endpoint;

@Configuration
public class WebServiceConfig {

    @Autowired
    private Bus bus;

    @Autowired
    private AuthenticationEndpoint authenticationEndpoint;

    @Autowired
    private
    ProjectEndpoint projectEndpoint;

    @Autowired
    private
    TaskEndpoint taskEndpoint;

    @Autowired
    private
    UserEndpoint userEndpoint;

    @Bean
    public Endpoint authenticationEndpointService() {
        EndpointImpl endpoint = new EndpointImpl(bus, authenticationEndpoint);
        endpoint.publish("/authenticationEndpoint");
        return endpoint;
    }

    @Bean
    public Endpoint projectEndpointService() {
        EndpointImpl endpoint = new EndpointImpl(bus, projectEndpoint);
        endpoint.publish("/projectEndpoint");
        return endpoint;
    }

    @Bean
    public Endpoint taskEndpointService() {
        EndpointImpl endpoint = new EndpointImpl(bus, taskEndpoint);
        endpoint.publish("/taskEndpoint");
        return endpoint;
    }

    @Bean
    public Endpoint userEndpointService() {
        EndpointImpl endpoint = new EndpointImpl(bus, userEndpoint);
        endpoint.publish("/userEndpoint");
        return endpoint;
    }
}