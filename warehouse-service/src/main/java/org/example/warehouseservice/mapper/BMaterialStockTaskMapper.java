package org.example.warehouseservice.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.domain.InBillDetailInfo;
import org.example.warehouseservice.domain.BMaterialStock;
import org.example.warehouseservice.domain.BMaterialStockTask;

import java.util.List;

public interface BMaterialStockTaskMapper {

    int batchMaterialStockTask(@Param("list") List<InBillDetailInfo> detailInfoList);
    List<BMaterialStockTask> listMaterialStockTask();

    void batchBMaterialStockTaskInsert(@Param("list") List<BMaterialStockTask> batch);
}
