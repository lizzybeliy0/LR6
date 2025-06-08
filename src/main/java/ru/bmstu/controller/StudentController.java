package ru.bmstu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.model.Student;
import ru.bmstu.service.StudentService;

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
    public ResponseEntity<Student> getStudent(
            @PathVariable String firstName,
            @PathVariable String lastName) {
        Student student = studentService.findStudent(firstName, lastName);
        return student != null ?
                ResponseEntity.ok(student) :
                ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        studentService.addStudent(student);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{firstName}/{lastName}")
    public ResponseEntity<Void> removeStudent(
            @PathVariable String firstName,
            @PathVariable String lastName) {
        Student student = studentService.findStudent(firstName, lastName);
        if (student != null) {
            studentService.removeStudent(student);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}