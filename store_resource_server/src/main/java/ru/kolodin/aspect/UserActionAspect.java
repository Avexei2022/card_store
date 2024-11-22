package ru.kolodin.aspect;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kolodin.service.kafka.KafkaProducerService;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Аспект для регистрации действий пользователей.
 */
@Aspect
@Component
@RequiredArgsConstructor
public class UserActionAspect {

    /**
     * Сервис  Кафки
     */
    private final KafkaProducerService kafkaProducerService;

    /**
     * Логирование действий пользователя при вызове метода,
     * помеченного аннотацией TrackUserAction.
     * @param proceedingJoinPoint точка начала процедуры.
     * @return  аспект.
     * @throws Throwable исключение.
     */
    @Around("@annotation(TrackUserAction)")
    public Object userActionLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().getName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n")
                .append(LocalDateTime.now())
                .append(": Пользователь вызвал метод: ")
                .append(methodName)
                .append("\nАргументы метода ").append(methodName).append(": ");
        Arrays.stream(proceedingJoinPoint.getArgs()).forEach(arg -> stringBuilder.append(arg).append("\n"));
        long timeStart = System.currentTimeMillis();
        Object method = proceedingJoinPoint.proceed();
        stringBuilder.append("Время выполнения метода ")
                .append(methodName)
                .append(" (ms): ")
                        .append(System.currentTimeMillis() - timeStart)
                .append("\n");
        System.out.println(stringBuilder);

        return method;
    }

    @Around("@annotation(KafkaRestController)")
    public Object sendMessageToKafkaAboutRestController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().getName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n")
                .append(LocalDateTime.now())
                .append(": Вызван метод: ")
                .append(methodName)
                .append(" Параметры: ");
        Arrays.stream(proceedingJoinPoint.getArgs()).forEach(arg -> stringBuilder.append(arg).append(" "));
        kafkaProducerService.sendMessage(stringBuilder.toString(), "StoreResourceServer.RestController");
        return proceedingJoinPoint.proceed();
    }
}
