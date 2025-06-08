package ru.bmstu.service;

import ru.bmstu.annotation.OnlyTeacher;
import ru.bmstu.model.Student;
import ru.bmstu.repository.LogsRepository;
import ru.bmstu.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private final StudentRepository studentRepository;
    private final LogsRepository logsRepository;

    @Autowired
    public TokenService(StudentRepository studentRepository, LogsRepository logsRepository) {
        this.studentRepository = studentRepository;
        this.logsRepository = logsRepository;
    }

    @OnlyTeacher
    public void addTokens(Student student, int amount) {
        student.setTokens(student.getTokens() + amount);
        studentRepository.save(student);
        logsRepository.logAction("Add tokens",
                "Added " + amount + " tokens to student " +
                        student.getFirstName() + " " + student.getLastName());
    }

    @OnlyTeacher
    public void deleteTokens(Student student, int amount) {
        int newAmount = student.getTokens() - amount;
        student.setTokens(Math.max(newAmount, 0));
        studentRepository.save(student);
        logsRepository.logAction("Subtract tokens",
                "Subtracted " + amount + " tokens from student " +
                        student.getFirstName() + " " + student.getLastName());
    }
}