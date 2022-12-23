package com.example.tuyendv.util;

import com.example.tuyendv.entity.Work;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ExcelHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static String SHEET_NAME = "work";

    //check file type excel or not
    public static boolean checkExcelFormat(MultipartFile file) {
        return Objects.equals(file.getContentType(), TYPE);
    }

    //convert excel to list
    public static List<Work> convertExcelToList(InputStream inputStream) {

        try {
            List<Work> lstExcel = new ArrayList<>();
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(SHEET_NAME); //name sheet in excel
            Iterator<Row> rows = sheet.iterator();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cells = currentRow.iterator();
                int cellIndex = 0;
                Work work = new Work();
                while (cells.hasNext()) {
                    Cell currentCell = cells.next();
                    switch (cellIndex) {
                        case 1:
                            work.setIdUser((int) currentCell.getNumericCellValue());
                            break;
                        case 2:
                            work.setDay((int) currentCell.getNumericCellValue());
                            break;
                        case 3:
                            work.setMonth((int) currentCell.getNumericCellValue());
                            break;
                        case 4:
                            work.setYear((int) currentCell.getNumericCellValue());
                            break;
                        case 5:
                            work.setTimeInWork(currentCell.getStringCellValue());
                            break;
                        case 6:
                            work.setTimeOutWork(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIndex++;
                }
                lstExcel.add(work);
            }
            System.out.println(lstExcel);
            System.out.println(lstExcel.size());
            workbook.close();
            return lstExcel;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

}
