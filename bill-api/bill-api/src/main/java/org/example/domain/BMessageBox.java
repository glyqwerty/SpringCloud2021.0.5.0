package org.example.domain;

import java.io.Serializable;

public class BMessageBox implements Serializable {
    private String billCode;
    private String body;

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
