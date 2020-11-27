FROM openjdk:11-jdk

VOLUME /tmp

ENV PORT=8480
EXPOSE 8480

ADD target/store-service-1.0-SNAPSHOT.jar store-service-1.0-SNAPSHOT.jar

RUN bash -c 'touch /store-service-1.0-SNAPSHOT.jar'

ENTRYPOINT ["java","-Dserver.port=${PORT:-8380}","-Djava.security.egd=file:/dev/./urandom","-XX:+UseContainerSupport","-Xmx512m","-Xms128m","-jar","/store-service-1.0-SNAPSHOT.jar"]
