package com.volley.req.parser;

import java.io.Serializable;

public class JsonParserBase<T> implements Serializable {
	//	private int code;
//	private String message;
	private T data;
	private String respCode;//
	private String respDesc;//错误描述
    private String serviceTime;//时间
//	public int getCode() {
//		return code;
//	}
//
//	public void setCode(int code) {
//		this.code = code;
//	}
//
//	public String getMessage() {
//		return message;
//	}
//
//	public void setMessage(String message) {
//		this.message = message;
//	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespDesc() {
		return respDesc;
	}

	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}

	public String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}

	@Override
	public String toString() {
		return "JsonParserBase [code=" + respCode + ", message=" + respDesc + ", data=" + data + "]";
	}

}
