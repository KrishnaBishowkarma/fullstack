package com.krishna.customer;

import com.krishna.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;
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
        List<Customer> actual = underTest.selectAllCustomers();

        // THEN
        assertThat(actual).isNotEmpty();
    }

    @Test
    void selectCustomerById() {
        // GIVEN
        String email = faker.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(),
                email,
                faker.number().numberBetween(1, 100)
        );

        underTest.insertCustomer(customer);

        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // WHEN
        Optional<Customer> actual = underTest.selectCustomerById(id);

        // THEN
        assertThat(actual)
                .isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c.getId()).isEqualTo(id);
                    assertThat(c.getName()).isEqualTo(customer.getName());
                    assertThat(c.getEmail()).isEqualTo(customer.getEmail());
                    assertThat(c.getAge()).isEqualTo(customer.getAge());
                });
    }

    @Test
    void willReturnEmptyWhenSelectCustomerById() {
        // GIVEN
        int id = 0;

        // WHEN
        var actual = underTest.selectCustomerById(id);

        // THEN
        assertThat(actual).isEmpty();
    }

    @Test
    void existsPersonWithEmail() {
        // Given
        String email = faker.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = faker.name().fullName();
        Customer customer = new Customer(
                name,
                email,
                20
        );

        underTest.insertCustomer(customer);

        // When
        boolean actual = underTest.existsPersonWithEmail(email);

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsPersonWithEmailReturnFalseWhenDoesNotExists() {
        // GIVEN
        String email = faker.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        // WHEN
        boolean actual = underTest.existsPersonWithEmail(email);

        // THEN
        assertThat(actual).isFalse();
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