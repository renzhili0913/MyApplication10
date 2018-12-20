package com.example.renzhili20181220.bean;

public class AddBean {

    /**
     * msg : 加购成功
     * code : 0
     */

    private String msg;
    private String code;
    public boolean isSuccess(){
        return code.equals("0");
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
