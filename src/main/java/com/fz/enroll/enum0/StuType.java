package com.fz.enroll.enum0;

public enum StuType {
	TYPEA(1,"A华师第二代生源","A","1子弟"),//A华师第二代生源
	TYPEB(2,"B华师第三代生源","B","2第三代"),//B华师大第三代
	TYPEC(3,"C社会对口生源","C","3其他生源"),//C其他
	;
	private int value;
	private String alias;
	private String shortAlias;
	private String graAlias;
	
	private StuType(int value,String alias,String shortAlias,String graAlias){
		this.value = value;
		this.alias = alias;
		this.shortAlias = shortAlias;
		this.graAlias = graAlias;
	}

	public static StuType valueOf(Integer value){
		if(value==null){
			return null;
		}
		for(StuType type:values()){
			if(type.val()==value){
				return type;
			}
		}
		return null;
	}
	public static StuType valueOfGraAlias(String alias){
		if(alias==null){
			return null;
		}
		for(StuType type:values()){
			if(type.graAlias.equals(alias)){
				return type;
			}
		}
		return null;
	}
	public int val(){
		return value;
	}
	public String alias(){
		return alias;
	}
	public String shortAlias(){
		return shortAlias;
	}
	public String graAlias(){
		return graAlias;
	}
}
