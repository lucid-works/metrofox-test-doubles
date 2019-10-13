FROM openjdk:8-jre

LABEL maintainer=michael.lifschitz@gmail.com

ENV WIREMOCK_VERSION 2.23.2

RUN mkdir /var/wiremock
RUN mkdir /var/wiremock/mappings

WORKDIR /var/wiremock

RUN wget -O /var/wiremock/wiremock-standalone.jar https://repo1.maven.org/maven2/com/github/tomakehurst/wiremock-jre8-standalone/$WIREMOCK_VERSION/wiremock-jre8-standalone-$WIREMOCK_VERSION.jar

EXPOSE 8080 8443

CMD java -jar /var/wiremock/wiremock-standalone.jar