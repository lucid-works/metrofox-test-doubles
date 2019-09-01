FROM openjdk:8u212-jre-alpine3.9

WORKDIR /opt/app
ARG JAR_FILE
ARG INFO_APP_BUILD
ARG INFO_APP_ENV

#######################
# Expose ports:
# - smtp  : 3025
# - smtps : 3465
# - pop3  : 3110
# - pop3s : 3995
# - imap  : 3143
# - imaps : 3993
#######################
EXPOSE 3025 3465 3110 3995 3143 3993

ENV JAVA_OPTS -Djava.net.preferIPv4Stack=true
ENV GREENMAIL_OPTS -Dgreenmail.hostname=0.0.0.0 \
    -Dgreenmail.setup.test.all \
    -Dgreenmail.auth.disabled

RUN addgroup greenmail && adduser -G greenmail -D greenmail

COPY ${JAR_FILE} /home/greenmail/app.jar
ENV INFO_APP_BUILD=${INFO_APP_BUILD}
ENV SPRING_PROFILES_ACTIVE=${INFO_APP_ENV}

RUN chown greenmail:greenmail /home/greenmail/app.jar
USER greenmail
WORKDIR /home/greenmail

CMD java $JAVA_OPTS $GREENMAIL_OPTS -jar /home/greenmail/app.jar