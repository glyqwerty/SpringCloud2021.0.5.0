package org.example.billservice.demos.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.domain.BMessageBox;

import java.util.List;

public interface BMessageBoxMapper {
    List<BMessageBox> noSuccess(int size);

    int inserBMessageBox(BMessageBox bMessageBox);

    int batchUpdateByBillCode(@Param("list") List<String> billCodes);
}
