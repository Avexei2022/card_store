package ru.gb.group5984.service.integration;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * Интерфейс интеграции.
 * В данном случае - уведомление подписчиков в виде записи информации в файл с именем подписчика.
 */
@MessagingGateway(defaultRequestChannel = "messageInputChannel")
public interface FileGateway {
    /**
     * Запись необходимой информации в файл.
     * @param filename имя файла - имя подписчика.
     * @param object уведомление.
     */
    void  writeToFile(@Header(FileHeaders.FILENAME) String filename, Object object);

}
