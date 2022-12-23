package com.example.tuyendv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacationDTO {

    private Integer id;
    private Integer idMember;
    private Integer idAdmin;
    private Date dateOff;
    private String content;

}
