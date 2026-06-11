package org.example.warehouseservice.service;

import org.example.domain.InBillInfo;
import org.springframework.scheduling.annotation.Async;

public interface BMaterialStockTaskService {
    int createMaterialStockTask(InBillInfo inBillInfo);
}
