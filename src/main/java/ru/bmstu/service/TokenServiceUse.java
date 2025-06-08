package ru.bmstu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bmstu.annotation.OnlyTeacher;
import ru.bmstu.model.Student;
import ru.bmstu.repository.LogsRepository;
import ru.bmstu.repository.StudentRepository;

@Service
public class TokenServiceUse implements TokenServiceInt {
    private final StudentRepository studentRepository;
    private final LogsRepository tokenLogRepository;

    @Autowired
    public TokenServiceUse(StudentRepository studentRepository, LogsRepository tokenLogRepository) {
        this.studentRepository = studentRepository;
        this.tokenLogRepository = tokenLogRepository;
    }

    @Override
    @OnlyTeacher
    public void addTokens(Student student, int amount) {
        student.setTokens(student.getTokens() + amount);
        studentRepository.save(student);
        tokenLogRepository.logAction("Add tokens", "Added " + amount + " tokens to student " + student.getFirstName() + " " + student.getLastName());
        System.out.println("Tokens successfully updated!");
    }

    @Override
    @OnlyTeacher
    public void deleteTokens(Student student, int amount) {
        if (student.getTokens() - amount < 0) {
            student.setTokens(0);
        }
        else {
            student.setTokens(student.getTokens() - amount);
        }
        studentRepository.save(student);
        tokenLogRepository.logAction("Take tokens", "Taked " + amount + " tokens from student " + student.getFirstName() + " " + student.getLastName());
        System.out.println("Tokens successfully updated!");
    }
}
