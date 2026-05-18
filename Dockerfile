FROM tomcat:10.1

COPY PetHubProject.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080