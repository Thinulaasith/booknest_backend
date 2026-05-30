package com.booknest.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerLoginResponse {

    private String token;
    private String type;
    private Long customerId;
    private String email;
    private String firstName;
    private String lastName;
}