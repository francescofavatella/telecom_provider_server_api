FROM maven:3.5.3-jdk-8 AS BUILDER

WORKDIR /usr/app

COPY pom.xml ./

#RUN mvn install
RUN mvn dependency:go-offline


WORKDIR /usr/app/src

COPY src ./

WORKDIR /usr/app

RUN mvn -e -X package

FROM openjdk:8-jre AS DEPLOYER
WORKDIR /usr/app
COPY --from=BUILDER /usr/app/target/telecom-provider-test-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT  ["java", "-jar", "telecom-provider-test-0.0.1-SNAPSHOT.jar"]
