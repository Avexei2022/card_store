package kolodin.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Аспект для регистрации действий пользователей и вывода их в консоль.
 */
@Aspect
@Component
public class UserActionAspect {


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
        stringBuilder
                .append("\n")
                .append(LocalDateTime.now())
                .append(": Пользователь вызвал метод: ")
                .append(methodName)
                .append("\nАргументы метода ").append(methodName).append(": ");
        Arrays.stream(proceedingJoinPoint.getArgs()).forEach(arg -> stringBuilder.append(arg).append(" "));
        long timeStart = System.currentTimeMillis();
        Object method = proceedingJoinPoint.proceed();
        stringBuilder.append("Время выполнения метода ")
                .append(methodName)
                .append(" (ms): ")
                        .append(System.currentTimeMillis() - timeStart);
        System.out.println(stringBuilder);

        return method;
    }
}
