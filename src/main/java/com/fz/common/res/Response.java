package com.fz.common.res;

public class Response {
	
	private int retCode;
	private String errorMsg;
	private Object data;
	private Object other;
	private String name;
	
	public Response(){}
	
	public Response(int retCode){
		this.retCode = retCode;
	}
	public Response(int retCode,String errorMsg){
		this.retCode = retCode;
		this.errorMsg = errorMsg;
	}
	public Response(int retCode,Object data){
		this.retCode = retCode;
		this.data = data;
	}

	public int getRetCode() {
		return retCode;
	}

	public void setRetCode(int retCode) {
		this.retCode = retCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getOther() {
		return other;
	}

	public void setOther(Object other) {
		this.other = other;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
