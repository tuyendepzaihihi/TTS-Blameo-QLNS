package com.example.tuyendv.service;

import com.example.tuyendv.entity.Vacation;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VacationService {

    List<Vacation> findAll(Pageable pageable);

    Vacation save(Vacation entity);

    Optional<Vacation> findById(Integer id);

}
