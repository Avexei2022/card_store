package ru.gb.group5984.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;
import ru.gb.group5984.model.basket.CardInBasket;
import ru.gb.group5984.model.storage.CardsStorage;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Конфигуратор интеграции.
 */
@Configuration
public class IntegrationConfig {

    /**
     * Входной канал сообщений.
     * @return - новый экземпляр канала
     */
    @Bean
    public MessageChannel messageInputChannel() {
        return new DirectChannel();
    }

    /**
     * Выходной канал записи в файл.
     * @return - новый экземпляр канала.
     */
    @Bean
    public MessageChannel fileWriterChannel() {
        return new DirectChannel();
    }

    /**
     * Преобразователь данных объекта в текст сообщения.
     * @return преобразователь.
     */
    @Bean
    @Transformer(inputChannel = "messageInputChannel", outputChannel = "fileWriterChannel")
    public GenericTransformer<CardInBasket, String> messageTransformer() {
        return cardInBasket -> cardInBasket.getCard().getName() +
                " Дата и время продажи: " +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                " Количество: " +
                cardInBasket.getAmount() +
                " Цена за единицу: " +
                cardInBasket.getPrice() +
                " В корзине покупателя c: " +
                cardInBasket.getCreated();
    }

    /**
     * Преобразователь данных.
     * @return выходные данные.
     */
    @Bean
    @Transformer(inputChannel = "messageInputChannel", outputChannel = "fileWriterChannel")
    public GenericTransformer<CardsStorage, String> notifyUser() {
        return CardsStorage::toString;
    }

    /**
     * Сервис выходного канала.
     * В данном случае сервис записи в файл.
     * @return новый сервис.
     */
    @Bean
    @ServiceActivator(inputChannel = "fileWriterChannel")
    public FileWritingMessageHandler fileMessageHandler() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("messages/"));
        handler.setExpectReply(true);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);
        return handler;
    }
}
