package com.example.tuyendv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private boolean gender;
    private Date dateOfBirth;
    private String username;
    private String password;
    private Integer salary;

}
