package com.krishna.customer;

import com.krishna.exception.DuplicateResourceException;
import com.krishna.exception.RequestValidationException;
import com.krishna.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
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

        when(customerDao.existsPersonWithId(id)).thenReturn(true);

        // WHEN
        underTest.deleteCustomerById(id);

        // THEN
        verify(customerDao).deleteCustomerById(id);
    }

    @Test
    void willThrowDeleteCustomerByIdNotExists() {
        // GIVEN
        int id = 10;

        when(customerDao.existsPersonWithId(id)).thenReturn(false);

        // WHEN
        assertThatThrownBy(() -> underTest.deleteCustomerById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Customer with [%s] not found".formatted(id));

        // THEN
        verify(customerDao, never()).deleteCustomerById(id);
    }

    @Test
    void canUpdateAllCustomersProperties() {
        // GIVEN
        int id = 10;
        Customer customer = new Customer(
                id,
                "krishna",
                "dai@gmail.com",
                12
        );
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail = "bhai@gmail.com";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("krishna dai", newEmail, 13);

        when(customerDao.existsPersonWithEmail(newEmail)).thenReturn(false);

        // WHEN
        underTest.updateCustomer(id, updateRequest);

        // THEN
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());
        assertThat(capturedCustomer.getEmail()).isEqualTo(updateRequest.email());
    }

    @Test
    void canUpdateOnlyCustomerName() {
        // GIVEN
        int id = 10;
        Customer customer = new Customer(
                id,
                "krishna",
                "dai@gmail.com",
                12
        );
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("krishna dai", null, null);
        // WHEN
        underTest.updateCustomer(id, updateRequest);
        // THEN
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();
        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
    }

    @Test
    void canUpdateOnlyCustomerEmail() {
        // GIVEN
        int id = 10;
        Customer customer = new Customer(
                id,
                "Krishna",
                "dai@gmail.com",
                12
        );
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));
        String newEmail = "krishnadai@gmail.com";
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null, newEmail, null);
        when(customerDao.existsPersonWithEmail(newEmail)).thenReturn(false);
        // WHEN
        underTest.updateCustomer(id, updateRequest);
        // THEN
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();
        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
        assertThat(capturedCustomer.getEmail()).isEqualTo(newEmail);
    }

    @Test
    void canUpdateOnlyCustomerAge() {
        // GIVEN
        int id = 10;
        Customer customer = new Customer(
                id,
                "krishna",
                "dai@gmail.com",
                12
        );
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null, null, 21);
        // WHEN
        underTest.updateCustomer(id, updateRequest);
        // THEN
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();
        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
    }

    @Test
    void willThrowWhenTryingToUpdateCustomerEmailWhenAlreadyTaken() {
        // GIVEN
        int id = 10;
        Customer customer = new Customer(
                id,
                "Krishna",
                "dai@gmail.com",
                12
        );
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail = "krishnadai@gmail.com";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null, newEmail, null);

        when(customerDao.existsPersonWithEmail(newEmail)).thenReturn(true);

        // WHEN
        assertThatThrownBy(() -> underTest.updateCustomer(id, updateRequest))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Email already taken!");

        // THEN
        verify(customerDao, never()).updateCustomer(any());
    }

    @Test
    void willThrowWhenCustomerUpdateHasNoChanges() {
        // GIVEN
        int id = 10;
        Customer customer = new Customer(
                id,
                "krishna",
                "dai@gmail.com",
                12
        );
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(customer.getName(), customer.getEmail(), customer.getAge());

        // WHEN
        assertThatThrownBy(() -> underTest.updateCustomer(id, updateRequest))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("No changes detected");

        // THEN
        verify(customerDao, never()).updateCustomer(any());
    }
}