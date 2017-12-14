############################################################
# Dockerfile to deploy jar using alpine image
############################################################
FROM anapsix/alpine-java

# Maintainer
MAINTAINER "sudheer veeravalli <veersudhir83@gmail.com>"

# Copy war file to tomcat webapps folder
COPY devops-web-hackathon.jar /home/devops-web-hackathon.jar

CMD ["java","-jar","/home/devops-web-hackathon.jar"]