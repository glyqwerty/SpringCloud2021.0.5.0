package org.example.billservice.demos.web;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.billservice.demos.domain.AjaxResponse;

import org.example.billservice.demos.service.BillService;
import org.example.domain.InBillDetailInfo;
import org.example.domain.InBillInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bill")
public class BillController {
    @Autowired
    private BillService billService;

    @PostMapping
    @Transactional
    public AjaxResponse bill(@RequestBody InBillInfo inBillInfo) {
        return  AjaxResponse.toAjax(  billService.insertBill(inBillInfo));
    }

    @PutMapping("/complete/{billCode}")
    public  AjaxResponse completeBill(@PathVariable("billCode") String billCode) throws JsonProcessingException {

        return  AjaxResponse.toAjax(billService.completeBillTran(billCode));
    }
    @PutMapping("/execute/{billCode}")
    public  AjaxResponse executeBill(@PathVariable("billCode") String billCode) {

        return  AjaxResponse.toAjax(billService.executeBill(billCode));
    }


    @GetMapping("/{billCode}")
    public AjaxResponse bill(@PathVariable String billCode) {
        AjaxResponse response = new AjaxResponse();
        response.setCode("200");
        response.setMsg("success");
        response.setData(billService.selectBillInfoByBillCode(billCode));
        return response;
    }
}
