package com.krishna.customer;

public record  CustomerUpdateRequest(
        String name,
        String email,
        Integer age
) {
}
