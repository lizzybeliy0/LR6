import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.bmstu.repository.CsvStudentRepository;
import ru.bmstu.repository.LogsRepository;
import ru.bmstu.repository.ManageLogRepository;
import ru.bmstu.repository.StudentRepository;
import ru.bmstu.service.StudentService;
import ru.bmstu.service.StudentServiceUse;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class TestConfig {

    @Bean
    public StudentRepository studentRepository() {
        Path csvPath = Paths.get("src/test/resources/studentTest.csv");
        return new CsvStudentRepository(csvPath);
    }

    @Bean
    public LogsRepository logRepository() {
        return new ManageLogRepository();
    }

    @Bean
    public StudentService studentService(StudentRepository studentRepository, LogsRepository logRepository) {
        return new StudentServiceUse(studentRepository, logRepository);
    }
}