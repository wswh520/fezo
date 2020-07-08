package com.fz.enroll.enum0;


public enum DataField {
	FIELD_XJFH("other7","学籍辅号"),
	FIELD_BNXH("other8","班内学号"),
	FIELD_BJ("other10","班级"),
	;
	private String field;
	private String alias;
	
	private DataField(String field,String alias){
		this.field = field;
		this.alias = alias;
	}
	
	public String field(){
		return field;
	}
	public String alias(){
		return alias;
	}
}
