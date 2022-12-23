package com.example.tuyendv.service.impl;

import com.example.tuyendv.entity.Work;
import com.example.tuyendv.repository.WorkRepository;
import com.example.tuyendv.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkServiceImpl implements WorkService {
    @Autowired
    WorkRepository workRepo;

    @Override
    public List<Work> findAll(Pageable pageable) {
        return workRepo.findAll(pageable).getContent();
    }

    @Override
    public List<Work> findAllByIdUser(Integer id) {
        return workRepo.findByIdUser(id);
    }

    @Override
    public Work save(Work entity) {
        return workRepo.save(entity);
    }

    @Override
    public Optional<Work> findById(Integer id) {
        return workRepo.findById(id);
    }

    @Override
    public List<Work> findByWorkInMonth(Integer id, Integer month) {
        return workRepo.findByIdUserAndMonth(id, month);
    }

    @Override
    public List<Work> findByWorkInYear(Integer id, Integer year) {
        return workRepo.findByIdUserAndYear(id, year);
    }
}
