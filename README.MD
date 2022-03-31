# SureTime

### Reference Documentation

For further reference, please consider the following sections:

* [Spring Boot Tutorial - Build a Rest Api with MongoDB](https://www.youtube.com/watch?v=ssj0CGxv60k&t=457s&ab_channel=Amigoscode)
* [Project Lombok](https://projectlombok.org/)
* [Spring Security](https://octoperf.com/blog/2018/03/08/securing-rest-api-spring-security/#user-auth-uuid)
* [Spring Security - 2](https://www.bezkoder.com/spring-boot-jwt-auth-mongodb/)
* [Hexagonal Architecture](https://www.baeldung.com/hexagonal-architecture-ddd-spring)

### Guides

The following guides illustrate how to use some features concretely:

* [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

### Developers recommendations

Use a [IntelliJ IDEA Ultimate](https://www.jetbrains.com/lp/intellij-frameworks/)

Use a [API-DOCS](http://localhost:8080/swagger-ui/index.html)
        ```http://localhost:8080/swagger-ui/index.html```
#### Requirements

* [ Maven](https://maven.apache.org/)
    - [Windows](https://mkyong.com/maven/how-to-install-maven-in-windows/)
    - [Linux](https://maven.apache.org/install.html)
* [ Docker](https://www.docker.com/)

#### User manual

1. Run docker
    ````
    docker-compose up -d 
    ````

2. Run BookStore application
    ```
    mvn spring-boot:run
    ```
3. To turn on security in application.properties set this variable to true. 
    ```
    suretime.app.isSecureMode = true
   ```
4. Register to API:

  ```
    curl --location --request POST 'http://localhost:8080/api/auth/signup' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "user",
    "email": "user@gmail.com",
    "password": "password"
}'
  ```
5. Login to API:
  ```
    curl --location --request POST 'http://localhost:8080/api/auth/signin' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "username":"admintest",
    "password":"password"
    }'
  ```
6. If authorization is turn on you can make GET request. If you want to make any other request you have to provide authorization token in request header.
  ```
    curl --location --request GET 'http://localhost:8080/api/test/admin' \
    --header 'Authorization: Bearer token'
  ```
### Project requirements

3. CRUD + Spring Security z podziałem na role : admin -dodaje i usuwa  ksiazki z ksiegarni, user możne przeglądać.

3.5  CRUD + koszyk - dodawanie usuwanie książek przez Usera.

4. Zamówienia -  Dodanie funkcji które może wykonywać User - składanie zamówień na książki, zmiana statusu zamówienia przez admina.

4.5 Wygląd aplikacji Można użyć boostrapa / Własny bardziej rozbudowany projekt lub bardziej rozszerzony projekt z przykładu

5 Płatności - np. wysyłanie szczczegółów zamówienia na maila z danymi do przelewu lub podpięcie np. PayU

+ 0.5  za zadania - jeżeli  zostanie zrealizowany inny projekt na zaliczenie.

Projekt proszę wysłać jako link do githuba.

Projekt powinien zawierać opis funkcjonalności, skrypt do zbudowania bazy danych, oraz inne opisy sposobu uruchomienia jeżeli potrzeba.