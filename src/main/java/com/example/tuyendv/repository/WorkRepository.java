package com.example.tuyendv.repository;

import com.example.tuyendv.entity.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkRepository extends JpaRepository<Work, Integer> {

    @Query(value = "select distinct id_user from works " +
            "where month = ?1 and year = ?2", nativeQuery = true)
    List<Integer> getListIdUser(Integer month, Integer year);

    List<Work> findByIdUser(Integer id);

    List<Work> findByIdUserAndMonth(Integer id, Integer month);

    List<Work> findByIdUserAndYear(Integer id, Integer year);

    @Override
    Page<Work> findAll(Pageable pageable);
}
