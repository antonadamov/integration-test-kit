# integration-test-kit

A Java-based integration test harness for backend services — supports Kafka, DocumentDB, and REST endpoints.

## Features

- Publish & consume Kafka messages (sync/async)
- Interact with DocumentDB (MongoDB-compatible)
- Send & receive REST HTTP requests
- Designed for integration, end-to-end, or contract tests
- Easily extendable: plug in new APIs (e.g., gRPC, SQL) by implementing interfaces

## Installation (Maven)
```xml
    <dependency>
      <groupId>me.antonadamov</groupId>
      <artifactId>async-testing-framework</artifactId>
      <version>1.0.0</version>
      <scope>test</scope>
    </dependency>
```
> Replace the version with the latest release if needed.

## Usage

### 1. Setup
```java
    KafkaTestClient kafka = KafkaTestClient.builder()
        .bootstrapServers("localhost:9092")
        .build();

    DocumentDbTestClient db = DocumentDbTestClient.builder()
        .connectionString("mongodb://localhost:27017")
        .database("testdb")
        .build();

    RestTestClient rest = RestTestClient.builder()
        .baseUrl("http://localhost:8080")
        .build();

    AsyncTestFramework framework = AsyncTestFramework.builder()
        .kafka(kafka)
        .documentDb(db)
        .rest(rest)
        .build();
```

### 2. Writing a test
```java
    @Test
    void testWorkflow() {
        framework.clearAll();

        // Send Kafka message
        kafka.send("orders", new Order(...));

        // Wait for processing and REST response
        Response resp = rest.get("/orders/123");
        assertEquals(200, resp.getStatus());

        // Verify document in DB
        OrderDoc doc = db.findOne("orders", eq("id", "123"));
        assertNotNull(doc);
        assertEquals("COMPLETED", doc.getStatus());

        // Optional: read messages from Kafka output topic
        List<Payment> payments = kafka.consume("payments", 1, Duration.ofSeconds(5));
        assertEquals(1, payments.size());
    }
```

### 3. Advanced usage

- Support for async flows with configurable timeouts
- Supports custom serializers/deserializers
- Reset Kafka topics and clear DB collections between tests
- Extendable: add new clients (e.g., SQL, gRPC)

## Configuration

| Client                | Properties                                                        |
|-----------------------|-------------------------------------------------------------------|
| KafkaTestClient       | bootstrapServers, topics, serializer, deserializer                |
| DocumentDbTestClient  | connectionString, database, collections                           |
| RestTestClient        | baseUrl, authentication, timeout                                  |
| AsyncTestFramework    | kafka(...), documentDb(...), rest(...), timeout(...)              |

## Why use this?

- All-in-one test harness: no need to add multiple frameworks
- Standard builder + JUnit integration
- Idempotent tests: automatic state cleanup
- Ideal for contract tests between microservices

## Example Test

```java
    public class OrderProcessingTest {
      private AsyncTestFramework fw;

      @BeforeEach
      void setup() {
        fw = AsyncTestFramework.builder()
            // ...init clients
            .build();
        fw.clearAll();
      }

      @Test
      void shouldProcessOrder() {
        // ...same as above
      }

      @AfterEach
      void teardown() {
        fw.close();
      }
    }
```


