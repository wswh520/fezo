package com.fz.enroll.enum0;


public enum UserType {
	SUPER(1),//超级管理员
	ADMIN(2),//学校管理员(学籍管理员）
	PATRIARCH(3),//家长
	GRADUATE(4),//毕业生
	RECRUIT_TEACHER(5),//招生老师
	HEALTH_TEACHER(6),     //保健老师
	GRADUATE_TEACHER(7), //毕业班主任  根据班级，年份 只能看到自己的学生信息
	;
	private int value;
	
	private UserType(int value){
		this.value = value;
	}

	public static UserType valueOf(int value){
		for(UserType type:UserType.values()){
			if(type.val()==value){
				return type;
			}
		}
		return null;
	}
	public int val(){
		return value;
	}

	public static boolean isTecherUser(int value){
	    return value == UserType.ADMIN.val() ||
                value == UserType.RECRUIT_TEACHER.val() ||
                value == UserType.HEALTH_TEACHER.val() ||
                value == UserType.GRADUATE_TEACHER.val();
    }
}
