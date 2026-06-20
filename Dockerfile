# Stage 1: Build the Maven application
FROM maven:3-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the application in Apache Tomcat
FROM tomcat:10-jdk21-temurin

# Remove default Tomcat apps to avoid paths conflicts
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the built war file as ROOT.war so it serves at the base URL (/) instead of (/FashionStore)
COPY --from=build /app/target/FashionStore.war /usr/local/tomcat/webapps/ROOT.war

# Expose port 8080 for web traffic
EXPOSE 8080

CMD ["catalina.sh", "run"]
