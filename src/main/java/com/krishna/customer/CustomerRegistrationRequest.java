package com.krishna.customer;

public record CustomerRegistrationRequest (
        String name,
        String email,
        Integer age
){
}
