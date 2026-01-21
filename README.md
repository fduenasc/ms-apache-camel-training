# Apache Camel Training - Learning Microservice

This project is a **learning and training** microservice for Apache Camel, designed to practice and understand the fundamental concepts of integration using Apache Camel with Quarkus.

## 📚 Description

This project contains a series of practical exercises covering basic and advanced Apache Camel concepts, including:

- Basic components (Timer, Direct)
- Message processing
- Data transformation
- RabbitMQ integration
- Enterprise Integration Patterns (EIPs)

## 🛠️ Technologies Used

- **Java 21**
- **Quarkus 3.8.6** - Supersonic Subatomic Java Framework
- **Apache Camel** - Enterprise integration framework
- **RabbitMQ** - Message broker for asynchronous messaging
- **Maven** - Dependency management and build tool

## 📋 Prerequisites

Before running this project, make sure you have installed:

- **Java 21** or higher
- **Maven 3.8+**
- **RabbitMQ** (optional, only for exercises using messaging)

### RabbitMQ Installation

For exercises that use RabbitMQ, you need to have a RabbitMQ server running. You can use Docker/Podman:

```bash
# Using Podman
podman run -d --name rabbitmq-got -p 5672:5672 -p 15672:15672 docker.io/library/rabbitmq:3-management

# Or using Docker
docker run -d --name rabbitmq-got -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

**Default credentials:**
- Username: `guest`
- Password: `guest`
- Web interface: http://localhost:15672

## 🚀 Running the Application

### Development Mode (Recommended)

Development mode enables hot reload and is ideal for learning and experimenting:

```bash
./mvnw quarkus:dev
```

Once started, the application will be available and you can see the exercise logs in real-time.

> **Note:** Quarkus includes a Dev UI available in development mode at: http://localhost:8080/q/dev/

### Packaging and Running

To package the application:

```bash
./mvnw package
```

This produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory. To run it:

```bash
java -jar target/quarkus-app/quarkus-run.jar
```

### Native Executable

To create a native executable:

```bash
./mvnw package -Dnative
```

Or using a container (if you don't have GraalVM installed):

```bash
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

## 📁 Project Structure

```
src/main/java/co/com/fduenasc/
├── Exercise1Router.java      # Exercise 1: Timer component
├── Exercise2Router.java       # Exercise 2: Direct endpoint and log component
├── Exercise3Router.java       # Exercise 3: Custom Processor
├── GameOfThronesRouter.java   # Advanced example: RabbitMQ integration
├── RabbitMQConfiguration.java # RabbitMQ configuration
└── TrainingRouter.java        # Basic training router
```

## 📖 Included Exercises

### Exercise 1: Timer Component
**File:** `Exercise1Router.java`

- **Objective:** Learn to use Apache Camel's `timer` component
- **Concepts:** Timer component, Exchange, Processors
- **Functionality:** Prints sequential numbers every 3 seconds

**To see it in action:** Start the application and observe the logs every 3 seconds.

### Exercise 2: Direct Endpoint and Log Component
**File:** `Exercise2Router.java`

- **Objective:** Learn to use `direct` endpoints and the `log` component
- **Concepts:** Direct endpoint, Log component, Route ID
- **Functionality:** Reads messages from `direct:start` and prints them to console

**To test it:** Send a message to the `direct:start` endpoint from another route or using ProducerTemplate.

### Exercise 3: Custom Processor
**File:** `Exercise3Router.java`

- **Objective:** Create a custom Processor to transform messages
- **Concepts:** Custom Processor, Data transformation, Exchange manipulation
- **Functionality:** Transforms text to uppercase using a custom Processor

**To test it:** The route includes a test example that runs automatically on startup.

### Advanced Example: Game of Thrones Router
**File:** `GameOfThronesRouter.java`

- **Objective:** Complete example of RabbitMQ integration
- **Concepts:** RabbitMQ integration, JSON marshalling, Split EIP
- **Functionality:** Sends JSON messages with Game of Thrones characters to RabbitMQ

**Requirements:** RabbitMQ must be running (see installation section).

## ⚙️ Configuration

### RabbitMQ Configuration

RabbitMQ configuration is located in `src/main/resources/application.properties`:

```properties
rabbitmq.host=localhost
rabbitmq.port=5672
rabbitmq.username=guest
rabbitmq.password=guest
```

You can modify these values according to your environment.

### Camel Quarkus Extensions

The project uses the following Camel Quarkus extensions:

- `camel-quarkus-core` - Camel core functionality
- `camel-quarkus-timer` - Timer component
- `camel-quarkus-direct` - Direct component
- `camel-quarkus-spring-rabbitmq` - RabbitMQ integration
- `camel-quarkus-jackson` - JSON processing

## 🔍 Verification and Monitoring

### Application Logs

All exercises generate logs that you can see in the console. Look for messages like:

```
INFO  [co.com.fduenasc.Exercise1Router] Número secuencial: 0
INFO  [co.com.fduenasc.Exercise3Router] Texto transformado a mayúsculas: HOLA MUNDO
```

### RabbitMQ Management UI

If you're using exercises with RabbitMQ, you can access the management interface:

- **URL:** http://localhost:15672
- **Username:** guest
- **Password:** guest

From here you can:
- View created exchanges and queues
- Monitor messages
- View connection statistics

## 📚 Learning Resources

### Official Documentation

- [Apache Camel Documentation](https://camel.apache.org/manual/)
- [Camel Quarkus Reference](https://camel.apache.org/camel-quarkus/latest/reference/index.html)
- [Quarkus Documentation](https://quarkus.io/guides/)
- [RabbitMQ Documentation](https://www.rabbitmq.com/documentation.html)

### Key Apache Camel Concepts

- **Route:** Defines the message processing flow
- **Endpoint:** Entry or exit point of a route
- **Exchange:** Container for the message in transit
- **Processor:** Component that processes the Exchange
- **EIP (Enterprise Integration Patterns):** Enterprise integration patterns

## 🐛 Troubleshooting

### RabbitMQ container doesn't start

```bash
# Check if the container is running
podman ps | grep rabbitmq

# If it's stopped, start it
podman start rabbitmq-got

# Check the logs
podman logs rabbitmq-got
```

### No messages visible in RabbitMQ

1. Verify that RabbitMQ is running
2. Make sure the exchange and queue are created (they are created automatically on startup)
3. Check application logs for connection errors

### Error: "No endpoint could be found for: direct://..."

Make sure the `camel-quarkus-direct` extension is in the `pom.xml`:

```xml
<dependency>
    <groupId>org.apache.camel.quarkus</groupId>
    <artifactId>camel-quarkus-direct</artifactId>
</dependency>
```

## 📝 Additional Notes

- This project is designed for **learning and training**
- Exercises are progressive: start with Exercise 1 and advance sequentially
- Experiment by modifying the routers to better understand how Apache Camel works
- Logs are your best friend to understand message flow

## 🤝 Contributions

This is a learning project. Feel free to:
- Experiment with the exercises
- Add new exercises
- Improve documentation
- Share your learnings

## 📄 License

This project is for educational and training purposes.

---

**Happy learning with Apache Camel! 🐪**
