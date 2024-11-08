package com.krishna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

//@SpringBootApplication
@ComponentScan(basePackages = "com.krishna")
@EnableAutoConfiguration
@Configuration
@RestController
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/greet")
    public GreetResponse greet() {
        return new GreetResponse("Hello There");
    }

//    public record GreetResponse(String greet) {
//    }

    class GreetResponse {
        public GreetResponse(String greet) {
            this.greet = greet;
        }

        private final String greet;

        public String getGreet() {
            return greet;
        }

        @Override
        public String toString() {
            return "GreetResponse{" +
                    "greet='" + greet + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GreetResponse response = (GreetResponse) o;
            return Objects.equals(greet, response.greet);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(greet);
        }
    }
}