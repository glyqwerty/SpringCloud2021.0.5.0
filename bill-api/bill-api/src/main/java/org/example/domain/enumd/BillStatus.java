package org.example.domain.enumd;

import com.alibaba.fastjson2.annotation.JSONCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum BillStatus {

    NEW(0,"新建"),
    EXECUTING(1,"执行中"),
    COMPLETE(2,"完成"),
    CANCEL(3,"已取消"),
    INSTACK(4,"已入库");
    private final int code;
    private final String desc;

    BillStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    // 3. JSON 序列化（枚举 -> 前端）
    @Override
    @JsonValue
    public String toString() {
        return this.name();
    }

    /**
     * 增加 @JsonCreator 注解：解决“前端传字符串 'NEW' 或数字 0 如何自动反序列化”的问题。
     * @param value
     * @return
     */
    @JSONCreator
    public static BillStatus fromValue(Object value) {
        if(value instanceof String){
            try {
                return BillStatus.valueOf((String) value);
            }catch (IllegalArgumentException  e){
                throw new IllegalArgumentException("未知状态码");
            }

        }else if(value instanceof Number){
            int val = ((Number)value).intValue();
            return Arrays.stream(BillStatus.values()).filter(e -> e.code == val).findFirst().orElseThrow(()->new IllegalArgumentException("未知状态码"));
        }
        throw new IllegalArgumentException("不支持的参数类型");
    }

    public static BillStatus getByCode(int code) {
        return Arrays.asList(BillStatus.values()).stream().filter(e->e.code == code).findFirst().orElseThrow(()->new IllegalArgumentException("不存在该状态码"));
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
