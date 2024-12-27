package com.krishna.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService underTest;
    private AutoCloseable autoCloseable;
    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllCustomers() {
        // WHEN
        underTest.selectAllCustomers();

        // THEN
        verify(customerRepository).findAll();
    }

    @Test
    void selectCustomerById() {
        // GIVEN
        int id = 1;

        // WHEN
        underTest.selectCustomerById(id);

        // THEN
        verify(customerRepository).findById(id);
    }

    @Test
    void insertCustomer() {
        // GIVEN
        Customer customer = new Customer(
                "bibechana",
                "bibechana@gmail.com",
                12
        );

        // WHEN
        underTest.insertCustomer(customer);

        // THEN
        verify(customerRepository).save(customer);
    }

    @Test
    void existsPersonWithEmail() {
        // GIVEN
        String email = "dai@gmail.com";

        // WHEN
        underTest.existsPersonWithEmail(email);

        // THEN
        verify(customerRepository).existsCustomerByEmail(email);
    }

    @Test
    void existsPersonWithId() {
        // GIVEN
        int id = 1;

        // WHEN\
        underTest.existsPersonWithId(id);

        // THEN
        verify(customerRepository).existsCustomerById(id);
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