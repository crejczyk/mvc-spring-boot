# mvc-spring-boot
[![GitHub license](https://img.shields.io/github/license/mashape/apistatus.svg)]()

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
