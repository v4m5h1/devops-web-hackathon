# Commands for docker images and containers used

> To build a tomcat docker image for this application
1. Go to the folder containing the Dockerfile
2. Adjust the contents of the Dockerfile to deploy the application
3. Run the below command to create the image
```sh
$ docker build -t application_tomcat .
```
> **Note: Follow some naming convention while bringing up images and container. In this case,
we are using <application_name>_<server_name> (all in lower case)**
4. Verify if the image is created using the command
```sh
$ docker images
```
5. Now that the image is created, let's create the container which is the actual run time instance of the server_name
```sh
$ docker create -e TZ=IST -p 9991:8080 --name application_tomcat_1 application_tomcat:latest
```
> **Note: Remember to follow the same naming conventions**
6. The above command will create the container, but does not start it automatically. Use the below command to start it manually
```sh
$ docker start application_tomcat_1
```
7. In case, you want to create and start container in single command
```sh
$ docker run -dit -e TZ=IST -p 9991:8080 --name application_tomcat_1 application_tomcat:latest
```
8. To use the docker swarm in cluster mode, we will use the docker-compose.yml file. Basicall, we will bring up a cluster environment so it does load balancing.

We will follow the instructions and commands available in the docker docs link below.
https://docs.docker.com/engine/swarm/stack-deploy/#set-up-a-docker-registry

9. Using the docker compose, we will first build an image **127.0.0.1:5000/application-dev-image:latest**,
   and then use this image to bring up 3 tomcat servers as a cluster
