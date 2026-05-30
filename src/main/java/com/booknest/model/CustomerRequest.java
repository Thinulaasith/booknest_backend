package com.booknest.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
}