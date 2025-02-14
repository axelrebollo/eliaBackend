#Base img with JDK21
FROM openjdk:17

#Work folder into container
WORKDIR /app

#Copy .jar generated
COPY target/notebook-0.0.1-SNAPSHOT.jar notebook.jar

#Port
EXPOSE 18091

#Execute application
ENTRYPOINT ["java", "-jar", "notebook.jar"]