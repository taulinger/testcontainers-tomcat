Testcontainers-Tomcat
========

[![Build Status](https://github.com/taulinger/ccrypt-j-cli/workflows/Java%20CI/badge.svg)](https://github.com/taulinger/ccrypt-j-cli/actions/workflows/java.yml?query=branch%3Amain)

Setup for IT/E2E testing by deploying a war file to a Tomcat running with Testcontainers.


## Usage

Prerequisites
- JDK 17 or later (https://adoptium.net/)
- Maven 3.6 or later
- Docker Engine

Steps
- Clone the Github project:
```
git clone https://github.com/taulinger/testcontainers-tomcat.git
```
- Change to the git repository and run Maven:
```
mvn test
```