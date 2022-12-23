package com.example.tuyendv.service.impl;

import com.example.tuyendv.entity.Vacation;
import com.example.tuyendv.repository.VacationRepository;
import com.example.tuyendv.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VacationServiceImpl implements VacationService {

    @Autowired
    VacationRepository vacationRepo;

    @Override
    public List<Vacation> findAll(Pageable pageable) {
        return vacationRepo.findByStatus(0, pageable).getContent();
    }

    @Override
    public Vacation save(Vacation entity) {
        return vacationRepo.save(entity);
    }

    @Override
    public Optional<Vacation> findById(Integer id) {
        return vacationRepo.findById(id);
    }
}
