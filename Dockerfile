FROM openjdk:8
EXPOSE 8080
ADD target/spring-boot-crud-example-0.0.1-SNAPSHOT.jar spring-boot-crud-example-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","spring-boot-crud-example-0.0.1-SNAPSHOT.jar" ]