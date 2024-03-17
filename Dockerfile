FROM openjdk:11
LABEL maintainer="task.net"
ADD target/Task0-0.0.1-SNAPSHOT.jar springboot-docker-demo.jar
ENTRYPOINT ["java","-jar","springboot-docker-demo.jar"]