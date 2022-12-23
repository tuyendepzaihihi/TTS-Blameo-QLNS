package com.example.tuyendv.controller;

import com.example.tuyendv.payload.response.MessageResponse;
import com.example.tuyendv.payload.response.Response;
import com.example.tuyendv.security.services.ExcelService;
import com.example.tuyendv.util.ExcelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    @Autowired
    ExcelService excelService;

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            if (ExcelHelper.checkExcelFormat(file)) {
                excelService.save(file);
                return ResponseEntity.ok().body(
                        new Response<>(200, "Uploaded the file successfully: " + file.getOriginalFilename())
                );
            }
            return ResponseEntity.badRequest().body(new MessageResponse("Please upload an excel file!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Error : " + e.getMessage()));
        }
    }
}
