package org.example.billservice.demos.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.example.domain.InBillDetailInfo;

import java.util.List;

public interface InBillDetailMapper {
    List<InBillDetailInfo> listBillDetailByBillCode(String billCode);

    int inserInBillDetailList(@Param("list") List<InBillDetailInfo> list);

    List<InBillDetailInfo> page(@Param("startPoint") Integer startPoint,@Param("size") Integer size);


    @Update({
            "LOAD DATA LOCAL INFILE '${path}'",
            "INTO TABLE b_inbilldetail",
            "FIELDS TERMINATED BY ','",
            "LINES TERMINATED BY '\\n'",
            "(billcode, materialcode, materialname, qty)"
    })
    void loadCsv(@Param("path") String path);
}
