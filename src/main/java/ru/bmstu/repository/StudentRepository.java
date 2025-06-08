package ru.bmstu.repository;

import ru.bmstu.model.Student;
import java.util.List;

public interface StudentRepository {
    List<Student> findAll();
    Student findByFullName(String firstName, String lastName);
    void save(Student student);
    void delete(Student student);
    void saveAll(List<Student> students);
    void add(Student student);
}
