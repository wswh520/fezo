package com.fz.enroll.enum0;


public enum GraduateImportField {
	xbxh("校编学号"),
	bj("班级"),
	xm("学生姓名"),
	xb("性别"),
	jg1("籍贯省"),
	jg2("籍贯市"),
	csd("出生地"),
	mz("民族"),
	hkxz("户口性质"),
	sfzh("身份证号"),
	dz("独生子女"),
	sl("视力残疾"),
	tl("听力残疾"),
	zl("智力残疾"),
	jzxz("居住性质"),
	jtzz("居住地址"),
	hkszd("户口所在地"),
	hkdz("户口地址"),
	fuxm("父亲姓名"),
	fudw("父亲工作单位"),
	fudh("父亲电话"),
	muxm("母亲姓名"),
	mudw("母亲工作单位"),
	mudh("母亲电话"),
	sylb("生源类别"),
	;
	private String alias;
	
	private GraduateImportField(String alias){
		this.alias = alias;
	}
	public String alias(){
		return alias;
	}
}
