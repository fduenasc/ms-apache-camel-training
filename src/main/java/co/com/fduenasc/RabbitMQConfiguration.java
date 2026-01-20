package co.com.fduenasc;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

import java.util.Objects;

/**
 * Configuration class for RabbitMQ ConnectionFactory
 * This provides the Spring AMQP ConnectionFactory required by the spring-rabbitmq component
 * and automatically creates the exchange and queue for Game of Thrones messages
 */
@ApplicationScoped
public class RabbitMQConfiguration {

    private static final Logger LOGGER = Logger.getLogger(RabbitMQConfiguration.class);
    
    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_USERNAME = "guest";
    private static final String DEFAULT_PASSWORD = "guest";
    
    // Exchange and Queue names
    private static final String EXCHANGE_NAME = "got-exchange";
    private static final String QUEUE_NAME = "character-queue";
    private static final String ROUTING_KEY = "character";

    @ConfigProperty(name = "rabbitmq.host", defaultValue = DEFAULT_HOST)
    String host;

    @ConfigProperty(name = "rabbitmq.port", defaultValue = "5672")
    int port;

    @ConfigProperty(name = "rabbitmq.username", defaultValue = DEFAULT_USERNAME)
    String username;

    @ConfigProperty(name = "rabbitmq.password", defaultValue = DEFAULT_PASSWORD)
    String password;

    private ConnectionFactory connectionFactory;

    @Produces
    @ApplicationScoped
    @SuppressWarnings("null") // Objects.requireNonNullElse() guarantees non-null return when defaultValue is non-null
    public ConnectionFactory produceConnectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(Objects.requireNonNullElse(host, DEFAULT_HOST));
        factory.setPort(port);
        factory.setUsername(Objects.requireNonNullElse(username, DEFAULT_USERNAME));
        factory.setPassword(Objects.requireNonNullElse(password, DEFAULT_PASSWORD));
        this.connectionFactory = factory;
        return factory;
    }

    @PostConstruct
    public void init() {
        try {
            // Ensure connectionFactory is initialized
            if (connectionFactory == null) {
                connectionFactory = produceConnectionFactory();
            }
            
            // Create RabbitAdmin to manage exchanges and queues
            // Objects.requireNonNull ensures non-null for type safety
            ConnectionFactory factory = Objects.requireNonNull(connectionFactory, "ConnectionFactory must not be null");
            RabbitAdmin admin = new RabbitAdmin(factory);
            
            // Create the exchange (topic exchange for flexibility)
            TopicExchange exchange = new TopicExchange(EXCHANGE_NAME, true, false);
            admin.declareExchange(exchange);
            LOGGER.info("Created exchange: " + EXCHANGE_NAME);
            
            // Create the queue
            Queue queue = new Queue(QUEUE_NAME, true, false, false);
            admin.declareQueue(queue);
            LOGGER.info("Created queue: " + QUEUE_NAME);
            
            // Bind the queue to the exchange with the routing key
            Binding binding = BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
            admin.declareBinding(binding);
            LOGGER.info("Bound queue '" + QUEUE_NAME + "' to exchange '" + EXCHANGE_NAME + "' with routing key '" + ROUTING_KEY + "'");
            
        } catch (Exception e) {
            LOGGER.warn("Failed to create RabbitMQ exchange/queue. They may already exist or RabbitMQ may not be available: " + e.getMessage());
        }
    }
}

