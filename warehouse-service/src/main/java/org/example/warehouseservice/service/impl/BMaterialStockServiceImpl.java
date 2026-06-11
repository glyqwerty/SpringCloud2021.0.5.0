package org.example.warehouseservice.service.impl;

//import org.apache.dubbo.config.annotation.DubboService;

import org.example.domain.InBillInfo;
import org.example.domain.enumd.BillStatus;
import org.example.warehouseservice.domain.BMaterialStock;
import org.example.warehouseservice.mapper.BMaterialStockMapper;
import org.example.warehouseservice.mapper.BMaterialStockTaskMapper;
import org.example.warehouseservice.service.BMaterialStockService;
import org.example.warehouseservice.service.BMaterialStockTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@DubboService
@Service
public class BMaterialStockServiceImpl implements BMaterialStockService {

    @Autowired
    private BMaterialStockMapper bMaterialStockMapper;
    @Autowired
    private BMaterialStockTaskMapper bMaterialStockTaskMapper;

    @Autowired
    private BMaterialStockTaskService bMaterialStockTaskService;
    @Override
    public int createMaterialStock(BMaterialStock bMaterialStock) {
        return bMaterialStockMapper.createMaterialStock(bMaterialStock);
    }


    @Override
    public BMaterialStock getBMaterialStockByMaterialCodeAndPosition(String materialCode, String positionCode) {
        return bMaterialStockMapper.getBMaterialStockByMaterialCodeAndPositionCode(materialCode, positionCode);
    }

    @Override
    public int updateBMaterialStock(BMaterialStock bMaterialStock) {
        return bMaterialStockMapper.updateBMaterialStock(bMaterialStock);
    }

    @Override
    @Transactional
    public void inwarehouseBill(InBillInfo inBillInfo) {
        int i = bMaterialStockMapper.updateBillStatusByBillCode(inBillInfo.getBillCode(),BillStatus.INSTACK, BillStatus.COMPLETE);
        if(i==0){
            return  ;
        }
        bMaterialStockTaskService.createMaterialStockTask(inBillInfo);
    }
}
