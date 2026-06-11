package org.example.warehouseservice.service.impl;

import org.example.domain.InBillDetailInfo;
import org.example.domain.InBillInfo;
import org.example.warehouseservice.domain.BMaterialStockTask;
//import org.example.warehouseservice.domain.StockTaskBuffer;
import org.example.warehouseservice.domain.StockTaskBuffer;
import org.example.warehouseservice.mapper.BMaterialStockTaskMapper;
import org.example.warehouseservice.service.BMaterialStockTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class BMaterialStockTaskServiceImpl implements BMaterialStockTaskService {
    @Autowired
    private BMaterialStockTaskMapper bMaterialStockTaskMapper;

    private final StockTaskBuffer buffer;

    public BMaterialStockTaskServiceImpl(StockTaskBuffer buffer) {
        this.buffer = buffer;
    }


    @Override
    public int createMaterialStockTask(InBillInfo inBillInfo) {
        for (InBillDetailInfo inBillDetailInfo : inBillInfo.getDetailInfoList()) {
            buffer.offer(build(inBillDetailInfo));
        }

        return 1;
    }
    public BMaterialStockTask build(InBillDetailInfo inBillDetailInfo){
        BMaterialStockTask bMaterialStockTask=  new BMaterialStockTask();
        bMaterialStockTask.setMaterialCode(inBillDetailInfo.getMaterialCode());
        bMaterialStockTask.setBillCode(inBillDetailInfo.getBillCode());
        bMaterialStockTask.setPositionCode(inBillDetailInfo.getPositionCode());
        bMaterialStockTask.setQty(inBillDetailInfo.getQty());
        return bMaterialStockTask;
    }
}
