package ru.bmstu.repository;

import ru.bmstu.model.Student;
import org.apache.commons.csv.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class CsvStudentRepository implements StudentRepository {
    private Path csvPath;
    private static final String[] HEADERS = {"FirstName", "LastName", "Tokens"};

    public CsvStudentRepository(Path csvPath) {
        this.csvPath = csvPath;
    }

    @Override
    public List<Student> findAll() {
        try (Reader reader = Files.newBufferedReader(csvPath)) {
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader(HEADERS).withSkipHeaderRecord());

            List<Student> students = new ArrayList<>();
            for (CSVRecord record : parser) {
                students.add(new Student(record.get("FirstName"),
                        record.get("LastName"),
                        Integer.parseInt(record.get("Tokens"))
                ));
            }
            return students;
        } catch (IOException e) {
            throw new RuntimeException("Failed to open file", e);
        }
    }

    @Override
    public Student findByFullName(String firstName, String lastName) {
        List<Student> students = findAll();
        for (Student student : students) {
            if (student.getFirstName().equals(firstName) && student.getLastName().equals(lastName)) {
                return student;
            }
        }
        return null;
    }

    @Override
    public void add(Student student) {
        List<Student> students = findAll();

        String originalFirstName = student.getFirstName();
        String originalLastName = student.getLastName();

        boolean exists = false;
        for (Student s : students) {
            if (s.getFirstName().equals(originalFirstName) && s.getLastName().equals(originalLastName)) {
                exists = true;
                break;
            }
        }

        if (exists) {
            int maxIncrement = 0;
            for (Student s : students) {
                if (s.getFirstName().equals(originalFirstName) && s.getLastName().startsWith(originalLastName)) {
                    String[] parts = s.getLastName().split("_");
                    if (parts.length > 1) {
                        try {
                            int num = Integer.parseInt(parts[parts.length - 1]); //см на то, что после _
                            if (num > maxIncrement) {
                                maxIncrement = num;
                            }
                        } catch (NumberFormatException ignored) {}
                    }
                }
            }
            student.setLastName(originalLastName + "_" + (maxIncrement + 1));
        }

        students.add(student);
        saveAll(students);
    }

    @Override
    public void save(Student student) {
        List<Student> students = findAll();
        List<Student> updatedList = new ArrayList<>();
        boolean found = false;

        for (Student s : students) {
            if (s.getFirstName().equals(student.getFirstName()) &&
                    s.getLastName().equals(student.getLastName())) {
                updatedList.add(student);
                found = true;
            } else {
                updatedList.add(s);
            }
        }
        if (!found) {
            updatedList.add(student);
        }
        saveAll(updatedList);
    }

    @Override
    public void delete(Student student) {
        List<Student> students = findAll();
        List<Student> updatedList = new ArrayList<>();

        for (Student s : students) {
            if (!(s.getFirstName().equals(student.getFirstName()) &&
                    s.getLastName().equals(student.getLastName()))) {
                updatedList.add(s);
            }
        }
        saveAll(updatedList);
    }

    @Override
    public void saveAll(List<Student> students) {
        try (Writer writer = Files.newBufferedWriter(csvPath);
             CSVPrinter printer = new CSVPrinter(writer,
                     CSVFormat.DEFAULT.withHeader("FirstName", "LastName", "Tokens"))) {

            for (Student s : students) {
                printer.printRecord(s.getFirstName(), s.getLastName(), s.getTokens());
            }

        } catch (IOException e) {
            throw new RuntimeException("Error writing to CSV", e);
        }
    }

}
