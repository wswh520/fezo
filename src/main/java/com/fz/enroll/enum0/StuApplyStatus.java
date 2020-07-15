package com.fz.enroll.enum0;

public enum StuApplyStatus {
	TABLE_EMPTY(-1),//未填表，只作为查询状态
	SUBMIT_NONE(1),//未提交
	SUBMIT_ONCE(2),//初次提交
	SUBMIT_TWICE(3),//再次提交
	REVIEW_PASS(4),//通过、毕业生信息需要二次审核
	REVIEW_REFUSE(5),//不通过、毕业生信息审核不通过
	REVIEW_WAITING(6),//待通过
//	EXAMINE_WAITING(7),//审核中
//	EXAMINE_ALREADY(8),//已审核
//	EXAMINE_REFUSE(9),//审核未通过
	;
	private int value;
	
	private StuApplyStatus(int value){
		this.value = value;
	}

	public static StuApplyStatus valueOf(Integer value){
		if(value==null){
			return null;
		}
		for(StuApplyStatus type:values()){
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
