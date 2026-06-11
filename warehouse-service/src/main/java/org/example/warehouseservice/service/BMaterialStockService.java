package org.example.warehouseservice.service;

import org.apache.ibatis.annotations.Param;
import org.example.domain.InBillInfo;
import org.example.warehouseservice.domain.BMaterialStock;


public interface BMaterialStockService {
    int createMaterialStock(BMaterialStock bMaterialStock);
    BMaterialStock getBMaterialStockByMaterialCodeAndPosition(String materialCode, String positionCode);
    int updateBMaterialStock(BMaterialStock bMaterialStock);


    void inwarehouseBill(InBillInfo inBillInfo);
}
