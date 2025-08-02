package amaliy.java.amaliy1.audit;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class AuditAspect {
    private final Logger log = LoggerFactory.getLogger("AUDIT");
    private final HttpServletRequest request;

    public AuditAspect(HttpServletRequest request) {
        this.request = request;
    }

    @Before("@annotation(auditable)")
    public void logAudit(JoinPoint joinPoint,Auditable auditable) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String endpoint = request.getRequestURI();
        String method = request.getMethod();
        String action = auditable.action();
        Object[] args = joinPoint.getArgs();

        String logMessage =String.format( """
                [AUDIT]
                Время: %s
                Пользователь: %s
                Действие: %s
                Метод: %s
                Endpoint: %s
                Аргументы: %s
                """,
                LocalDateTime.now(),
                username,
                action,
                method,
                endpoint,
                Arrays.toString(args)
        );
        log.info( logMessage );
    }
}
