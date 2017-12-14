############################################################
# Dockerfile to deploy war using tomcat with jre base image
############################################################
FROM tomcat:8.5-jre8

# Maintainer
MAINTAINER "sudheer veeravalli <veersudhir83@gmail.com>"

# Copy war file to tomcat webapps folder 
COPY devops-web-hackathon.war /usr/local/tomcat/webapps/

# Make port 8080 available to the world outside this container
EXPOSE 8080

CMD ["catalina.sh", "run"]