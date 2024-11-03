FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline #Baixa todas as dependências do projeto Maven, permitindo que o processo de construção possa ocorrer offline. Isso ajuda a economizar tempo em builds futuros, pois as dependências já estarão no cache.

COPY src src

RUN mvn package -DskipTests

FROM openjdk:17-slim
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prd"]