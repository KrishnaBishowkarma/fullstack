package com.krishna.customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerListDataAccessService implements CustomerDao {

    //db
    private static List<Customer> customers;

    static {
        customers = new ArrayList<>();
        Customer krishna = new Customer(
                1,
                "Krishna",
                "krishna@gmail.com",
                25
        );
        customers.add(krishna);

        Customer janaki = new Customer(
                2,
                "Janaki",
                "janaki@gmail.com",
                28
        );
        customers.add(janaki);
    }

    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }
}