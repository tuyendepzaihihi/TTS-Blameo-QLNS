package com.example.tuyendv.service.impl;

import com.example.tuyendv.entity.Role;
import com.example.tuyendv.repository.RoleRepository;
import com.example.tuyendv.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepo;

    @Override
    public List<Role> findAll() {
        return roleRepo.findAll();
    }

    @Override
    public Role save(Role entity) {
        return roleRepo.save(entity);
    }

    @Override
    public Optional<Role> findById(Integer id) {
        return roleRepo.findById(id);
    }

    @Override
    public Optional<Role> findByRole(String role) {
        return roleRepo.findByRole(role);
    }
}
