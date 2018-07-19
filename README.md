# mvc-spring-boot

[![Build Status](https://travis-ci.org/crejczyk/mvc-spring-boot.svg?branch=master)](https://travis-ci.org/crejczyk/mvc-spring-boot)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=com.softmill.springboot%3Amvc-spring-boot&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.softmill.springboot%3Amvc-spring-boot)
[![Coverage Status](https://sonarcloud.io/api/project_badges/measure?project=com.softmill.springboot%3Amvc-spring-boot&metric=coverage)](https://sonarcloud.io/dashboard?id=com.softmill.springboot%3Amvc-spring-boot)

## Requirements

1. Java - 1.8.x

2. Maven - 3.x.x

3. MongoDB - 3.x.x

## Run MONGO using Docker for Windows
- `docker volume create --name=mongodata` 
- `docker run -d -p 27017:27017 -v mongodata:/data/db mongo`

```bash
mvn spring-boot:run
```
The server will start at <http://localhost:8080>.

## Code coverage report

```bash
mvn jacoco:report
```

## Exploring the Rest API

The application defines following REST APIs

```
1. GET /tweets - Get All Tweets

2. POST /tweets - Create a new Tweet

3. GET /tweets/{id} - Retrieve a Tweet by Id

4. PUT /tweets/{id} - Update a Tweet

5. DELETE /tweets/{id} - Delete a Tweet

6. GET /stream/tweets - Stream tweets to the browser
```
