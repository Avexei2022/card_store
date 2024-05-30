package ru.gb.group5984.service.observer;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.gb.group5984.model.observer.NewProductEvent;
import ru.gb.group5984.model.storage.CardsStorage;
import ru.gb.group5984.model.users.User;
import ru.gb.group5984.service.db.UserDbService;
import ru.gb.group5984.service.integration.FileGateway;

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
     * Уведомить пользователей подписчиков о поступлении нового товара в продажу.
     * @param event событие - поступление нового товара в продажу.
     */
    @Override
    public void onApplicationEvent(NewProductEvent event) {
        CardsStorage cardsStorage = event.getCardsStorage();
        List<User> userList = userDbService.findAllUser();
        userList.stream().filter(User::getIsSubscribe)
                .forEach(user -> {
                    String fileName = user.getUsername().concat(".txt");
                    fileGateway.writeToFile(fileName, cardsStorage);
                });
    }

    /**
     * Возвращает, поддерживает ли этот прослушиватель асинхронное выполнение.
     * @return да/нет.
     */
    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
