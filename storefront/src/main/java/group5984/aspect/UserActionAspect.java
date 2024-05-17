package group5984.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Аспект для регистрации действий пользователей и вывода их в консоль
 */
@Aspect
@Component
public class UserActionAspect {


    @Around("@annotation(TrackUserAction)")
    public Object userActionLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().getName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(LocalDateTime.now())
                .append(": Пользователь вызвал метод: ")
                .append(methodName)
                .append("\nАргументы метода ").append(methodName).append(": ");
        Arrays.stream(proceedingJoinPoint.getArgs()).forEach(arg -> stringBuilder.append(arg).append("\n"));
        long timeStart = System.currentTimeMillis();
        Object method = proceedingJoinPoint.proceed();
        stringBuilder.append("\nВремя выполнения метода ")
                .append(methodName)
                .append(" (ms): ")
                        .append(System.currentTimeMillis() - timeStart);
        System.out.println(stringBuilder);

        return method;
    }
}
