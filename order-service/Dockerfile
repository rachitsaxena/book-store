FROM frolvlad/alpine-oraclejdk8
VOLUME /tmp
ADD order-service-jar/target/order-service-jar-0.0.1-SNAPSHOT.jar order-service.jar
EXPOSE 8011
ENTRYPOINT ["java", "-jar", "order-service.jar"]