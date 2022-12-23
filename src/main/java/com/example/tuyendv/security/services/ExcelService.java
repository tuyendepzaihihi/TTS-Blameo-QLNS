package com.example.tuyendv.security.services;

import com.example.tuyendv.entity.Work;
import com.example.tuyendv.repository.WorkRepository;
import com.example.tuyendv.util.ExcelHelper;
import com.example.tuyendv.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {

    @Autowired
    WorkRepository workRepo;

    @Autowired
    Utils utils;

    public void save(MultipartFile file) {
        try {
            List<Work> workList = ExcelHelper.convertExcelToList(file.getInputStream());
            for (int i = 0; i < workList.size(); i++) {
                workList.set(i, utils.checkWork(workList.get(i)));
            }
            workRepo.saveAll(workList);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

}
