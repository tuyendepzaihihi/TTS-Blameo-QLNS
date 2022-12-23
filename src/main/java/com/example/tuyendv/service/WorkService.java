package com.example.tuyendv.service;

import com.example.tuyendv.entity.Work;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface WorkService {

    List<Work> findAll(Pageable pageable);

    List<Work> findAllByIdUser(Integer id);

    Work save(Work entity);

    Optional<Work> findById(Integer id);

    List<Work> findByWorkInMonth(Integer id, Integer month);

    List<Work> findByWorkInYear(Integer id, Integer year);

}
