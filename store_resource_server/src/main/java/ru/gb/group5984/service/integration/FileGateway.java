package ru.gb.group5984.service.integration;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;


@MessagingGateway(defaultRequestChannel = "messageInputChannel")
public interface FileGateway {
    void  writeToFile(@Header(FileHeaders.FILENAME) String filename, Object object);

}
