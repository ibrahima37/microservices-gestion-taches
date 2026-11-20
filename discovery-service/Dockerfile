## Étape 1 : build avec Maven
#FROM maven:3.9.6-eclipse-temurin-17 AS build
#WORKDIR /app
#COPY pom.xml .
#COPY . .
#RUN mvn clean package -DskipTests
#
## Étape 2 : image finale
#FROM eclipse-temurin:17-jre
#WORKDIR /app
##COPY --from=build /app/target/discovery-service-0.0.1-SNAPSHOT.jar app.jar
#COPY discovery-service/target/discovery-service-0.0.1-SNAPSHOT.jar app.jar
#EXPOSE 8761
#ENTRYPOINT ["java","-jar","app.jar"]
