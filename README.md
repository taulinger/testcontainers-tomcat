Testcontainers and Tomcat
========

Setup for IT/E2E testing by deploying a WAR file to a Tomcat running with Testcontainers.


## Usage

Prerequisites
- JDK 17 or later (https://adoptium.net/)
- Docker Engine

Steps
- Clone the Github project:
```
git clone https://github.com/taulinger/testcontainers-tomcat.git
```
- Change to the git repository and run Maven Wrapper:
    
```
./mvnw clean verify
```
or on Windows
```
mvnw.cmd clean verify
```