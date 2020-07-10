package com.fz.enroll.enum0;

/**
 * 附件所属对象类型
 * @author 
 *
 */
public enum AttOtype {
	TYPE_NONE (1),//未指定	
	TYPE_GRASTUINFO(2),//毕业生照片
	//增加：以下为新生招生信息照片
	TYPE_CHILDREN_PHOTO(10), // 儿童的登记照
	TYPE_CHILDREN_HK_INDEXPAGE(11), // 户口首页（有地址的那一页）
	TYPE_CHILDREN_HOUSEHOLDERPAGE(12), // 户主页
	TYPE_CHILDREN_PAGE(13), // 儿童主页
	TYPE_CHILDREN_BIRTH_CERTIFICATE(14), // 儿童出生证

		// 6、补充类：
	//TYPE_PARENT_ACCOUNT_PAGE(15), // 父母户口页
	TYPE_FATHER_ACCOUNT_PAGE(16), // 父亲户口页
	TYPE_MOTHER_ACCOUNT_PAGE(17), // 母亲户口页
	TYPE_THREE_GENERATION_RELATIONSHIP(18),// 三代关系证明
	TYPE_WORK_PROVE(19),//华师教职工校园一卡通
	TYPE_FATHER_ACCOUNT_LEARN_PAGE(20),//父亲最高学历证明
	TYPE_MOTHER_ACCOUNT_LEARN_PAGE(21),//母亲最高学历证明
	TYPE_WORK_SCHOOL_PROVE(22),//入学通知书
//		①父母户口页
//		②三代关系证明
	;
	private int value;
	
	private AttOtype(int value){
		this.value = value;
	}
	
	public int val(){
		return value;
	}
	
	public static AttOtype valueOf(Integer value){
		if(value==null){
			return null;
		}
		for(AttOtype type:values()){
			if(type.val()==value){
				return type;
			}
		}
		return null;
	}
}
