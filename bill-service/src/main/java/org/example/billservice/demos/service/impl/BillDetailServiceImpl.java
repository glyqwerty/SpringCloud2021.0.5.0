package org.example.billservice.demos.service.impl;

import org.example.billservice.demos.mapper.InBillDetailMapper;
import org.example.billservice.demos.service.BillDetailService;
import org.example.billservice.demos.service.BillService;
import org.example.domain.InBillDetailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BillDetailServiceImpl implements BillDetailService {
    @Autowired
    private InBillDetailMapper billDetailMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<InBillDetailInfo> page(int startPoint, int size) {
        return billDetailMapper.page(startPoint,size);
    }

    @Override
    public int batchInsert(List<InBillDetailInfo> inBillDetailInfoList) {
        return billDetailMapper.inserInBillDetailList(inBillDetailInfoList);
    }

    @Override
    public void loadCsv(String absolutePath) {
        String path = absolutePath.replace("\\", "/");
        billDetailMapper.loadCsv(path);

    }

}
