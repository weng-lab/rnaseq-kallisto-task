FROM openjdk:8-jre-slim as base
RUN apt-get update && apt-get install -y \
    build-essential \
    wget \
    python-pip \
    python3 \
    python3-pip \
    liblzma-dev \
    libbz2-dev \
    zlib1g-dev \
    python \
    git

RUN mkdir /software
WORKDIR /software
ENV PATH="/software:${PATH}"


RUN wget https://github.com/pachterlab/kallisto/releases/download/v0.44.0/kallisto_linux-v0.44.0.tar.gz && tar -xzf kallisto_linux-v0.44.0.tar.gz
ENV PATH="/software/kallisto_linux-v0.44.0:${PATH}"


FROM openjdk:8-jdk-alpine as build
COPY . /src
WORKDIR /src

RUN ./gradlew clean shadowJar

FROM base
RUN mkdir /app
COPY --from=build /src/build/*.jar /app/kallisto.jar
