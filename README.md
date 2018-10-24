# cfpushdir
Example for how to push to the Cloud Foundry Java buildpack using a either a self-contained jar or directory structure. The commands to use, and directory structures are described in the car/pom.xml file.

# Prerequisites:
- Jenkins started with Blue Ocean plugins installed (pipelines)
- Jenkins Credentials Binding plugin installed
- Jenkins credentials (username/password type) added for Cloud Foundry login
- Docker build ran from the ./Docker directory to create the Jenkins build node image

# Project
The Java project is simply two jars containing a CarService and an Engine class. This is enough to show how the uber-jar is constructed by the Maven Shade plugin and dependecy jars are declared in the pom.xml files.

The project intentionally does not use Spring Boot dependencies so as to keep things simple and to mirror a non-Spring Boot web application being pushed to the Java buildpack.

# Web Service
When started, there is only one REST HTTP GET service:
- Local:
  - ```http://localhost:8080/cars```
- Pivotal Cloud, depending on which manifest.yml is used:
  - ```https://carservice-from-jarpath-yourrandom-route.cfapps.io/cars```
  - ```https://carservice-from-dirpath-yourrandom-route.cfapps.io/cars```
  
# Cloud Foundry Manifests
- Doing a "cf push" using the ```manifest-from-jarpath.yml``` is the preferred way to deploy to Cloud Foundry that assumes the Java application is a self-contained jar file.
- Doing a "cf push" using the ```manifest-from-dirpath.yml``` shows how to deploy an application that cannot be self contained, for example has a custom start script or external files it needs to load (provided in the uploaded directories).

# Jenkins Pipeline
## To run a Jenkins pipeline script that builds and pushes the application to Cloud Foundry:
- Create Docker image that Jenkins will use as the build agent. It includes the JDK, Maven and the CF CLI application by:
  - ```cd docker```
  - ```docker image build -t maven-cf:3.5.4-alpine .```
- Start Jenkins as a Docker container using the "jenkinsci/blueocean" image. Reference these instructions to get started: https://jenkins.io/doc/tutorials/create-a-pipeline-in-blue-ocean/
- Create a new Pipeline job and point it at the git repo and the ```Jenkinsfile```
- Create a Jenkins credential to hold your Pivotal Cloud username and password, uniquely named with an id of ```cf-credentials```

## To run a Jenkins pipeline script that builds and creates a Docker image containing the built application:
- Run the Jenkins blueocean container. Note the user "root" and the volume mount to the docker.sock file:
```docker run --rm -u root -p 8080:8080 -v jenkins-data2:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock -v /tmp:/home jenkinsci/blueocean```
- Use the Jenkins plugin admin to automatically install a version of Maven and label it "M3"
- Create a new Pipeline job and point it at the git repo and the ```Jenkinsfile```
  - Note that this Jenkins file tells the build to run the Jenkins master, and not on a ```agent { docker ... }``` because the script needs access to the docker client and docker daemon, which a docker agent would not be able to do.
