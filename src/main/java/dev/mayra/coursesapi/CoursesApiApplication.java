package dev.mayra.coursesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CoursesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoursesApiApplication.class, args);
    }

}
