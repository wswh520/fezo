package com.fz.enroll.enum0;

public enum AgeScope {

	LESS_THAN_6(0,6),//小于6岁
	GREATER_THAN_6(6,100),//大于6岁
	;
	private int begin;
	private int end;
	
	private AgeScope(int begin,int end){
		this.begin = begin;
		this.end = end;
	}
	public int begin(){
		return begin;
	}
	public int end(){
		return end;
	}
}
