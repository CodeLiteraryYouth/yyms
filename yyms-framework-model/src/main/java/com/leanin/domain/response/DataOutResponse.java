package com.leanin.domain.response;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class DataOutResponse implements Serializable {

	/**
     * 返回客户端统一格式，包括状态码，提示信息，以及业务数据
     */
    private static final long serialVersionUID = 1L;
    //状态码
    private int status;
    //必要的提示信息
    private String message;
    //业务数据
    private Object data;

    public DataOutResponse(int status,String message,Object data){
        this.status = status;
        this.message = message;
        this.data = data;
    }
    
    public DataOutResponse(String message) {
		super();
		this.message = message;
	}

	public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }  
    @Override
    public String toString(){
        if(null == this.data){
            this.setData(new Object());
        }
        return JSON.toJSONString(this);
    }
}

