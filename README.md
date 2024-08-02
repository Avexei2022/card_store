# [Интернет-магазин CardStore](https://drive.google.com/file/d/1hx_WERaDS9MlTZlK4y6nK1ED8fStLETA/view?usp=drive_link)  
## на базе микросервисной архитектуры с использованием Spring Framework  
### Стек: Spring Boot, Spring Cloud, Spring Web MVC, Spring Data JPA, Spring Security, Spring Integration, Spring AOP, PostgreSQL, Thymeleaf, Docker, Lombok, Maven, GSON, Netflix Eureka, Jackson, JJWT, Prometheus, Micrometer, Grafana, HTML, CSS, WireShark.

## Сервисы:  
1. store_resource_server..StoreServerApp - сервис ресурсов магазина;  
2. store_warehouse..WarehouseApp - веб-сервис склада магазина;  
3. storefront.. StoreFrontApp - веб-сервис магазина;  
4. bank_resource_service..BankServerApp - сервис ресурсов банка;  
5. bank..BankApp - веб-сервер банка;  
6. configserver..ConfigServerApp - сервер конфигурации - запускать первым;  
7. api_gateway..ApiGatewayApp;  
8. EurekaServer..EurekaServerApp.


# Порядок запуска  
1. Установить PostgreSQL на порту:5432  
2. Создать базу данных с именем db_trading_floor username: admin password: admin  
3. Можно с другими настройками, но тогда внести коррективы в application.yaml сервисов ресурсов магазина и банка  
4. Запустить сервер конфигурации  
5. Запустить сервер Eureka и GateWay  
6. Запустить все остальные сервисы  
7. Зайти в банк на порт 8081 или через GateWay на порту 8765/bank  
![Начальная страница](https://drive.google.com/file/d/1CjMLcFu47pVYL2AW15K8DlmRoYxrXnkj/view?usp=drive_link) 

8. В банке авторизоваться с логином bank и паролем bank 
9. В банке добавить кандидатов, затем перевести их в клиенты (Здесь и далее - клики по карточкам)  
10. Обязательным клиентом является Rick Sanchez - на нем счет магазина  
11. Зайти на склад через порт 8082 или через GateWay на порту 8765/storage  
12. На складе авторизоваться с логином admin и паролем admin  
13. Закупить товар и выставить его на продажу  
14. Зайти в магазин через порт 8083 или через GateWay на порту 8765/storefront   
15. В магазине зарегистрировать покупателя, желательно из списка клиентов банка  
16. Запомнить логин и пароль - они одинаковые, можно и скопировать  
17. Далее закупать товар и оплачивать его, если конечно открыт счет в банке.

# Ссылки  
1. [Полное описание проекта](https://drive.google.com/file/d/1e7FoFYp3YGuEZ0-s7DPb6noMkWPOVL_Q/view?usp=drive_link)
2. Диаграммы классов:
   - [сервис ресурсов магазин](https://drive.google.com/file/d/1jURqDFh80Fh0lrKHB6NNRHmc31Re41qN/view?usp=drive_link)  
   - [веб-сервис склада магазина](https://drive.google.com/file/d/1d7X-54NRjyXNeMDuGC5A93oo1ylwRlk7/view?usp=drive_link)  
   - [веб-сервис магазина](https://drive.google.com/file/d/1jte1UOmYdhyzjmUY4FqQtA_kCkAN0dfi/view?usp=drive_link)  
   - [сервис ресурсов банка](https://drive.google.com/file/d/1cpLWTDvQFdyNK0AyTIdxJrgPl67AajL-/view?usp=drive_link)  
   - [веб-сервер банка](https://drive.google.com/file/d/1b3wJMeqix1aFLiDWX71tdXSikr6EjNmq/view?usp=drive_link)
     
