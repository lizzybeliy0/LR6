import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.bmstu.model.Student;
import ru.bmstu.service.StudentService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    public void testAddAndFindStudent() {
        Student newStudent = new Student("Test", "Student", 10);
        studentService.addStudent(newStudent);

        Student found = studentService.findStudent("Test", "Student");
        assertNotNull(found);
        assertEquals("Test", found.getFirstName());
        assertEquals("Student", found.getLastName());
        assertEquals(10, found.getTokens());
    }

    @Test
    public void testGetAllStudents() {
        List<Student> students = studentService.getAllStudents();
        assertFalse(students.isEmpty());
    }
}