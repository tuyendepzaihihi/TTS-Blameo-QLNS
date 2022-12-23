package com.example.tuyendv.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalaryDTO {

    private Integer idUser;
    private Double salaryReceived;
    private Integer month;
    private Integer year;

}
