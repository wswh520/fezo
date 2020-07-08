package com.fz.enroll.enum0;


public enum UserStatus {
	NORMAL(1),//正常
	DELETE(2),//已删除
	FORBIDDEN(3),//禁用
	;
	private int value;
	
	private UserStatus(int value){
		this.value = value;
	}

	public static UserStatus valueOf(Integer value){
		if(value==null){
			return null;
		}
		for(UserStatus type:values()){
			if(type.val()==value){
				return type;
			}
		}
		return null;
	}
	public int val(){
		return value;
	}
}
