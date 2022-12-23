package com.example.tuyendv.service.impl;

import com.example.tuyendv.constant.Constant;
import com.example.tuyendv.dto.SalaryDTO;
import com.example.tuyendv.entity.ERole;
import com.example.tuyendv.entity.Role;
import com.example.tuyendv.entity.User;
import com.example.tuyendv.entity.Work;
import com.example.tuyendv.repository.UserRepository;
import com.example.tuyendv.repository.VacationRepository;
import com.example.tuyendv.repository.WorkRepository;
import com.example.tuyendv.service.UserService;
import com.example.tuyendv.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    WorkRepository workRepo;

    @Autowired
    VacationRepository vacationRepo;

    @Autowired
    Utils utils;

    @Override
    public Page<User> findAllUser(Integer pageNo, Integer pageSize){
        Map<String, Object> output = new HashMap<>();
        User user = utils.getInfoUser();
        List<Role> roleList = new ArrayList<>(user.getRoles());
        if (!roleList.get(0).getRole().equals(Constant.ROLE_SUPER_ADMIN))
            return userRepo.findAll(PageRequest.of(pageNo - 1, pageSize));
        return userRepo.findAllById(user.getId(), PageRequest.of(pageNo - 1, pageSize));
    }

    @Override
    public Double getSalary(Integer month, Integer year) throws ParseException {
        User user = utils.getInfoUser();
        double salary = 0;
        int dayOff = vacationRepo.countVacationsInMonth(user.getId(), month, year);
        List<Work> workList = workRepo.findByIdUserAndYear(user.getId(), year);
        for (Work work : workList) {
            if (work.getMonth().equals(month)) {
                double contactSalary = userRepo.findById(user.getId())
                        .orElseThrow(() -> new RuntimeException("User is not found!"))
                        .getSalary();
                salary += utils.getSalaryUser(contactSalary, month, year,
                        utils.getTimeUserWork(work.getTimeInWork(), work.getTimeOutWork())
                );
            }
        }
        salary = salary - (dayOff > 4 ? (dayOff - 4) : 0) * 50000;
        return salary;
    }

    @Override
    public List<SalaryDTO> getListSalary(Integer month, Integer year) throws ParseException {
        List<SalaryDTO> salaryList = new ArrayList<>();
        List<Integer> lstId = workRepo.getListIdUser(month, year);
        for (Integer id : lstId) {
            SalaryDTO dto = new SalaryDTO();
            dto.setIdUser(id);
            dto.setMonth(month);
            dto.setYear(year);
            dto.setSalaryReceived((double) Math.round(getSalary(id, month, year)));
            salaryList.add(dto);
        }
        return salaryList;
    }

    @Override
    public User save(User entity) {
        return userRepo.save(entity);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepo.findById(id);
    }

    @Override
    public boolean existsByUserName(String username) {
        return userRepo.existsByUserName(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }
}
