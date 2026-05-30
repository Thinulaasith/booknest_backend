package com.booknest.model;

import lombok.Data;

@Data
public class CustomerLoginRequest {

    private String email;
    private String password;
}