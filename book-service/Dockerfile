FROM frolvlad/alpine-oraclejdk8
VOLUME /tmp
ADD book-service-jar/target/book-service-jar-0.0.1-SNAPSHOT.jar book-service.jar
EXPOSE 8101
ENTRYPOINT ["java", "-jar", "book-service.jar"]