## BUILD STAGE
FROM gradle:jdk21@sha256:4301b1887d736d5b90c20b2f65593ee0d545a543a41d117e5a2df36b3bc22b88 AS build

COPY --chown=gradle:gradle . /project

WORKDIR /project

RUN gradle clean build --stacktrace

## PACKAGE STAGE
FROM eclipse-temurin:21-jre-alpine@sha256:23467b3e42617ca197f43f58bc5fb03ca4cb059d68acd49c67128bfded132d67

COPY --from=build /project/build/libs/gatewayserver-*-SNAPSHOT.jar /project/gatewayserver.jar

ENTRYPOINT ["java", "-jar", "/project/gatewayserver.jar"]