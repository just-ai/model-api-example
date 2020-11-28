FROM ubuntu:latest
MAINTAINER i.ilyin@just-ai.com
ENV PYTHONUNBUFFERED=1
RUN apt-get update \
    && apt-get install -y python3-pip python3-dev \
    && ln -sf python3 /usr/bin/python
RUN pip3 install --no-cache --upgrade pip setuptools
RUN pip install --upgrade pip
RUN DEBIAN_FRONTEND="noninteractive" apt-get -y install tzdata
RUN apt-get install -y openjdk-8-jdk
RUN apt-get -y install locales
RUN sed -i '/en_US.UTF-8/s/^# //g' /etc/locale.gen && \
    locale-gen
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8
RUN pip install -U spacy
RUN python -m spacy download en_core_web_sm
RUN python -m spacy download ja_core_news_sm
COPY scripts scripts
COPY creds creds
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080
