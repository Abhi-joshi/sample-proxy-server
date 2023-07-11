FROM maven:3.9.3-eclipse-temurin-17-alpine AS build
COPY src /home/sample-proxy-server/src
COPY pom.xml /home/sample-proxy-server
RUN mvn -f /home/sample-proxy-server/pom.xml clean package -Dmaven.test.skip

FROM amazoncorretto:17
MAINTAINER abhishek
COPY --from=build /home/sample-proxy-server/target/sample-proxy-server-0.0.1-SNAPSHOT.jar sample-proxy-server-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/sample-proxy-server-0.0.1-SNAPSHOT.jar"]