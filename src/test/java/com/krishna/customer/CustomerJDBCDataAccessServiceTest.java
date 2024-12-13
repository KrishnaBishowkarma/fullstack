package com.krishna.customer;

import com.krishna.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomerJDBCDataAccessServiceTest extends AbstractTestcontainers {

    private CustomerJDBCDataAccessService underTest;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    @BeforeEach
    void setUp() {
        underTest = new CustomerJDBCDataAccessService(
                getJdbcTemplate(),
                customerRowMapper
        );
    }

    @Test
    void selectAllCustomers() {
        // GIVEN
        Customer customer = new Customer(
                faker.name().fullName(),
                faker.internet().safeEmailAddress() + "-" + UUID.randomUUID(),
                faker.number().numberBetween(1, 100)
        );
        underTest.insertCustomer(customer);

        // WHEN
        List<Customer> customers = underTest.selectAllCustomers();

        // THEN
        assertThat(customers).isNotEmpty();
    }

    @Test
    void selectCustomerById() {
        // GIVEN

        // WHEN

        // THEN
    }

    @Test
    void insertCustomer() {
        // GIVEN

        // WHEN

        // THEN
    }

    @Test
    void existsPersonWithEmail() {
        // GIVEN

        // WHEN

        // THEN
    }

    @Test
    void existsPersonWithId() {
        // GIVEN

        // WHEN

        // THEN
    }

    @Test
    void deleteCustomerById() {
        // GIVEN

        // WHEN

        // THEN
    }

    @Test
    void updateCustomer() {
        // GIVEN

        // WHEN

        // THEN
    }
}