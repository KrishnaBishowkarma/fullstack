package com.krishna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/greet")
    public GreetResponse greet(
            @RequestParam(value = "name", required = false) String name
    ) {
        String greetMessage = name == null || name.isBlank() ? "Hello" : "Hello " + name;
        GreetResponse response = new GreetResponse(
                greetMessage,
                List.of("Java", "Go", "Javascript"),
                new Person("Parwati", 21, 24_200)
        );
        return response;
    }

    record Person(String name, int age, double savings) {
    }

    record GreetResponse(
            String greet,
            List<String> faveProgrammingLanguages,
            Person person
    ) {
    }
}