package org.example.billservice.demos.web;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.billservice.demos.domain.AjaxResponse;
import org.example.billservice.demos.listener.ExcelListener;
import org.example.billservice.demos.listener.ExcelToCVSListener;
import org.example.billservice.demos.service.BillDetailService;
import org.example.billservice.demos.service.BillService;
import org.example.domain.InBillDetailInfo;
import org.example.domain.InBillInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/billdetail")
public class BillDetailController {
    private static final Logger log = LoggerFactory.getLogger(BillDetailController.class);
    @Autowired
    private BillDetailService detailService;



    @GetMapping("/export")
    public void exportBill(HttpServletResponse response) throws Exception {

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");

        String fileName = URLEncoder.encode("入库单", String.valueOf(StandardCharsets.UTF_8))
                .replaceAll("\\+", "%20");

        response.setHeader("Content-disposition",
                "attachment;filename*=utf-8''" + fileName + ".xlsx");

        ExcelWriter writer = EasyExcel.write(response.getOutputStream(), InBillDetailInfo.class).build();
        WriteSheet writerSheet = EasyExcel.writerSheet("订单").build();
        int startPoint=0;
        int size=5000;
        try {
            while (true) {
                List<InBillDetailInfo> page = detailService.page(startPoint, startPoint+size);
                if (page.isEmpty()) {
                    break;
                }
                writer.write(page, writerSheet);
                startPoint += size;
            }
        }finally {
            writer.finish();
        }

    }

    @PostMapping("/importExcel")
    public void importExcel(MultipartFile file) throws Exception {
        log.info("开始导入");
            EasyExcel.read(file.getInputStream(),
                    InBillDetailInfo.class,
                    new ExcelListener(detailService))
                    .sheet()
                    .doRead();

    }

    @PostMapping("importExcelLoad")
    public void importExcelLoad(MultipartFile file) throws IOException {
        log.info("开始导入");
        File csvFile = File.createTempFile("import-", ".csv");
        try{
        EasyExcel.read(file.getInputStream(),
                InBillDetailInfo.class,
                new ExcelToCVSListener(csvFile))
                .sheet().doRead();
        log.info(csvFile.getAbsolutePath());

        detailService.loadCsv(csvFile.getAbsolutePath());
        }catch (Exception e){

        }finally {
            log.info("导入完成");
             csvFile.delete();
        }
    }
}
