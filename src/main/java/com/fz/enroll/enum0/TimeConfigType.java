package com.fz.enroll.enum0;

public enum TimeConfigType {
	SERVE(1),//服务时段
	SIGN_IN(2),//注册时段
	APPLY_INPUT(3),//填表时段
	APPLY_MODIFY(4),//改表时段
	INFO_INPUT(5),//信息录入时段
	GRA_SERVE(11),//毕业生服务时段
	GRA_INFO_INPUT(12),//毕业生信息录入时段
	;
	private int value;
	
	private TimeConfigType(int value){
		this.value = value;
	}

	public static TimeConfigType valueOf(Integer value){
		if(value==null){
			return null;
		}
		for(TimeConfigType type:values()){
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
