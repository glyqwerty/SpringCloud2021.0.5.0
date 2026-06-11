package org.example.billservice.demos.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import org.example.billservice.demos.service.BillDetailService;
import org.example.domain.InBillDetailInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ExcelListener extends AnalysisEventListener<InBillDetailInfo> {

    private static final int Bath_SIZE=5000;
    private static final Logger log = LoggerFactory.getLogger(ExcelListener.class);

    private BillDetailService billDetailService;

    private List<InBillDetailInfo> cacheList =
            new ArrayList<>(Bath_SIZE);
    public ExcelListener(BillDetailService detailService) {
        this.billDetailService = detailService;
    }

    @Override
    public void invoke(InBillDetailInfo data, AnalysisContext context) {
//        ReadRowHolder readRowHolder = context.readRowHolder();
//        log.info(readRowHolder.getRowIndex()+"");
        cacheList.add(data);
        if(cacheList.size()>=Bath_SIZE){
            doSave(cacheList);
            cacheList.clear();
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if(cacheList.size()>0){
            doSave(cacheList);
        }
        log.info("导入完成");
    }
    private void doSave(List<InBillDetailInfo> cacheList){
        billDetailService.batchInsert(cacheList);
    }
}
