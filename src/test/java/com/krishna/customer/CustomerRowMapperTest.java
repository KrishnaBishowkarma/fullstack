package com.krishna.customer;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerRowMapperTest {

    @Test
    void mapRow() throws SQLException {
        // GIVEN
        CustomerRowMapper customerRowMapper = new CustomerRowMapper();
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getInt("age")).thenReturn(25);
        when(resultSet.getString("name")).thenReturn("Krishna");
        when(resultSet.getString("email")).thenReturn("dai@gmail.com");

        // WHEN
        Customer actualCustomer = customerRowMapper.mapRow(resultSet, 1);

        // THEN
        Customer expectedCustomer = new Customer(1, "Krishna", "dai@gmail.com", 25);
        assertThat(actualCustomer).isEqualTo(expectedCustomer);
    }
}