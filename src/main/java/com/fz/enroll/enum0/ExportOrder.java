package com.fz.enroll.enum0;

public enum ExportOrder {
	//a<==>tb_stuapply
	//c<==>tb_stuinfo
	ORDER_NO("a.type asc,a.no asc"),
	ORDER_CLASS("c.other10 asc,a.sex desc,a.pinyin asc"),
	ORDER_NUMBER("c.other7 asc"),
	;

	private String field;
	
	private ExportOrder(String field){
		this.field = field;
	}
	
	public String field(){
		return field;
	}
}
