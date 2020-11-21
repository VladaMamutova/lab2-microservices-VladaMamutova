FROM openjdk:11-jdk

VOLUME /tmp

ENV PORT=8180
EXPOSE 8180

ADD target/warranty-service-1.0-SNAPSHOT.jar warranty-service-1.0-SNAPSHOT.jar

RUN bash -c 'touch /warranty-service-1.0-SNAPSHOT.jar'

ENTRYPOINT ["java","-Dserver.port=${PORT:-8180}","-Djava.security.egd=file:/dev/./urandom","-XX:+UseContainerSupport","-Xmx512m","-Xms128m","-jar","/warranty-service-1.0-SNAPSHOT.jar"]
