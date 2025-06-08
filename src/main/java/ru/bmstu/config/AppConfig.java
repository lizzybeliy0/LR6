package ru.bmstu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.bmstu.repository.CsvStudentRepository;
import ru.bmstu.repository.StudentRepository;

import java.io.IOException;
import java.nio.file.Path;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("ru.bmstu")
@EnableWebMvc
@EnableAspectJAutoProxy
public class AppConfig {
    @Value("${students.csv.path}")
    private String csvPathString;

    @Bean
    public StudentRepository studentRepository() throws IOException {
        Path csvPath = Path.of(csvPathString);
        return new CsvStudentRepository(csvPath);
    }
}