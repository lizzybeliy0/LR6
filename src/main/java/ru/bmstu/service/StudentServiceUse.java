package ru.bmstu.service;

import ru.bmstu.annotation.OnlyTeacher;
import ru.bmstu.model.Student;
import ru.bmstu.repository.LogsRepository;
import ru.bmstu.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentServiceUse implements StudentService{
    private final StudentRepository studentRepository;
    private final LogsRepository logRepository;

    @Autowired
    public StudentServiceUse(StudentRepository studentRepository, LogsRepository logRepository) {
        this.studentRepository = studentRepository;
        this.logRepository = logRepository;
    }
    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student findStudent(String firstName, String lastName) {
        return studentRepository.findByFullName(firstName, lastName);
    }

    @Override
    @OnlyTeacher
    public void addStudent(Student student) {
        studentRepository.add(student);
        logRepository.logAction("New student", "Added new student: " + student.getFirstName()
                + " " + student.getLastName() + " with " + student.getTokens() + " tokens");
        System.out.println("Student successfully added.");
    }

    @Override
    @OnlyTeacher
    public void removeStudent(Student student) {
        studentRepository.delete(student);
        logRepository.logAction("Removed student", "Student " + student.getFirstName()
                + " " + student.getLastName() + " was removed");
        System.out.println("Student successfully removed.");
    }
}
