#Base img with JDK17
FROM openjdk:17

#Work folder into container
WORKDIR /app

#Copy .jar generated
COPY target/notebook-0.0.1-SNAPSHOT.jar notebook.jar

#Port
EXPOSE 18091

#Execute application
ENTRYPOINT ["java", "-jar", "/app/notebook.jar"]