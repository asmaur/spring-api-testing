# Spring API Testing With AI

![Java](https://img.shields.io/badge/Java-%233776ab.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring boot](https://img.shields.io/badge/Spring-boot-%2325A162.svg?style=for-the-badge&logo=springboot&logoColor=white)
![Junit5](https://img.shields.io/badge/junit-%2325A162.svg?style=for-the-badge&logo=junit5&logoColor=white)


This project is an API Unit Testing Course using **Junit5 and Mockito**

This project was build as API Testing Course with Junit5 and Mockito and may be available on my [YouTube Channel](https://www.youtube.com/@wanubit), to demonstrate how to unit test an API with ChatGPT.

## Requirements

The list of tools required to build and run the project:

* Open JDK 17
* Apache Maven 3.8

## Steps to Set up

**1. Clone the application**

```bash
git@github.com:asmaur/spring-boot-api-tutorial.git
```

### 2. Start a database
The default database used in the course is the H2 an in _**memory database**_. Fell free to choose any DB of your choice. The easiest way to set up your database is to modify the configuration file `application.yml`.

### 3. Run the Application
You can run the application using:

```bash
mvn spring-boot:run
```

The application will start on port `8080` so you can send a sample request to `http://localhost:8080/courses` to see if you're up and running.


## Application Architecture

```
 ╭┄┄┄┄┄┄┄╮      ┌──────────┐      ┌──────────┐
 ┆   ☁   ┆  ←→  │    ☕     │  ←→ │    💾     │
 ┆  Web  ┆ HTTP │  Spring  │      │ Database │
 ╰┄┄┄┄┄┄┄╯      │  Service │      └──────────┘
                └──────────┘
                    
```

The sample application is almost as easy as it gets. It stores `Person`s in an in-memory database (using _Spring Data_) and provides a _REST_ interface with some endpoints:

## Explore Rest APIs

The app defines following CRUD APIs.
### Course Endpoints

* `GET /api/v1/courses` get all courses.


* `POST /api/v1/courses` create new course.

* `GET /api/v1/courses/{id}` get course by id.

* `PUT /api/v1/courses/` update course.

* `DELETE /api/v1/courses/{id}` delete courses.

### Review Endpoints

* `POST /api/v1/reviews` create new course.

* `GET /api/v1/reviews/{id}` get course by id.

* `PUT /api/v1/reviews/` update course.

* `DELETE /api/v1/reviews/{id}` delete courses.

### Internal Architecture
The **Spring Service** itself has a pretty common internal architecture:

* `Controller` classes provide _REST_ endpoints and deal with _HTTP_ requests and responses
* `Repository` classes interface with the _database_ and take care of writing and reading data to/from persistent storage



  ```
  Request  ┌────────── Spring Service ───────────┐
   ─────────→ ┌─────────────┐    ┌─────────────┐ │   ┌─────────────┐
   ←───────── │  Controller │ ←→ │  Repository │←──→ │  Database   │
  Response │  └─────────────┘    └─────────────┘ │   └─────────────┘
           │                                     │
           │                                     │
           │                                     │
           │                                     │
           └─────────────────────────────────────┘
                                      
  ```  

## Test Layers
Testing have different level. In this course we are focusing bottom layer testing: Unit

```
      ╱╲
  End-to-End
    ╱────╲
   ╱ Inte-╲
  ╱ gration╲
 ╱──────────╲
╱   Unit     ╲
──────────────
```

The base of this course is made up of unit tests. They should make the biggest part of your automated test suite.

The next layer, integration tests, test all places where your application serializes or deserializes data. Your service's REST API, Repositories or calling third-party services are good examples. This codebase contains example for all of these tests.

```
 ╭┄┄┄┄┄┄┄╮      ┌──────────┐      ┌──────────┐
 ┆   ☁   ┆ ←→  │    ☕     │  ←→  │    💾     │
 ┆  Web  ┆      │  Spring  │      │ Database │
 ╰┄┄┄┄┄┄┄╯      │  Service │      └──────────┘
                └──────────┘

  │    Controller     │      Repository      │
  └─── Integration ───┴──── Integration ─────┘

  │                                          │
  └────────────── Acceptance ────────────────┘               
```

```

```

## Tools
You can find lots of different tools, frameworks and libraries being used in the different examples:

* **Spring Boot**: application framework
* **JUnit**: test runner
* **Hamcrest Matchers**: assertions
* **Mockito**: test doubles (mocks, stubs)
* **MockMVC**: testing Spring MVC controllers

All test can be found under test package.
```
src/test/java/com/wanubit/springtesting
```
