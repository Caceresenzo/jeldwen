FROM jeldwen-base

RUN mkdir /jeldwen

COPY pom.xml /jeldwen

COPY backend /jeldwen/backend
COPY socket /jeldwen/socket
COPY beacon /jeldwen/beacon
COPY frontend /jeldwen/frontend

WORKDIR /jeldwen

RUN mvn package