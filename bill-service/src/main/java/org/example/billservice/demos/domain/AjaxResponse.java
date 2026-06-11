package org.example.billservice.demos.domain;

public class AjaxResponse<T> {
    private String code;
    private String msg;
    private T data;
    public AjaxResponse() {

    }
    public AjaxResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public AjaxResponse(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     *
     * @param i 影响行数
     * @return
     */
    public static AjaxResponse toAjax(int i) {
        return i>0?success():error();

    }

    private static AjaxResponse error() {
        AjaxResponse response = new AjaxResponse();
        response.setCode("500");
        response.setMsg("操作失败");
        return null;
    }

    private static AjaxResponse success() {
        AjaxResponse response = new AjaxResponse();
        response.setCode("200");
        response.setMsg("操作成功");
        return response;
    }
}
