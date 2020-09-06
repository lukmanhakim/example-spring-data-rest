[build]: https://img.shields.io/badge/build-success-success
# Spring Data Rest Example
 
![Build][build]

#### Technology

- Lang : `Java`
- Framework : `Spring Framework`
- Database : `H2 (Embedded)`

---

#### Requirements

- JDK 8
- Maven 3

---

#### Endpoints

| Name       | Endpoint                              |
| ---------- | ------------------------------------- |
| Swagger    | http://localhost:8080/swagger-ui/     |
| H2 Console | http://localhost:8080/db              |
| Health     | http://localhost:8080/actuator/health |

---

#### Run

```
$ mvn clean spring-boot:run
```

---

#### Build

* Jar
    ```
    $ mvn clean install
    ```

* Docker Image
    ```
    $ mvn spring-boot:build-image
    ```
---

#### Deploy

```
Lorem Ipsum
```

