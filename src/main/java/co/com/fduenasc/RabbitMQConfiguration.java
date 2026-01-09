package co.com.fduenasc;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

/**
 * Configuration class for RabbitMQ ConnectionFactory
 * This provides the Spring AMQP ConnectionFactory required by the spring-rabbitmq component
 */
@ApplicationScoped
public class RabbitMQConfiguration {

    @ConfigProperty(name = "rabbitmq.host", defaultValue = "localhost")
    String host;

    @ConfigProperty(name = "rabbitmq.port", defaultValue = "5672")
    int port;

    @ConfigProperty(name = "rabbitmq.username", defaultValue = "guest")
    String username;

    @ConfigProperty(name = "rabbitmq.password", defaultValue = "guest")
    String password;

    @Produces
    @ApplicationScoped
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        return factory;
    }
}

