# Spring Boot ShoppingMall

![image](https://user-images.githubusercontent.com/5029567/155296107-9b086dec-e61c-4cce-8e4d-6fdcb97f037d.png)
[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2FHoon9901%2Fhoon_shoppingmall&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)

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
- Configure database (application.yml)
  ```yml
  spring.datasource.url=jdbc:postgresql://localhost:5432/shop
  spring.datasource.username=<YOUR_USERNAME>
  spring.datasource.password=<YOUR_PASSWORD>
  ```

- Run Porject
  ```bash
    > ./gradlew bootRun    
  ```
