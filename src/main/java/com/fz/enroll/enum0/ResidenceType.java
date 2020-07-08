package com.fz.enroll.enum0;


public enum ResidenceType {
	REGULAR(1,"正式"),
	UNREGULAR(2,"搭户"),
	;
	private int value;
	private String alias;
	
	private ResidenceType(int value,String alias){
		this.value = value;
		this.alias = alias;
	}

	public static ResidenceType valueOf(Integer value){
		if(value==null){
			return null;
		}
		for(ResidenceType type:values()){
			if(type.val()==value){
				return type;
			}
		}
		return null;
	}
	
	public static ResidenceType valueOfAlias(String alias){
		if(alias==null){
			return null;
		}
		for(ResidenceType type:values()){
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
