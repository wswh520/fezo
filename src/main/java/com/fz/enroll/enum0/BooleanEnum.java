package com.fz.enroll.enum0;

public enum BooleanEnum {

	FALSE(0,"否"),
	TRUE(1,"是"),
	;
	private int value;
	private String alias;
	
	private BooleanEnum(int value,String alias){
		this.value = value;
		this.alias = alias;
	}

	public static BooleanEnum valueOf(Integer value){
		if(value==null){
			return null;
		}
		for(BooleanEnum type:values()){
			if(type.val()==value){
				return type;
			}
		}
		return null;
	}
	public static BooleanEnum valueOfAlias(String alias){
		if(alias==null){
			return null;
		}
		for(BooleanEnum type:values()){
			if(type.alias.equals(alias)){
				return type;
			}
		}
		return null;
	}
	public int val(){
		return value;
	}
	public String alias() {
		return alias;
	}
}
