Requirements : Apache tomcat 9, MySQL database and jdk12
Steps to deploy project in Tomcat server
1.  Copy WebStoriesApp.war from https://github.com/SwityG/JavaRepository/tree/main/WebStoriesApp/target into webapps folder of Apache tomcat.
2.  Copy 'images' folder from https://github.com/SwityG/JavaRepository/tree/main/WebStoriesApp/src/main/webapp into 'webapps' folder of Apache tomcat.
3.  Download mysql-connector.jar file and place in 'lib' folder of Apache tomcat.
4.  For database configuration, make changes in web.xml file (change username and password for your local system.)
5.  Run sql script 'webStrories.sql' located in https://github.com/SwityG/JavaRepository/tree/main/WebStoriesApp/sql folder on database.
6.  Start tomcat server, when ready access website using   'http://hostname:portunumber/WebStoriesApp'
