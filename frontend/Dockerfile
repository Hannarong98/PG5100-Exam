# NOTE: This file is a direct copy of:
# https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/frontend/Dockerfile

# ONLY used for testing

FROM adoptopenjdk/openjdk11:alpine

COPY target/quizgame-exec.jar .


CMD java -jar quizgame-exec.jar \
    --spring.datasource.username="postgres"  \
    --spring.datasource.password  \
    --spring.datasource.url="jdbc:postgresql://postgresql-host:5432/postgres"