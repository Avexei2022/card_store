# Интернет-магазин CardStore
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

