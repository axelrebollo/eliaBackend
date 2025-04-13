#Base img with JDK17
FROM openjdk:17

#Work folder into container
WORKDIR /app

#Copy .jar generated
COPY target/user-0.0.1-SNAPSHOT.jar user.jar

#Port
EXPOSE 18090

#Execute application
ENTRYPOINT ["java", "-jar", "/app/user.jar"]