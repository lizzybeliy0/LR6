package ru.bmstu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.annotation.OnlyTeacher;
import ru.bmstu.exception.StudentNotFoundException;
import ru.bmstu.model.Student;
import ru.bmstu.service.StudentService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{firstName}/{lastName}")
    public ResponseEntity<Object> getStudent(//=этот метод возвращ HTTP отв
            @PathVariable String firstName,
            @PathVariable String lastName) {
        Student student = studentService.findStudent(firstName, lastName);
        if (student == null) {
            throw new StudentNotFoundException();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("/find")
    public ResponseEntity<Object> getStudentByQuery(//обычно для фильтрации, не конкретного ресурса
            @RequestParam String firstName,
            @RequestParam String lastName) {
        Student student = studentService.findStudent(firstName, lastName);
        if (student == null) {
            throw new StudentNotFoundException();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    @OnlyTeacher
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        studentService.addStudent(student);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{firstName}/{lastName}")
    @OnlyTeacher
    public ResponseEntity<Object> removeStudent(
            @PathVariable String firstName,
            @PathVariable String lastName) {
        Student student = studentService.findStudent(firstName, lastName);

        if (student == null) {
            throw new StudentNotFoundException();
        }
        studentService.removeStudent(student);
        return ResponseEntity.noContent().build(); //Соотв REST API (запрос-ответ)
    }
}