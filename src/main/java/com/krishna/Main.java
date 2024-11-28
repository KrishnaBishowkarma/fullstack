package com.krishna;

import com.krishna.customer.Customer;
import com.krishna.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication

public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {
        return args -> {
            Customer krishna = new Customer(
                    "Krishna",
                    "krishna@gmail.com",
                    25
            );

            Customer janaki = new Customer(
                    "Janaki",
                    "janaki@gmail.com",
                    28
            );

            List<Customer> customers = List.of(krishna, janaki);
//            customerRepository.saveAll(customers);
        };
    }
}
