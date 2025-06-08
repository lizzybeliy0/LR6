package ru.bmstu.service;

import ru.bmstu.model.Student;

public interface TokenServiceInt {
    void addTokens(Student student, int count);
    void deleteTokens(Student student, int count);
}