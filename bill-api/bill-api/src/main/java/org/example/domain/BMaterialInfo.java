package org.example.domain;

import java.util.Map;

public class BMaterialInfo extends BaseDomain{
    private String materialCode;
    private String materialName;
    private String materialType;
    private Map<String,Object> extAttr;

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public Map<String, Object> getExtAttr() {
        return extAttr;
    }

    public void setExtAttr(Map<String, Object> extAttr) {
        this.extAttr = extAttr;
    }
}
