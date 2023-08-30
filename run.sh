#!/bin/bash

mvn clean install  -Dmaven.test.skip

docker container rm ewm-service
docker container rm stats-client
docker container rm stats-server
docker container rm stats-db
docker container rm ewm-db



docker image rm ewm-stats-client
docker image rm ewm-stats-server
docker image rm ewm-stats-server
docker image rm postgres:14-alpine

docker-compose up
