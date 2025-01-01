package com.krishna.customer;

import com.krishna.exception.DuplicateResourceException;
import com.krishna.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    void willThrowWhenGetCustomerReturnsEmptyOptional() {
        // GIVEN
        int id = 10;

        when(customerDao.selectCustomerById(id)).thenReturn(Optional.empty());

        // WHEN
        // THEN
        assertThatThrownBy(() -> underTest.getCustomer(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Customer with [%s] not found".formatted(id));

    }

    @Test
    void addCustomer() {
        // GIVEN
        String email = "dai@gmail.com";

        when(customerDao.existsPersonWithEmail(email)).thenReturn(false);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "dai",
                email,
                12
        );

        // WHEN
        underTest.addCustomer(request);

        // THEN
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).insertCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getId()).isNull();
        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());
    }

    @Test
    void willThrowWhenEmailExistsWhileAddingCustomer() {
        // GIVEN
        String email = "dai@gmail.com";

        when(customerDao.existsPersonWithEmail(email)).thenReturn(true);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "dai",
                email,
                12
        );

        // WHEN
        assertThatThrownBy(() -> underTest.addCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Email already taken!");

        // THEN
        verify(customerDao, never()).insertCustomer(any());
    }

    @Test
    void deleteCustomerById() {
        // GIVEN
        int id = 10;

        when(customerDao.existsPersonWithId(id)).thenReturn(false);

        // WHEN
        underTest.deleteCustomerById(id);

        // THEN
        verify(customerDao).deleteCustomerById(id);
    }

    @Test
    void updateCustomer() {
        // GIVEN

        // WHEN

        // THEN
    }
}