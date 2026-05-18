FROM tomcat:10.1

COPY PetHub.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080