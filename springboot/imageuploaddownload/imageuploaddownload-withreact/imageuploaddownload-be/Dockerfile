# ——— Build/Test image ——————————————————————————————————————————————————————
# Name the builder image "as builder"
FROM maven:3-openjdk-11-slim as builder

WORKDIR /build

COPY . .

# Build the Maven project for all modules
RUN mvn clean install -DskipTests

# ——— Final production image ————————————————————————————————————————————————
FROM amazoncorretto:11.0.20-alpine3.18

MAINTAINER learntechverse.com

ENV ENVIRONMENT=dev

# Copy JAR layers, layers that change more often should go at the end
COPY --from=builder /build/target/imageuploaddownload-be-0.0.1-SNAPSHOT.jar ./imageuploaddownload-be.jar

#exposing the port where our Spring Boot application will run on.
EXPOSE 8080

#HEALTHCHECK --interval=5m --timeout=3s CMD curl -f http://localhost:8080/actuator/health/ || exit 1

#Interval - specifies the time between the health check for the application container. it waits for the specified time from one check to another.
#Timeout - specifies the time that the health check waits for a response to consider the status of the container. For example, if we define 30 seconds and our server doesn’t respond within 30 seconds, then it’s considered as failed.
#Retries - specifies the number of consecutive health check failures required to declare the container status as unhealthy. Health check will only try up to the specified retry number. If the server fails consecutively up to the specified times, it is then considered unhealthy.

# User app
#RUN useradd -d /home/appuser -m -s /bin/bash appuser
#USER appuser

#https://www.baeldung.com/java-security-egd
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/imageuploaddownload-be.jar"]
ENTRYPOINT ["java","-jar","/imageuploaddownload-be.jar"]
