package org.example.billservice.demos.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.domain.InBillInfo;
import org.springframework.transaction.annotation.Transactional;

public interface BillService {
    /**
     * 插入入库单
     * @return
     */
    InBillInfo selectBillInfoByBillCode( String  billCode);
    int insertBill( InBillInfo  inBillInfo);
    // 修改入库单
    int updateBill(InBillInfo inBillInfo);
    // 删除入库单
    int deleteBill(String billCode);
    // 执行入库单
    int executeBill(String billCode);
    // 完成入库单
    int completeBill(String billCode) throws JsonProcessingException;

    int completeBillTran(String billCode) throws JsonProcessingException;
}
