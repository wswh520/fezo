package com.fz.enroll.enum0;

public enum SexEnum {

	FALSE(0,"女"),
	TRUE(1,"男"),
	;
	private int value;
	private String alias;
	
	private SexEnum(int value,String alias){
		this.value = value;
		this.alias = alias;
	}

	public static SexEnum valueOf(Integer value){
		if(value==null){
			return null;
		}
		for(SexEnum type:values()){
			if(type.val()==value){
				return type;
			}
		}
		return null;
	}
	public static SexEnum valueOfAlias(String alias){
		if(alias==null){
			return null;
		}
		for(SexEnum type:values()){
			if(type.alias.equals(alias)){
				return type;
			}
		}
		return null;
	}
	public int val(){
		return value;
	}
}
