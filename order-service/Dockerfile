FROM openjdk:11-jdk

VOLUME /tmp

ENV PORT=8380
EXPOSE 8380

ADD target/order-service-1.0-SNAPSHOT.jar order-service-1.0-SNAPSHOT.jar

RUN bash -c 'touch /order-service-1.0-SNAPSHOT.jar'

ENTRYPOINT ["java","-Dserver.port=${PORT:-8380}","-Djava.security.egd=file:/dev/./urandom","-XX:+UseContainerSupport","-Xmx512m","-Xms128m","-jar","/order-service-1.0-SNAPSHOT.jar"]
