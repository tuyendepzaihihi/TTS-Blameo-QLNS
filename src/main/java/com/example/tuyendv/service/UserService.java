package com.example.tuyendv.service;

import com.example.tuyendv.dto.SalaryDTO;
import com.example.tuyendv.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Page<User> findAllUser(Integer pageNo, Integer pageSize);

    Double getSalary(Integer month, Integer year) throws ParseException;

    List<SalaryDTO> getListSalary(Integer month, Integer year) throws ParseException;

    User save(User entity);

    Optional<User> findById(Integer id);

    boolean existsByUserName(String username);

    boolean existsByEmail(String email);
}
