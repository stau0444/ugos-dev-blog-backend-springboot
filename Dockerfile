FROM openjdk:11-jre
RUN mkdir "/home/ubuntu/kkk"
COPY ~/credential.yml /home/ubuntu/credential.yml
COPY build/libs/ugosdevblog-0.0.1-*.jar app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","app.jar"]