package org.example.domain;


import org.example.domain.enumd.BillStatus;

import java.util.List;

public class InBillInfo extends BaseDomain {
    private String billCode;
    private String billType;
    // 取值只有0(新建) 1(执行中) 2(已完成)，
    private BillStatus status;
    private List<InBillDetailInfo> detailInfoList;


    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public BillStatus getStatus() {
        return status;
    }

    public void setStatus(BillStatus status) {
        this.status = status;
    }

    public List<InBillDetailInfo> getDetailInfoList() {
        return detailInfoList;
    }

    public void setDetailInfoList(List<InBillDetailInfo> detailInfoList) {
        this.detailInfoList = detailInfoList;
    }
}
