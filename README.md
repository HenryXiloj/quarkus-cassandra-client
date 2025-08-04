# Quarkus Cassandra Client CRUD

A complete example of integrating [Quarkus](https://quarkus.io/) with [Apache Cassandra](https://cassandra.apache.org/) to build a REST API with CRUD operations. This project demonstrates how to create a Kubernetes Native Java application with NoSQL database connectivity.

📘 Blog Post: [Quarkus Cassandra Client CRUD](https://jarmx.blogspot.com/2022/10/quarkus-cassandra-client-crud.html)

## Overview

This application provides a REST API for managing products using Quarkus framework and Apache Cassandra as the database. It showcases the integration between Quarkus and Cassandra with proper configuration, data models, repositories, and services.

## Features

- REST API with full CRUD operations
- Quarkus framework integration
- Apache Cassandra NoSQL database
- Docker containerization
- Java 17 support
- Annotation-based data mapping

## Technology Stack

- **Java**: 17
- **Framework**: Quarkus 2.13.3.Final
- **Database**: Apache Cassandra 4.0.7
- **Build Tool**: Maven
- **Container**: Docker
- **IDE**: IntelliJ IDEA
- **Testing**: Postman

## API Endpoints

| Method | URL | Description |
|--------|-----|------------|
| GET | `/products` | Get all products |
| GET | `/products/{id}` | Get product by ID |
| POST | `/products` | Create new product |
| PUT | `/products/{id}` | Update product by ID |
| DELETE | `/products` | Delete product |

## Project Structure

```
quarkus-cassandra-client/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/henry/
│   │   │       ├── configuration/
│   │   │       │   └── Config.java
│   │   │       ├── mapper/
│   │   │       │   └── InventoryMapper.java
│   │   │       ├── model/
│   │   │       │   └── Product.java
│   │   │       ├── repository/
│   │   │       │   └── ProductDao.java
│   │   │       ├── resource/
│   │   │       │   └── ProductResource.java
│   │   │       └── service/
│   │   │           └── ProductService.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker
- Cassandra 4.0.7+ (or Docker container)

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/HenryXiloj/quarkus-cassandra-client.git
cd quarkus-cassandra-client
```

### 2. Start Cassandra with Docker

```bash
# Pull Cassandra image
docker pull cassandra

# Run Cassandra container
docker run --name cassandra -p 127.0.0.1:9042:9042 -p 127.0.0.1:9160:9160 -d cassandra

# Access Cassandra shell
docker exec -it cassandra /bin/bash
cqlsh
```

### 3. Create Database Schema

```sql
-- Check datacenter name
USE system;
SELECT data_center FROM local;

-- Create keyspace
CREATE KEYSPACE inventory
WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

-- Create product table
CREATE TABLE inventory.product(
    id uuid PRIMARY KEY, 
    description text
);
```

### 4. Configure Application

The application is configured through `application.properties`:

```properties
quarkus.http.port=9000
quarkus.cassandra.contact-points=localhost:9042
quarkus.cassandra.local-datacenter=datacenter1
keyspace.one=inventory
```

### 5. Build and Run

```bash
# Development mode
./mvnw compile quarkus:dev

# Or run tests
./mvnw test
```

The application will start on `http://localhost:9000`

## Dependencies

Key dependencies in `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>io.quarkus</groupId>
        <artifactId>quarkus-resteasy-reactive</artifactId>
    </dependency>
    <dependency>
        <groupId>io.quarkus</groupId>
        <artifactId>quarkus-resteasy-reactive-jackson</artifactId>
    </dependency>
    <dependency>
        <groupId>com.datastax.oss.quarkus</groupId>
        <artifactId>cassandra-quarkus-client</artifactId>
    </dependency>
    <dependency>
        <groupId>com.datastax.oss</groupId>
        <artifactId>java-driver-mapper-processor</artifactId>
        <version>4.15.0</version>
    </dependency>
    <dependency>
        <groupId>com.datastax.oss</groupId>
        <artifactId>java-driver-mapper-runtime</artifactId>
    </dependency>
</dependencies>
```

## Usage Examples

### Create a Product

```bash
curl -X POST http://localhost:9000/products \
  -H "Content-Type: application/json" \
  -d '{"description": "Sample Product"}'
```

### Get All Products

```bash
curl -X GET http://localhost:9000/products
```

### Get Product by ID

```bash
curl -X GET http://localhost:9000/products/{product-id}
```

### Update Product

```bash
curl -X PUT http://localhost:9000/products/{product-id} \
  -H "Content-Type: application/json" \
  -d '{"description": "Updated Product"}'
```

### Delete Product

```bash
curl -X DELETE http://localhost:9000/products \
  -H "Content-Type: application/json" \
  -d '{"id": "product-id", "description": "Product to delete"}'
```

## Key Components

### Product Model
- Entity class with UUID primary key
- Uses Cassandra mapper annotations
- Simple POJO with id and description fields

### Repository Layer
- DAO interface with CRUD operations
- Uses DataStax driver annotations
- Supports pagination with `PagingIterable`

### Service Layer
- Business logic implementation
- JSON processing with Jackson
- UUID generation for new products

### REST Resource
- JAX-RS endpoints
- JSON request/response handling
- Dependency injection with CDI

## Architecture Highlights

- **Annotation Processing**: Uses DataStax mapper annotations for code generation
- **Multiple Keyspaces**: Supports configuration for multiple Cassandra keyspaces
- **Native Compilation**: Ready for GraalVM native image compilation
- **Reactive**: Built on Quarkus reactive stack

## Testing

The application includes:
- JUnit 5 test framework
- REST Assured for API testing
- Test configurations for different environments

## Docker Support

The project includes native Docker support through Quarkus:

```bash
# Build native image
./mvnw package -Pnative

# Build Docker image
docker build -f src/main/docker/Dockerfile.native -t quarkus/cassandra-client .
```

## References

- [Quarkus Cassandra Guide](https://quarkus.io/guides/cassandra)
- [DataStax Java Driver Documentation](https://docs.datastax.com/en/developer/java-driver/4.2/manual/mapper/)
- [Apache Cassandra Documentation](https://cassandra.apache.org/_/index.html)
- [Quarkus CDI Reference](https://quarkus.io/guides/cdi-reference)



## Contributing

Contributions are welcome! Please feel free to submit pull requests or open issues for any improvements or bug fixes.
