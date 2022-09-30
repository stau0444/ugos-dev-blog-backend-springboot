FROM openjdk:11-jre
COPY ~/credential.yml credential.yml
COPY build/libs/ugosdevblog-0.0.1-*.jar app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","app.jar"]