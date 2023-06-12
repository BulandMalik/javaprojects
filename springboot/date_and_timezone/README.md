# About Code #

- This code is a sample implementation of an API to demonstrate use of date and time libraties with different timezones.

## Tech Stack 

- Java 8
- Springboot
- JPA (Spring Data)
- H2 DB
- Model Mapper

## Setup Guide 

1. Clone the Code : git clone git@bitbucket.org:thisara_udaya/myfleet.git -b use-java-time

2. Install Dependencies : `` mvnw install ``

3. Run the Code : `` mvnw spring-boot:run ``

## APIs

> GET http://localhost:8080/trips/search/:carId?fromDate=:fromDate&timezone=:timezone

> GET http://localhost:8080/trips/:carId

> GET http://localhost:8080/cars/search/:carId

> GET http://localhost:8080/cars/:carId


> POST http://localhost:8080/trips

> POST http://localhost:8080/cars

> DELETE http://localhost:8080/trips

> DELETE http://localhost:8080/cars

#### Demo video link :  https://youtu.be/poO-_V339mY