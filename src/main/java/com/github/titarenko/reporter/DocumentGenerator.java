package com.github.titarenko.reporter;

import com.github.titarenko.model.Request;
import com.github.titarenko.model.Session;
import com.github.titarenko.model.User;
import com.github.titarenko.service.UserService;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class DocumentGenerator {

    @Autowired
    private UserService userService;

    public void createDoc() {
        StringBuilder stringBuilder = new StringBuilder();
        for (User user : userService.getAllUsers()) {
            for (Session session : user.getSessionList()) {
                for (Request request : session.getRequestList()){
                    stringBuilder.append("/"+user.getUserName());
                    stringBuilder.append("/"+user.getUserGroup().getGroupName());
                    stringBuilder.append("/"+session.getDateOpened());
                    stringBuilder.append("/"+session.getDateClosed());
                    stringBuilder.append("/"+request.getUrl());
                    stringBuilder.append("/"+request.getMethod());
                    stringBuilder.append("/"+request.getParams());
                    stringBuilder.append("\n");
                }
            }
        }

        //Blank Document
        XWPFDocument document = new XWPFDocument();
        //Write the Document in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(
                    new File("D:/Dropbox/JAVA/RestForEmailingDoc/report.docx"));
            //create Paragraph
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText(stringBuilder.toString());

            document.write(out);
            out.close();
            System.out.println("Doc written successfully...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createXls() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sample sheet");

        Map<String, Object[]> data = new HashMap<String, Object[]>();
        data.put("1", new Object[]{"Emp No.", "Name", "Salary"});
        data.put("2", new Object[]{1d, "John", 1500000d});
        data.put("3", new Object[]{2d, "Sam", 800000d});
        data.put("4", new Object[]{3d, "Dean", 700000d});

        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof Date)
                    cell.setCellValue((Date) obj);
                else if (obj instanceof Boolean)
                    cell.setCellValue((Boolean) obj);
                else if (obj instanceof String)
                    cell.setCellValue((String) obj);
                else if (obj instanceof Double)
                    cell.setCellValue((Double) obj);
            }
        }

        try {

            FileOutputStream out = new FileOutputStream(
                    new File("D:/Dropbox/JAVA/RestForEmailingDoc/report.xls"));
            workbook.write(out);
            out.close();
            System.out.println("Excel written successfully...");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
