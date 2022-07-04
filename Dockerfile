FROM openjdk:8-jdk-alpine
RUN mkdir /opt/app
COPY assignment-0.0.1-SNAPSHOT.jar /opt/app
EXPOSE 9091:9091
CMD ["java", "-jar", "/opt/app/assignment-0.0.1-SNAPSHOT.jar"]