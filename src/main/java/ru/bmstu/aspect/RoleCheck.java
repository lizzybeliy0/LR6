package ru.bmstu.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;
import ru.bmstu.exception.AccessDeniedException;
import ru.bmstu.model.Role;
import ru.bmstu.repository.LogsRepository;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Component
public class RoleCheck {

    private Role currentUserRole = Role.STUDENT;
    private final LogsRepository logsRepository;

    public RoleCheck(LogsRepository logsRepository) {
        this.logsRepository = logsRepository;
    }

    public void setCurrentUserRole(Role role) {
        this.currentUserRole = role;
    }

    @Around("@annotation(ru.bmstu.annotation.OnlyTeacher)")
    public Object checkTeacherRole(ProceedingJoinPoint joinPoint) throws Throwable {
        if (currentUserRole != Role.TEACHER) {
            String message = "Error: this operation is allowed for teachers only.";
            logsRepository.logAction("Access denied", message);
            System.out.println(message);

            throw new AccessDeniedException();
        }

        return joinPoint.proceed();
    }
}