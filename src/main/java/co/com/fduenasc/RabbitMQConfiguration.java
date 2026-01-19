package co.com.fduenasc;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import java.util.Objects;

/**
 * Configuration class for RabbitMQ ConnectionFactory
 * This provides the Spring AMQP ConnectionFactory required by the spring-rabbitmq component
 */
@ApplicationScoped
public class RabbitMQConfiguration {

    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_USERNAME = "guest";
    private static final String DEFAULT_PASSWORD = "guest";

    @ConfigProperty(name = "rabbitmq.host", defaultValue = DEFAULT_HOST)
    String host;

    @ConfigProperty(name = "rabbitmq.port", defaultValue = "5672")
    int port;

    @ConfigProperty(name = "rabbitmq.username", defaultValue = DEFAULT_USERNAME)
    String username;

    @ConfigProperty(name = "rabbitmq.password", defaultValue = DEFAULT_PASSWORD)
    String password;

    @Produces
    @ApplicationScoped
    @SuppressWarnings("null") // Objects.requireNonNullElse() guarantees non-null return when defaultValue is non-null
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(Objects.requireNonNullElse(host, DEFAULT_HOST));
        factory.setPort(port);
        factory.setUsername(Objects.requireNonNullElse(username, DEFAULT_USERNAME));
        factory.setPassword(Objects.requireNonNullElse(password, DEFAULT_PASSWORD));
        return factory;
    }
}

