# cfpushdir
Example for how to push to the Cloud Foundry Java buildpack using a jar or directory structure. The commands to use, and directory structures are described in the car/pom.xml file.

Requirements:
- Jenkins started with Blue Ocean plugins installed (pipelines)
- Jenkins Credentials Binding plugin installed
- Jenkins credentials (username/password type) added for Cloud Foundry login
- Docker build ran from the ./Docker directory to create the Jenkins build node image
