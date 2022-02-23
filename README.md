# Spring Boot ShoppingMall

![image](https://user-images.githubusercontent.com/5029567/155296107-9b086dec-e61c-4cce-8e4d-6fdcb97f037d.png)


## Tech Stack
```
- Spring Boot 2.6.2
- Spring Data JPA
- Spring Security
- PostgreSQL
- H2
- QueryDSL
- Thymeleaf
```

## Setting Up
- Create PostgreSQL database
  ```bash
    psql > CREATE DATABASE shop;
  ```
- Configure database
  ```yml
  spring.datasource.url=jdbc:postgresql://localhost:5432/shop
  spring.datasource.username=<YOUR_USERNAME>
  spring.datasource.password=<YOUR_PASSWORD>
  ```

- Run Porject
  ```bash
    > ./gradlew bootRun    
  ```
