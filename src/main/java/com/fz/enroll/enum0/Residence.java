package com.fz.enroll.enum0;


public enum Residence {
	INNERCITY(1,"市内"),
	INNERPROVINCE(2,"省内"),
	OUTPROVINCE(3,"省外"),
	;
	private int value;
	private String alias;
	
	private Residence(int value,String alias){
		this.value = value;
		this.alias = alias;
	}

	public static Residence valueOf(Integer value){
		if(value==null){
			return null;
		}
		for(Residence type:values()){
			if(type.val()==value){
				return type;
			}
		}
		return null;
	}
	
	public static Residence valueOfAlias(String alias){
		if(alias==null){
			return null;
		}
		for(Residence type:values()){
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
