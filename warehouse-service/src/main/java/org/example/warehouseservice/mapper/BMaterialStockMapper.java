package org.example.warehouseservice.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.domain.enumd.BillStatus;
import org.example.warehouseservice.domain.BMaterialStock;


public interface BMaterialStockMapper {
    int createMaterialStock(BMaterialStock bMaterialStock);
    BMaterialStock getBMaterialStockByMaterialCodeAndPositionCode(@Param("materialCode") String materialCode, @Param("positionCode") String positionCode);
    int updateBMaterialStock(BMaterialStock bMaterialStock);

    int updateBillStatusByBillCode(@Param("billCode") String billCode, @Param("status") BillStatus status, @Param("oldStatus") BillStatus oldStatus);
}
