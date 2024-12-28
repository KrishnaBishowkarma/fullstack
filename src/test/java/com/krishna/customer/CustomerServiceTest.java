package com.krishna.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerDao customerDao;
    private CustomerService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerDao);
    }

    @Test
    void getAllCustomers() {
        // WHEN
        underTest.getAllCustomers();

        // THEN
        verify(customerDao).selectAllCustomers();
    }

    @Test
    void canGetCustomer() {
        // GIVEN
        int id = 10;
        Customer customer = new Customer(
                12,
                "krishna",
                "dai@gmail.com",
                12
        );
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        // WHEN
        Customer actual = underTest.getCustomer(10);

        // THEN
        assertThat(actual).isEqualTo(customer);
    }

    @Test
    void addCustomer() {
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