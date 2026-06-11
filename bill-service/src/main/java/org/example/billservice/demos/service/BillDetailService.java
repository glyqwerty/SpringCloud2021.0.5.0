package org.example.billservice.demos.service;

import org.apache.ibatis.annotations.Param;
import org.example.domain.InBillDetailInfo;

import java.util.List;

public interface BillDetailService {
    List<InBillDetailInfo> page(int startPoint, int size);
    int batchInsert(@Param("list") List<InBillDetailInfo> inBillDetailInfoList);

    void loadCsv(String absolutePath);
}
