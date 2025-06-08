package ru.bmstu.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.bmstu.model.Role;
import ru.bmstu.repository.LogsRepository;

@Aspect
@Component
public class RoleCheck {

    private Role currentUserRole;
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
            logsRepository.logAction("Access denied!", "Student attempted to perform a restricted action");
            System.out.println("Error: this operation is allowed for teachers only.");
            return null;
        }

        return joinPoint.proceed();
    }
}
