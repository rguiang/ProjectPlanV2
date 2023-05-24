# Project Plann

## Requirements

### For building and running the application you need:
```
JDK 11
Maven 4
PostgreSQL
```


## Postgres Instance Configuration
In order to use your instance please update the Database Configuration section in `application.properties`

```
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.hibernate.show-sql=true
spring.datasource.url=jdbc:postgresql://localhost:5432/project_plan
spring.datasource.username=postgres
spring.datasource.password=P@ssw0rd
```

## Running the application locally
Use the Spring boot Maven plugin to build the project:
```
mvn clean install -DskipTests
```
There are several ways to run a Spring Boot application on your local machine. One way is to execute the main method in the de.codecentric.springbootsample.Application class from your IDE.

Alternatively you can use the Spring Boot Maven plugin like so:
```
mvn spring-boot:run
```

Once the application starts, open http://localhost:8080 on your browser. 
