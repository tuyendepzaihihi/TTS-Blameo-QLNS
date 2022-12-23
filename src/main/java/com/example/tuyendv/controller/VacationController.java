package com.example.tuyendv.controller;

import com.example.tuyendv.dto.VacationDTO;
import com.example.tuyendv.entity.Vacation;
import com.example.tuyendv.payload.response.MessageResponse;
import com.example.tuyendv.payload.response.Response;
import com.example.tuyendv.repository.VacationRepository;
import com.example.tuyendv.service.VacationService;
import com.example.tuyendv.util.DateUtils;
import com.example.tuyendv.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/off")
public class VacationController {

    @Autowired
    VacationService vacationService;

    @Autowired
    Utils utils;

    @Autowired
    VacationRepository vacationRepo;

    @GetMapping("/list-vacation")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public ResponseEntity<?> getAllVacation(@RequestParam("page") int page,
                                            @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(new Response<>(vacationService.findAll(pageable),
                200, "Successfully!"));
    }

    @PostMapping("/create-vacation")
    public ResponseEntity<?> createVacation(@RequestBody VacationDTO dto) {
        try {
            if (utils.getInfoUser() != null) {
                Vacation dayOff = new Vacation();
                dayOff.setStatus(0);
                dayOff.setDay(DateUtils.getDayFromDate(new Date()));
                dayOff.setMonth(DateUtils.getMonthFromDate(new Date()));
                dayOff.setYear(DateUtils.getYearFromDate(new Date()));
                dayOff.setIdMember(utils.getInfoUser().getId());
                dayOff.setIdAdmin(utils.getInfoUser().getIdManager());
                dayOff.setContent(dto.getContent());
                return ResponseEntity.ok().body(
                        new Response<>(vacationService.save(dayOff), 200, "Successfully!")
                );
            }
            return ResponseEntity.badRequest().body(new MessageResponse("User is not found!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Error : " + e.getMessage()));
        }
    }

    @PostMapping("/update-vacation")
    public ResponseEntity<?> updateVacation(@RequestBody VacationDTO dto) {
        try {
            Vacation vacation = vacationService.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Vacation is not found!"));
            if (dto.getIdMember().equals(utils.getInfoUser().getId())) {
                if (vacation.getStatus() == 0) {
                    vacation.setContent(dto.getContent());
                    vacation.setDay(DateUtils.getDayFromDate(new Date()));
                    vacation.setMonth(DateUtils.getMonthFromDate(new Date()));
                    vacation.setYear(DateUtils.getYearFromDate(new Date()));
                    vacation.setStatus(0);
                    vacation.setIdMember(dto.getIdMember());
                    vacation.setIdAdmin(utils.getInfoUser().getIdManager());
                    return ResponseEntity.ok().body(
                            new Response<>(vacationService.save(vacation), 200, "Successfully!")
                    );
                }
                return ResponseEntity.badRequest().body(new MessageResponse("Can't edit vacation"));
            }
            return ResponseEntity.badRequest().body(new MessageResponse("Unable to complete this action"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Error : " + e.getMessage()));
        }
    }

    @PostMapping("/update-status-vacation")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public ResponseEntity<?> updateStatusVacation(@RequestParam("vacation_id") Integer id,
                                                  @RequestParam("status") Integer status) {
        try {
            Vacation vacation = vacationService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Vacation is not found!"));
            if (utils.getInfoUser().getId().equals(vacation.getIdAdmin())) {
                vacation.setStatus(status);
                return ResponseEntity.ok().body(
                        new Response<>(vacationService.save(vacation), 200, "Successfully!")
                );
            }
            return ResponseEntity.badRequest().body(new MessageResponse("Unable to complete this action"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Error : " + e.getMessage()));
        }
    }

    @PostMapping("/delete-vacation")
    public ResponseEntity<?> deleteVacation(@RequestParam("vacation_id") Integer id) {
        try {
            Vacation vacation = vacationService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Vacation is not found!"));
            if (utils.getInfoUser().getId().equals(vacation.getIdMember())) {
                vacation.setStatus(-1);
                return ResponseEntity.ok().body(
                        new Response<>(vacationService.save(vacation), 200, "Successfully!")
                );
            }
            return ResponseEntity.badRequest().body(new MessageResponse("Unable to complete this action"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Error : " + e.getMessage()));
        }
    }

}
