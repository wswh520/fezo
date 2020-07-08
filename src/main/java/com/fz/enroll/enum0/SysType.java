package com.fz.enroll.enum0;


public enum SysType {
	xs(1),//新生
	bys(2),//毕业生
	;
	private int value;
	
	private SysType(int value){
		this.value = value;
	}

	public static SysType valueOf(int value){
		for(SysType type:SysType.values()){
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
