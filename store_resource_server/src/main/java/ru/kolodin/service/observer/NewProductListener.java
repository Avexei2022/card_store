package ru.kolodin.service.observer;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.kolodin.model.observer.NewProductEvent;
import ru.kolodin.model.storage.CardsStorage;
import ru.kolodin.model.users.Buyer;
import ru.kolodin.service.db.UserDbService;
import ru.kolodin.service.integration.FileGateway;
import ru.kolodin.service.kafka.KafkaProducerService;

import java.util.List;

/**
 * Слушатель поступления нового товара в продажу с подготовкой уведомления подписчиков.
 */
@Component
@AllArgsConstructor
public class NewProductListener implements ApplicationListener<NewProductEvent> {

    /**
     * Сервис пользователей.
     */
    private final UserDbService userDbService;

    /**
     * Интерфейс интеграции.
     */
    private final FileGateway fileGateway;

    /**
     * Сервис  Кафки
     */
    private final KafkaProducerService kafkaProducerService;

    /**
     * Уведомить пользователей подписчиков о поступлении нового товара в продажу.
     * @param event событие - поступление нового товара в продажу.
     */
    @Override
    public void onApplicationEvent(NewProductEvent event) {
        CardsStorage cardsStorage = event.getCardsStorage();
        List<Buyer> buyerList = userDbService.findAllBuyer();
        buyerList.stream().filter(Buyer::getIsSubscribe)
                .forEach(buyer -> {
                    String fileName = buyer.getUsername().concat(".txt");
                    fileGateway.writeToFile(fileName, cardsStorage);
                    kafkaProducerService.sendMessage(createMailMessage(cardsStorage, buyer), "StoreResourceServer.Email");
                });
    }

    /**
     * Поддерживает этот прослушиватель асинхронное выполнение или нет.
     * @return да/нет.
     */
    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }

    /**
     * Создать текст сообщения о поступлении нового товара
     * @param cardsStorage товар
     * @param buyer покупатель
     * @return текст сообщения
     */
    private String createMailMessage (CardsStorage cardsStorage, Buyer buyer) {
        return "Email: " + buyer.getEmail()
                + " User: " +  buyer.getUsername()
                + " NewCards: Name=" + cardsStorage.getCard().getName()
                + " Price=" + cardsStorage.getPrice();
    }
}
