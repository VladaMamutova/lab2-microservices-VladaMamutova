FROM openjdk:11-jdk

VOLUME /tmp

ENV PORT=8280
EXPOSE 8280

ADD target/warehouse-service-1.0-SNAPSHOT.jar warehouse-service-1.0-SNAPSHOT.jar

RUN bash -c 'touch /warehouse-service-1.0-SNAPSHOT.jar'

ENTRYPOINT ["java","-Dserver.port=${PORT:-8280}","-Djava.security.egd=file:/dev/./urandom","-XX:+UseContainerSupport","-Xmx512m","-Xms128m","-jar","/warehouse-service-1.0-SNAPSHOT.jar"]
