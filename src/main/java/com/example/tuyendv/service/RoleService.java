package com.example.tuyendv.service;

import com.example.tuyendv.dto.RoleDTO;
import com.example.tuyendv.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<Role> findAll();

    Role save(Role entity);

    Optional<Role> findById(Integer id);

    Optional<Role> findByRole(String role);
}
