package ru.bmstu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bmstu.annotation.OnlyTeacher;
import ru.bmstu.aspect.RoleCheck;

import ru.bmstu.model.Role;
import ru.bmstu.model.Student;
import ru.bmstu.repository.LogsRepository;
import ru.bmstu.service.StudentService;
import ru.bmstu.service.TokenService;

import java.util.List;
import java.util.Scanner;

@Component
public class StudentApp {
    private final StudentService studentService;
    private final TokenService tokenService;
    private final RoleCheck roleCheckAspect;
    private final Scanner scanner;
    private final LogsRepository logsRepository;

    @Autowired
    public StudentApp(StudentService studentService, TokenService tokenService,
                      RoleCheck roleCheckAspect, LogsRepository logsRepository) {
        this.studentService = studentService;
        this.tokenService = tokenService;
        this.roleCheckAspect = roleCheckAspect;
        this.scanner = new Scanner(System.in);
        this.logsRepository = logsRepository;
    }

    public void run() {
        System.out.println("Welcome to the token management system");

        authenticateUser();

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    listStudents();
                    break;
                case "2":
                    modifyTokens();
                    break;
                case "3":
                    addStudent();
                    break;
                case "4":
                    removeStudent();
                    break;
                case "5":
                    showLogs();
                    break;
                case "6":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid input, please, try again");
            }
        }

        System.out.println("Exiting...");
        scanner.close();
    }

    private void authenticateUser() {
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter your role (STUDENT/TEACHER): ");
        String roleStr = scanner.nextLine().toUpperCase();

        try {
            Role role = Role.valueOf(roleStr);
            roleCheckAspect.setCurrentUserRole(role);
            System.out.println("Logged in as " + role);
            logsRepository.logAction("Authorization", firstName + " " + lastName + " as " + role);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid role. Logging in as STUDENT.");
            roleCheckAspect.setCurrentUserRole(Role.STUDENT);
            logsRepository.logAction("Authorization", firstName + " " + lastName + " as " + Role.STUDENT);
        }
    }

    private void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Print all students");
        System.out.println("2. Change tokens");
        System.out.println("3. Add a new student");
        System.out.println("4. Delete a student");
        System.out.println("5. View logs");
        System.out.println("6. Exit");
        System.out.print("Select an option: ");
    }

    private void listStudents() {
        System.out.println("\nList of all students:");
        studentService.getAllStudents().forEach(student ->
                System.out.println(student.getFirstName() + " " + student.getLastName() +
                        ": " + student.getTokens() + " tokens"));
        logsRepository.logAction("List all students", "User requested the list of all students");
    }

    private void modifyTokens() {
        System.out.print("Enter student's first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter student's last name: ");
        String lastName = scanner.nextLine();

        Student student = studentService.findStudent(firstName, lastName);
        if (student == null) {
            System.out.println("Student not found");
            return;
        }

        System.out.print("Enter the number of tokens to add: ");
        int amount = Integer.parseInt(scanner.nextLine());

        if (amount > 0) {
            tokenService.addTokens(student, amount);
        } else {
            tokenService.deleteTokens(student, -amount);
        }
    }

    private void addStudent() {
        System.out.print("Enter new student's first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter new student's last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter the number of tokens: ");
        int tokens = Integer.parseInt(scanner.nextLine());

        Student newStudent = new Student(firstName, lastName, tokens);
        studentService.addStudent(newStudent);
    }

    private void removeStudent() {
        System.out.print("Enter first name of student to remove: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name of student to remove: ");
        String lastName = scanner.nextLine();

        Student student = studentService.findStudent(firstName, lastName);
        if (student == null) {
            System.out.println("No such student found.");
            return;
        }
        studentService.removeStudent(student);
    }

    private void showLogs() {
        List<String> logs = logsRepository.readLogsFromFile();

        System.out.println("System Logs:");
        for (String logLine : logs) {
            System.out.println(logLine);
        }
    }
}
