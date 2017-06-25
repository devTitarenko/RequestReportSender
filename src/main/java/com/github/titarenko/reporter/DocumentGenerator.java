package com.github.titarenko.reporter;

import com.github.titarenko.dao.impl.RequestDaoImpl;
import com.github.titarenko.model.DocumentFormat;
import com.github.titarenko.model.ReportItem;
import com.github.titarenko.model.Request;
import com.github.titarenko.model.User;
import com.github.titarenko.service.RequestService;
import com.sun.org.apache.xml.internal.serialize.LineSeparator;
import org.apache.log4j.Logger;
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

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DocumentGenerator {

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
        LOGGER.info("DocumentGenerator initialized...");
        uploadPath = servletContext.getRealPath("") + File.separator;
        reportItemList = new ArrayList<>();
    }

    public void createReport(DocumentFormat documentFormat, Date dateFilter) {
//        reportItemList.add(new Object[]{"Country", "Location", "User name",
//                "User group", "Session date opened", "Session date closed",
//                "Request URL", "Request method", "Request params"});

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
        switch (documentFormat) {
            case DOC:
                createDoc();
                break;
            case XLS:
                createXls();
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    private void createDoc() {
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();

        try (FileOutputStream out = new FileOutputStream(
                new File(uploadPath + FILE_NAME + ".doc"))) {

            for (ReportItem item : reportItemList) {
                StringBuilder stringBuilder = new StringBuilder();
                for (Object obj : item) {
                    stringBuilder.append("/").append(obj);
                }
                run.setText(stringBuilder.toString());
                run.addBreak();
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
        for (ReportItem item : reportItemList) {
            Row row = sheet.createRow(rowNum++);
            int columnNum = 0;
            for (Object obj : item) {
                Cell cell = row.createCell(columnNum++);
                if (obj instanceof Date)
                    cell.setCellValue(obj.toString());
                else if (obj instanceof Boolean)
                    cell.setCellValue((Boolean) obj);
                else if (obj instanceof String)
                    cell.setCellValue((String) obj);
                else if (obj instanceof Long)
                    cell.setCellValue((Long) obj);
                else if (obj instanceof Double)
                    cell.setCellValue((Double) obj);
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
