FROM frolvlad/alpine-oraclejdk8
VOLUME /tmp
ADD inventory-service-jar/target/inventory-service-jar-0.0.1-SNAPSHOT.jar inventory-service.jar
EXPOSE 9501
ENTRYPOINT ["java", "-jar", "inventory-service.jar"]