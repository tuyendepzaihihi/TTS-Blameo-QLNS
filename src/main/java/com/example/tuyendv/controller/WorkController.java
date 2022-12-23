package com.example.tuyendv.controller;

import com.example.tuyendv.entity.Work;
import com.example.tuyendv.payload.response.MessageResponse;
import com.example.tuyendv.payload.response.Response;
import com.example.tuyendv.service.UserService;
import com.example.tuyendv.service.WorkService;
import com.example.tuyendv.util.DateUtils;
import com.example.tuyendv.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/work")
public class WorkController {

    @Autowired
    WorkService workService;

    @Autowired
    UserService userService;

    @Autowired
    Utils utils;

    @GetMapping("/list-work")
    public ResponseEntity<?> getAllWork(@RequestParam("page") int page,
                                        @RequestParam("size") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return ResponseEntity.ok().body(new Response<>(workService.findAll(pageable),
                    200, "Successfully!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Error : " + e.getMessage()));
        }
    }

    @GetMapping("/list-work-user")
    public ResponseEntity<?> getAllWorkUser() {
        try {
            return ResponseEntity.ok().body(new Response<>(workService.findAllByIdUser(utils.getInfoUser().getId()),
                    200, "Successfully!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Error : " + e.getMessage()));
        }
    }

    @GetMapping("/list-work-month")
    public ResponseEntity<?> getAllWorkByUserAndMonth(@RequestParam("month") Integer month) {
        try {
            return ResponseEntity.ok().body(
                    new Response<>(workService.findByWorkInMonth(utils.getInfoUser().getId(), month),
                            200, "Successfully!")
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Error : " + e.getMessage()));
        }
    }

    @GetMapping("/list-work-year")
    public ResponseEntity<?> getAllWorkByUserAndYear(@RequestParam("year") Integer year) {
        try {
            return ResponseEntity.ok().body(
                    new Response<>(workService.findByWorkInYear(utils.getInfoUser().getId(), year),
                            200, "Successfully!")
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Error : " + e.getMessage()));
        }
    }

//    @PostMapping("/create-work")
//    public ResponseEntity<?> createWork(){
//        try{
//            if(utils.getInfoUser() != null){
//                Work entity = new Work();
//                entity.setIdUser(utils.getInfoUser().getId());
//                entity.setDay(DateUtils.getDayFromDate(new Date()));
//                entity.setMonth(DateUtils.getMonthFromDate(new Date()));
//                entity.setYear(DateUtils.getYearFromDate(new Date()));
//                return ResponseEntity.ok().body(
//                        new Response<>(workService.save(entity), 200, "Successfully!")
//                );
//            }
//            return ResponseEntity.badRequest().body(new MessageResponse("User is not found!"));
//        }catch (Exception e){
//            e.printStackTrace();
//            return ResponseEntity.badRequest().body(new MessageResponse("Error : " + e.getMessage()));
//        }
//    }

}
