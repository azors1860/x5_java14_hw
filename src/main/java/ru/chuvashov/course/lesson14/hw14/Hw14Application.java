package ru.chuvashov.course.lesson14.hw14;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class Hw14Application {

    public static void main(String[] args) {
        SpringApplication.run(Hw14Application.class, args);
    }

}
