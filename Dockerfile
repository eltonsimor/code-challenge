FROM openjdk:8-jre-alpine
MAINTAINER "Elton Beserra Moraes - eltonsimor@gmail.com"

COPY build/libs/code-challenge.jar code-challenge.jar

EXPOSE 8080

CMD ["sh", "-c", "java ${JAVA_OPTS} -jar code-challenge.jar"]
