#Notice that we are using two FROM in the Dockerfile, we called it multi-stage builds.
#Multi-stage builds can help to optimize the docker image.
#We copy the built jar file from stage one which is maven and store only the jar file into the current working directory.
#Then, we discard the local Maven repositories and class files generated in the target directory.

# AS <NAME> to name this stage as maven
FROM maven:3.6.1-alpine AS maven
LABEL MAINTAINER="sphokuhle"

WORKDIR /usr/src/app
COPY . /usr/src/app
# Compile and package the application to an executable JAR
RUN mvn package

# For Java 8,
FROM adoptopenjdk/openjdk8:alpine-jre

ARG JAR_FILE=demo-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app
# Copy the demo.jar from the maven stage to the /opt/app directory of the current stage.
COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app/

ENTRYPOINT ["java","-jar","demo-0.0.1-SNAPSHOT.jar"]