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