FROM openjdk:11

ENV APP_HOME=/usr/app/

WORKDIR $APP_HOME

COPY build/libs/kurento-0.0.1-SNAPSHOT.jar kurentoApplication.jar

EXPOSE 8443

CMD ["java", "-jar","-Dkms.url=ws://52.79.213.254:8888/kurento", "kurentoApplication.jar"]
