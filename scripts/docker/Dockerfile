#
# Dockerfile to create an image with maven and cloud foundry cli installed
# > docker image build -t maven-cf:3.5.4-alpine .
#
# Use an lightweight maven runtime as a parent image
FROM maven:3.5.4-alpine

# Set the working directory to /app
WORKDIR /app

# Copy the Cloud Foundry cli archive into the container at /app
ADD . /app

# Put the cf command in a dir on the path
RUN tar -xvf cf-cli_6.39.1_linux_x86-64.tgz -C /usr/local/bin
