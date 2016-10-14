FROM frolvlad/alpine-oraclejdk8
VOLUME /tmp
ADD search-service-jar/target/search-service-jar-0.0.1-SNAPSHOT.jar search-service.jar
EXPOSE 8005
ENTRYPOINT ["java", "-jar", "search-service.jar"]