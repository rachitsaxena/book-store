FROM frolvlad/alpine-oraclejdk8
VOLUME /tmp
ADD profile-service-jar/target/profile-service-jar-0.0.1-SNAPSHOT.jar profile-service.jar
EXPOSE 9010
ENTRYPOINT ["java", "-jar", "profile-service.jar"]