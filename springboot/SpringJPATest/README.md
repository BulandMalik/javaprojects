# Spring JPA Test App

## Closing particular port on Mac
1. lsof -i :8080 (get the PID)
2. kill -9 PID

## H2 JPA Test
1. Add Pom H2 dependency
```
    <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-data-jpa</artifactId>
   </dependency>
   
   <dependency>
       <groupId>com.h2database</groupId>
       <artifactId>h2</artifactId>
       <scope>runtime</scope>
   </dependency>
```
2. By default, Spring Boot configures the application to connect to an in-memory store with the username sa and an empty password. However, we can change those parameters by adding the following properties to the application.properties/yaml file:
```aidl
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    username: sa
    password: password
    driverClassName: org.h2.Driver
    jpa:
      spring.jpa.database-platform: org.hibernate.dialect.H2Dialect

  h2:
    console:
      enabled: true
      path: /h2-console
```
3. By design, the in-memory database is volatile, and results in data loss after application restart. We can change that behavior by using file-based storage. To do this we need to update the spring.datasource.url property:
```aidl
spring:
  datasource:
    url: jdbc:h2:file:/data/demo
```
4. Database Operations
```aidl
We can use basic SQL scripts to initialize the database. In order to demonstrate this, let's add a data.sql file under src/main/resources directory:
INSERT INTO parent_entity (id, parentProperty) VALUES (1, 'p1');
INSERT INTO parent_entity (id, parentProperty) VALUES (2, 'p2');

INSERT INTO child_entity (id, childProperty1, childProperty2, parent) VALUES (1, 'cp1.1', 'cp1.2', p1');
INSERT INTO child_entity (id, childProperty1, childProperty2, parent) VALUES (2, 'cp2.1', 'cp2', p2.2');

update parent_entity set children (id, parentProperty) VALUES (2, 'p2');
``` 
5. By default, data.sql scripts get executed before the Hibernate is initialized. We need Hibernate to create our tables before inserting the data into them. To achieve this, we need to defer the initialization of our data source. We'll use the below property to achieve this:
```aidl
spring:
  jpa:
    defer-datasource-initialization=true:

```
6. Please note that for any script-based initialization, i.e. inserting data via data.sql or creating schema via schema.sql (which we'll learn next), we need to set the below property:
``` spring.sql.init.mode=always ```
6. References

   1. https://www.baeldung.com/spring-boot-data-sql-and-schema-sql
   2. https://www.baeldung.com/spring-boot-h2-database
