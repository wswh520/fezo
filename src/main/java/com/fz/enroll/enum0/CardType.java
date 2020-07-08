package com.fz.enroll.enum0;

public enum CardType {
	CARD_TYPE1(1,"居民身份证"),//居民身份证
	CARD_TYPE2(2,"香港特区护照/身份证明"),//香港特区护照/身份证明
	CARD_TYPE3(3,"澳门特区护照/身份证明"),//澳门特区护照/身份证明
	CARD_TYPE4(4,"台湾居民来往大陆通行证"),//台湾居民来往大陆通行证
	CARD_TYPE5(5,"境外永久居住证"),//境外永久居住证
	CARD_TYPE6(6,"护照"),//护照
	CARD_TYPE7(7,"其他"),//其他
	;
	private int value;
	private String alias;
	
	private CardType(int value,String alias){
		this.value = value;
		this.alias = alias;
	}

	public static CardType valueOf(Integer value){
		if(value==null){
			return null;
		}
		for(CardType type:values()){
			if(type.val()==value){
				return type;
			}
		}
		return null;
	}
	public int val(){
		return value;
	}
	public String alias(){
		return this.alias;
	}
}
