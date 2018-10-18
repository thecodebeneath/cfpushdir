# cfpushdir
Example for how to push to the Cloud Foundry Java buildpack using a either a self-contained jar or directory structure. The commands to use, and directory structures are described in the car/pom.xml file.

# Prerequisites:
- Jenkins started with Blue Ocean plugins installed (pipelines)
- Jenkins Credentials Binding plugin installed
- Jenkins credentials (username/password type) added for Cloud Foundry login
- Docker build ran from the ./Docker directory to create the Jenkins build node image

# Project
The Java project is simply two jars containing a CarService and an Engine class. This is enough to show how the uber-jar is constructed by the Maven Shade plugin and dependecy jars are declared in the pom.xml files.

# Web Service
When started, there is only one REST HTTP GET service:
- Local: http://localhost:8080/cars
- Pivotal Cloud, depending on which manifest.yml is used:
  -- https://carservice-from-jarpath-yourrandom-route.cfapps.io/cars
  -- https://carservice-from-dirpath-yourrandom-route.cfapps.io/cars
  
# Cloud Foundry Manifests
- Doing a "cf push" using the "manifest-from-jarpath.yml" is the preferred way to deploy to Cloud Foundry that assumes the Java application is a self-contained jar file.
- Doing a "cf push" using the "manifest-from-dirpath.yml" shows how to deploy an application that cannot be self contained, for example has a custom start script or external files it needs to load (provided in the uploaded directories).

# Jenkins Pipeline
To run a Jenkins pipeline script that builds and pushes the application to Cloud Foundry:
- Create Docker image that Jenkins will use as the build agent. It includes the JDK, Maven and the CF CLI application by:
  -- cd docker
  -- docker image build -t maven-cf:3.5.4-alpine .- Create a Pipeline job and point it at the git repo and the ./Jenkinsfile
- Create a new Pipeline job and point the Pipeline location the git repo and the Jenkinsfile
- Create a Jenkins credential to hold your Pivotal Cloud username and password, uniquely named with an id of "cf-credentials"
