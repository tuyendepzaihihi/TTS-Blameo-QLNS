package com.example.tuyendv.payload.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    private String userName;

    private String email;

    private String password;

    private String fullName;

}
