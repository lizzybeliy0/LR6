package ru.bmstu.service;


import ru.bmstu.model.Student;
import java.util.List;


public interface StudentService {
    List<Student> getAllStudents();
    Student findStudent(String firstName, String lastName);
    void addStudent(Student student);
    void removeStudent(Student student);
}
