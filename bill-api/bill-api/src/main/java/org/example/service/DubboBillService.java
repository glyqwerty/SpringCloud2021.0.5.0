package org.example.service;

import org.example.domain.InBillInfo;

public interface DubboBillService {
    int inwarehouseBill(String billCode);
    InBillInfo getBillInfoByBillCode(String billCode);
}
