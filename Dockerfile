FROM openjdk:17
Expose 8081
ADD target/customer-service.jar
ENTRYPOINT ["java","-jar","/customer-service.jar"]