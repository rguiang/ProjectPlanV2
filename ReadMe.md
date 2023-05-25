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

Once the application starts, open http://localhost:8080 on your browser to access the index page of the application.

To add new project, click the "Add Project" in the index page to redirect you to the new project creation page that will required you to input the project name and description.

There are 3 tools(Edit, Info, Delete) that you can use for every projects in the project lists.
```
Project List

Edit - Click the Edit to update the Project's name and desciption.
Info - Click the Info to view the Information of the project together with the task and task creation.
Delete - Click the Delete to remove/delete the project.
```

In the project info page, There is a button for the Task creation to be able to crate task for the chosen project and task list at the bottom of the page.

There is also 3 tools((Edit, Info, Delete)) that you can use to update the task.
```
Task list

Edit - Click the Edit to update the task.
Info - Click the Info to view the information of the task.
Delete - Click the Delete to remove/delete the task.
```
Note: 
```
1. In you can only update the start date of tasks that doesn't have dependency.
2. there is ony 8 hours can be allowed per day(8:00 am to 16:00pm).
```

In the task info page, you are able to see the list of dependency of the task and also in this page you are able to add or delete a dependency task.
