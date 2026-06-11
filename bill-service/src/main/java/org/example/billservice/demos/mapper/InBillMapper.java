package org.example.billservice.demos.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.domain.InBillInfo;
import org.example.domain.enumd.BillStatus;

public interface InBillMapper {
    public int inserInBillInfo(InBillInfo inBillInfo);

    InBillInfo selectBillInfoByBillCode(String billCode);

    int updateBillStatusByBillCode(@Param("billCode") String billCode, @Param("status") BillStatus status, @Param("oldStatus")  BillStatus oldStatus);


}
