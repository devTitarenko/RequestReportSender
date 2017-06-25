package com.github.titarenko.service.impl;

import com.github.titarenko.dao.impl.RequestDaoImpl;
import com.github.titarenko.model.DocumentFormat;
import com.github.titarenko.model.ReportItem;
import com.github.titarenko.model.Request;
import com.github.titarenko.model.User;
import com.github.titarenko.service.Reporter;
import com.github.titarenko.service.RequestService;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ReporterImpl implements Reporter {

    @Autowired
    private RequestService requestService;
    @Autowired
    private ServletContext servletContext;

    private String uploadPath;
    private static final String FILE_NAME = "report";
    private List<ReportItem> reportItemList;

    private static final Logger LOGGER = Logger.getLogger(RequestDaoImpl.class);

    @PostConstruct
    private void init() {
        LOGGER.info("Reporter initialized...");
        uploadPath = servletContext.getRealPath("") + File.separator;
        reportItemList = new ArrayList<>();
    }

    @Override
    public void createReport(DocumentFormat documentFormat, Date dateFilter) {
        LOGGER.info("Date Filter: " + dateFilter);
        List<Request> requestList = (dateFilter == null) ? requestService.getAllRequests() :
                requestService.getRequestsByDate(dateFilter);
        for (Request request : requestList) {
            User user = request.getSession().getUser();
            ReportItem reportItem = new ReportItem(
                    user.getLocation().getCountry().getCountryName(),
                    user.getLocation().getLocationName(),
                    user.getUserName(),
                    user.getUserGroup().getGroupName(),
                    request.getSession().getDateOpened(),
                    request.getSession().getDateClosed(),
                    request.getUrl(),
                    request.getMethod(),
                    request.getParams());
            reportItemList.add(reportItem);
        }

        Collections.sort(reportItemList);

        LOGGER.info("reportItemList.size(): " + reportItemList.size());
        if (reportItemList.isEmpty())
            return;

        switch (documentFormat) {
            case DOC:
                createDoc();
                break;
            case XLS:
                createXls();
                break;
            default:
                IllegalArgumentException e = new IllegalArgumentException("Invalid document format");
                LOGGER.error(e.getMessage() + ": " + documentFormat);
                throw e;
        }
    }

    private void createDoc() {
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();

        try (FileOutputStream out = new FileOutputStream(
                new File(uploadPath + FILE_NAME + ".doc"))) {

//            Header
            XWPFRun header = paragraph.createRun();
            header.setBold(true);
            StringBuilder stringBuilder = new StringBuilder();
            for (Object obj : ReportItem.header) {
                stringBuilder.append("/").append(obj);
            }
            header.setText(stringBuilder.toString());
            header.addBreak();

//            Content
            XWPFRun content = paragraph.createRun();
            for (ReportItem item : reportItemList) {
                stringBuilder = new StringBuilder();
                for (Object obj : item) {
                    stringBuilder.append("/").append(obj);
                }
                content.setText(stringBuilder.toString());
                content.addBreak();
            }

            document.write(out);
            LOGGER.info("Doc written successfully...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createXls() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Requests Report sheet");

        int rowNum = 0;
        int columnNum = 0;

//        Font for Header
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);

//        Header
        Row header = sheet.createRow(rowNum++);
        for (Object obj : ReportItem.header) {
            Cell cell = header.createCell(columnNum++);
            cell.setCellValue(obj.toString());
            cell.setCellStyle(style);
        }

//        Content
        for (ReportItem item : reportItemList) {
            Row row = sheet.createRow(rowNum++);
            columnNum = 0;
            for (Object obj : item) {
                Cell cell = row.createCell(columnNum++);
                cell.setCellValue(String.valueOf(obj));
            }
        }

        try (FileOutputStream out = new FileOutputStream(
                new File(uploadPath + FILE_NAME + ".xls"))) {
            workbook.write(out);
            LOGGER.info("Excel written successfully...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
