package com.example.tuyendv.repository;

import com.example.tuyendv.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Boolean existsByEmail(String email);

    Boolean existsByUserName(String userName);

    Optional<User> findByUserName(String username);

    Page<User> findAllById(Integer id, Pageable pageable);

}
