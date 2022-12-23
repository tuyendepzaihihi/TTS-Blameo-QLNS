package com.example.tuyendv.repository;

import com.example.tuyendv.entity.Vacation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacationRepository extends JpaRepository<Vacation, Integer> {

    @Query(value = "select count(*) from vacations " +
            "where status  = 1 " +
            "and  id_member = ?1 " +
            "and month = ?2 " +
            "and year = ?3", nativeQuery = true)
    Integer countVacationsInMonth(Integer idMember, Integer month, Integer year);

    Page<Vacation> findByStatus(Integer status, Pageable pageable);

}
