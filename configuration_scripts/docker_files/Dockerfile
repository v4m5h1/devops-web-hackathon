############################################################
# Dockerfile to build apache tomcat container images
# Based on official tomcat latest image
############################################################
FROM tomcat:8.5-jre8

# Maintainer
MAINTAINER "sudheer veeravalli <veersudhir83@gmail.com>"

# Add tar file to tomcat webapps folder which will explode as folder
ADD devops-web-hackathon.jar /usr/local/tomcat/webapps/

# Make port 8080 available to the world outside this container
EXPOSE 8080

CMD ["catalina.sh", "run"]
