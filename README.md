# [Интернет-магазин CardStore](https://drive.google.com/file/d/1e7FoFYp3YGuEZ0-s7DPb6noMkWPOVL_Q/view?usp=drive_link)  
## на базе микросервисной архитектуры с использованием Spring Framework  
#### Стек: Spring Boot, Spring Cloud, Spring Web MVC, Spring Data JPA, Spring Security, Spring Integration, Spring AOP, PostgreSQL, Thymeleaf, Docker, Lombok, Maven, GSON, Netflix Eureka, Jackson, JJWT, Prometheus, Micrometer, Grafana, Apache JMeter, HTML, CSS, WireShark.


## Легенда проекта  

Данный проект является эмуляцией интернет-магазина. В качестве поставщика товаров
выбран ресурс «The Rick and Morty API», расположенный в интернете по адресу
https://rickandmortyapi.com/. Данный ресурс является RESTful и GraphQL API, основанном на
телевизионном шоу Rick and Morty. Он предоставляет доступ к данным о сотнях персонажей,
изображений, локаций и эпизодов. API заполнен канонической информацией, как она
представлена в шоу. С помощью API разработчики могут создавать новые приложения.
По выбранной легенде, карточки персонажей являются товаром магазина, поэтому
название интернет-магазина “CardStore”. Одновременно, данные персонажа в его карточке
можно считать паспортными данными. Имя персонажа является уникальным и может быть
использовано в качестве логина и пароля для регистрации пользователя в магазине и при
открытии счета в банке. В этой связи, персонажи шоу Rick and Morty, по легенде данного
проекта, являются покупателями товаров в интернет-магазине, а также являются клиентами
банка. Пользовательский интерфейс интернет-магазина максимально приближен к дизайну
ресурса «The Rick and Morty API».

## Сервисы:  
1. store_resource_server..StoreServerApp - сервис ресурсов магазина;  
2. store_warehouse..WarehouseApp - веб-сервис склада магазина;  
3. storefront.. StoreFrontApp - веб-сервис магазина;  
4. bank_resource_service..BankServerApp - сервис ресурсов банка;  
5. bank..BankApp - веб-сервер банка;  
6. configserver..ConfigServerApp - сервер конфигурации - запускать первым;  
7. api_gateway..ApiGatewayApp;  
8. EurekaServer..EurekaServerApp.

![Конфигурация](https://drive.google.com/thumbnail?id=1imkiMESQzU4djbiz2SPEh06T-wrtKnaq&sz=s800)  


## Порядок запуска  
1. Установить PostgreSQL на порту:5432  
2. Создать базу данных с именем db_trading_floor username: admin password: admin  
3. Можно с другими настройками, но тогда внести коррективы в application.yaml сервисов ресурсов магазина и банка  
4. Запустить сервер конфигурации  
5. Запустить сервер Eureka и GateWay  
6. Запустить все остальные сервисы  

## Порядок работы  

1. Зайти в банк на порт 8081 или через GateWay на порту 8765/bank  


   ![Начальная страница Банка](https://drive.google.com/thumbnail?id=1hx_WERaDS9MlTZlK4y6nK1ED8fStLETA&sz=s800)  
  
  
2. При нажатии на кнопку LOGIN откроется окно авторизации, в котором авторизоваться с логином bank и паролем bank 
  

3. В банке добавить кандидатов, затем перевести их в клиенты (Здесь и далее - клики по карточкам)  


   ![Посетители банка Банка](https://drive.google.com/thumbnail?id=1qMYQMrwopRYQP7S_z9-CUAn_JCDgyhPj&sz=s800)  

   ![Кандидаты в клиенты Банка](https://drive.google.com/thumbnail?id=1JkdOunCA-hQmypdHcjCvd8NsM9-9knN3&sz=s800)  

   ![Клиенты Банка](https://drive.google.com/thumbnail?id=10Few37rJ0FSBM0wr1grc6qEGWFRlxCP2&sz=s800)  
  

 `ВАЖНО!`  Обязательным клиентом является Rick Sanchez - на нем счет магазина.  
   В данной версии, при добавлении клиента на его счет кладется 1001.00 у.е.  
4. Зайти на склад через порт 8082 или через GateWay на порту 8765/storage  
  
  
   ![Начальная страница Склада магазина](https://drive.google.com/thumbnail?id=1OC3OhikCCZobXyTjPSoToOyweYK-S0N1&sz=s800)  
  

5. При нажатии на кнопку LOGIN откроется окно авторизации, в котором авторизоваться с логином admin и паролем admin  


6. Закупить товар и выставить его на продажу (клики по карточкам).  


   ![Страница закупки товара у поставщика - Склад магазина](https://drive.google.com/thumbnail?id=1plNVf16TRUJb6tSTW3Nfh9fuQVLbrnlf&sz=s800)  

   ![Страница товара на складе - Склад магазина](https://drive.google.com/thumbnail?id=1zqFVDsp-xlhYfmn-l7RT6wyebuNScuUx&sz=s800)  

   ![Товар в продаже - Склад магазина](https://drive.google.com/thumbnail?id=1c-oev0-_Gu7n-m98DBp5Gvsx3CqAqotU&sz=s800)  


   В данной версии количество и стоимость товара установлены по умолчанию (100 и 19.99 соответственно)  
      
7. Зайти в магазин через порт 8083 или через GateWay на порту 8765/storefront   


   ![Начальная страница Магазина](https://drive.google.com/thumbnail?id=1nfjwkrHTnAVOps2UJYM2W_9mjYlask-M&sz=s800)

8. В магазине зарегистрировать покупателя (клики на карточки), желательно из списка клиентов банка  


   ![Страница регистрации покупателя](https://drive.google.com/thumbnail?id=1zRS6p5fP6IIi_5kK5KAGIAYAmNT8xEHE&sz=s800)  


   В данной версии покупатели эмулируются, т.е. покупателями являются персонажи Rick&Morty шоу.
   Ограничений по количеству зарегистрированных покупателей нет, но если у покупателя отсутствует счет в банке,
   то при оплате товара будет выведена соответствующая информация.  

![Страница сообщения об отсутствии счета в банке](https://drive.google.com/thumbnail?id=1Tb7yIexL9ENTR3mafHirj7ZxnQoTV8K5&sz=s800)  


   После выбора персонажа в качестве покупателя будет выведена информация о логине и пароле, которые можно скопировать или запомнить. 
   Запомнить легко, т.к. логин соответствует полному имени персонажа через нижнее подчеркивание. Пароль совпадает с логином. 

![Страница сообщения об успешной регистрации](https://drive.google.com/thumbnail?id=1OvxcD6B0oMCdmMyEAJNfPlN60fMkI3e1&sz=s800)

9. После регистрации покупателя, следует авторизоваться через кнопку LOGIN 

![Страница авторизации](https://drive.google.com/thumbnail?id=1bfbSIUSIulUdWdc6ZuyTduemqVbgvjQV&sz=s800)

10. Далее закупать товар, складывая его в корзину, и оплачивать стоимость всего товара в корзине, если, конечно, открыт счет в банке.

![Страница товара](https://drive.google.com/thumbnail?id=1FfHdvkzSikYzm8h1FE1vxQln2aI_plaH&sz=s800)  

![Страница корзинв](https://drive.google.com/thumbnail?id=1Dp1_t53WLPoUOnehBbMruR6806MZN_Zj&sz=s800)

![Страница сообщений о результате оплаты товара](https://drive.google.com/thumbnail?id=1xTS1vKImqIzlmvsqy3nHq3r_z2Q0S9SJ&sz=s800)  

   При недостатке средств на счете, или при отсутствии счета в банке, покупатель получит соответствующее сообщение.  


## Ссылки  
1. [Полное описание проекта](https://drive.google.com/file/d/1e7FoFYp3YGuEZ0-s7DPb6noMkWPOVL_Q/view?usp=drive_link)
2. Диаграммы классов:
   - [сервис ресурсов магазин](https://drive.google.com/file/d/1jURqDFh80Fh0lrKHB6NNRHmc31Re41qN/view?usp=drive_link)  
   - [веб-сервис склада магазина](https://drive.google.com/file/d/1d7X-54NRjyXNeMDuGC5A93oo1ylwRlk7/view?usp=drive_link)  
   - [веб-сервис магазина](https://drive.google.com/file/d/1jte1UOmYdhyzjmUY4FqQtA_kCkAN0dfi/view?usp=drive_link)  
   - [сервис ресурсов банка](https://drive.google.com/file/d/1cpLWTDvQFdyNK0AyTIdxJrgPl67AajL-/view?usp=drive_link)  
   - [веб-сервер банка](https://drive.google.com/file/d/1b3wJMeqix1aFLiDWX71tdXSikr6EjNmq/view?usp=drive_link)


## Мониторинг  

Одной из задач проекта является задача создать ресурс для обеспечения владельца интернет-магазина
и его персонала возможности получать в реальном времени всю необходимую информацию о ходе продаж 
и о работе сервиса магазина в целом.
В качестве дополнительной опции такая же задача поставлена и сервису ресурсов банка.  
Для решения данной задачи к сервису ресурсов магазина и к сервису ресурсов банка добавлен модуль Spring Boot Actuator,
который позволяет мониторить приложение Spring Boot. 
Он предоставляет набор встроенных конечных точек, которые показывают различную информацию о приложении, 
включая статус здоровья, метрики приложения, информацию об окружающей среде и т. д..  
Особенности Spring Boot Actuator состоит в лёгкости интеграции с внешними системами мониторинга,
такими как Prometheus, Graphite, DataDog, New Relic и т. д.,
а также в возможности использования JMX или HTTP-конечных точек для управления
и мониторинга приложения в производственной среде.  
Для обеспечения мониторинга и оповещения об изменениях использована библиотека Micrometer 
и система мониторинга с открытым исходным кодом Prometheus.  
В качестве пользовательских метрик в сервисе ресурсов магазина использован счетчик товаров,
добавленных на склад и счетчик товаров добавленных в корзины покупателей. В качестве пользовательской метрики в 
сервисе ресурсов банка использован счетчик положительных транзакций.  
После запуска Prometheus и при переходе в интернет-браузере по его настроенному адресу
будет отображаться информация о доступных ресурсах.   

![Prometheus](https://drive.google.com/thumbnail?id=1NP2z56QQCMHZFcXWTOsEAGFqIWl68BTd&sz=s800)


Для визуализации, мониторинга и анализа данных предложена платформа Grafana. 
Данная платформа позволяет пользователям создавать дашборды с панелями,
каждая из которых отображает определённые показатели в течение установленного периода времени.
Пример дашборда с пользовательскими метриками, настроенными выше:

![Grafana](https://drive.google.com/thumbnail?id=1ZcZskus0RWP66EELB-YaLIAqUaETBLNP&sz=s800)


## Интеграционные решения

В проекте использован Spring Integration для сохранения информации о покупках пользователей. 
По замыслу, информация о покупках должна храниться в файлах с именем покупателя.
В результате, каждый раз после оплаты товара, в файл с именем пользователя будет добавлена строка
о проданном товаре. Файлы сохраняются в папке messages.  
Пример сохраненных файлов и содержимого одного из них:


![Интеграция](https://drive.google.com/thumbnail?id=13hk7IP_0zYQvpYoutHB-02yM4BDmUq02&sz=s800)


## Наблюдатель (Observer)

Observer в проекте использован для уведомления покупателей о поступлении нового товара, 
в случае если покупатель подписан на данную рассылку.
По легенде данного проекта уведомления поступают в виде их записи в файл с именем покупателя.


![Интеграция](https://drive.google.com/thumbnail?id=1Qil56U-8n7HnBvMsy5wMVPaugEfDr-XA&sz=s800)


## Spring AOP

В данном проекте Spring AOP используется для целей логирования. Его преимуществом 
является отсутствие необходимости добавлять логику в каждый метод.  
Во всех сервисах проекта добавлен пакет aspect, в котором создан аспект и аннотация
для регистрации действий пользователя и вывода их в консоль.
При вызове метода, который помечен данной аннотацией, в консоль будет выведена информация о наименовании метода, 
его аргументах и времени выполнения метода.  
Пример информации, выведенной в консоль:


![Интеграция](https://drive.google.com/thumbnail?id=1A5W1DzLmxn4KTjhTi0OzCYusEFiZdxO4&sz=s800)  


## Тестирование

Тестирование является важной и обязательной составляющей в разработке программного обеспечения. 
Для того, чтобы убедиться в правильности работы приложений и обнаружить ошибки
в сервисы ресурсов магазина и банка были добавлены модульные и интеграционные тесты. 
В сервис ресурсов магазина добавлены модульный и интеграционный тесты перемещения товара с полки в корзину покупателя.
В данных тестах проверяется корректность подсчёта товара на складе и в корзине покупателя 
после переноса товара в корзину, а также в модульном тесте проверяется работа исключений при отсутствии товара в продаже.
Результаты работы тестов:

![Модульный тест](https://drive.google.com/thumbnail?id=1oi35Qxllw4igOUPCy1D2thzc4DoGs_bD&sz=s600)

![Интеграционный тест](https://drive.google.com/thumbnail?id=122f2Wl147IQ7_34a_x0w21bBBVt0WhPo&sz=s600)

В сервис ресурсов банка добавлены модульный и интеграционный тесты перевода денежных средств.
В данных тестах проверяется корректность списания и начисления денежных средств на счетах клиентов банка,
а также в модульном тесте проверяется работа исключений при отсутствии лицевого счета клиента 
или недостатке средств на счете.  

Для выполнения требований к производительности системы, проведено нагрузочное тестирование сервиса ресурсов магазина 
и сервиса ресурсов банка с использованием Apache JMeter.   
Для тестирования сервиса ресурсов магазина выбран запрос покупателей первой страницы из 20 товаров,
выставленных на продажу. Была представлена нагрузка в 1000 запросов за 60 секунд. Для тестирования сервиса ресурсов банка
выбрано проведение транзакций. Была представлена нагрузка в 1000 транзакций за 60 секунд. 

![Нагрузочное тестирование магазина](https://drive.google.com/thumbnail?id=15-3T41KI0bw6qb-mK2KNiLXbBle6BURN&sz=s800)  

![Нагрузочное тестирование banka](https://drive.google.com/thumbnail?id=1moVPvZAl5gJ1L8iPE90yk-v3KF_QRs0_&sz=s800)

Результаты нагрузочного тестирования показали, что сервисы ресурсов магазина и банка 
соответствует требованиям производительности и способны справиться с пиковыми нагрузками.
