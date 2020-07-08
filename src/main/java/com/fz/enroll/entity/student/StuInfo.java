package com.fz.enroll.entity.student;

import com.fz.common.util.DateUtils;
import com.fz.common.util.Utils;
import com.fz.enroll.entity.BaseEntity;
import com.fz.enroll.enum0.CardType;
import com.fz.enroll.enum0.StuApplyStatus;

public class StuInfo extends BaseEntity {

	private boolean locked;//是否锁定了，true:锁定了不能修改
	private StuApply stuApply;
	public boolean getLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public StuApply getStuApply() {
		return stuApply;
	}
	public void setStuApply(StuApply stuApply) {
		this.stuApply = stuApply;
	}
	public StuApplyStatus getStatusStr(){
		return StuApplyStatus.valueOf(this.getStatus());
	}
	public CardType getOther45Str(){
		return CardType.valueOf(this.getOther45());
	}
	public CardType getOther57Str(){
		return CardType.valueOf(this.getOther57());
	}
	
	private int stuId;
	private int status = 1;//状态：未提交、初次提交
	private String addressOfBirth;
	private String other1;//姓名拼音
	private String other2;//曾用名
	private String other3;//身份证件有效期
	private String other4;//户口所在地
	private String other5;//户口性质
	private String other6;//特长
	private String other7;//学籍辅号
	private String other8;//班内学号
	private String other9 = Utils.connectString(DateUtils.getCurrentYear(),"级");//年级
	private String other10;//班级
	private String other11 = Utils.connectString(DateUtils.getCurrentYear(),"09");//入学年月
	private String other12 = "就近入学";//入学方式
	private String other13 = "走读";//就读方式
	private String other14 = "正常入学";//学生来源
	//学生个人联系信息
	private String other15;//现住址
	private String other16;//通信地址
	private String other17;//家庭地址
	private String other18;//联系电话
	private String other19;//邮政编码
	private String other20;//电子信箱
	private String other21;//主页地址
	//学生个人扩展信息
	private String other22;//是否独生子女
	private String other23;//是否受过学前教育
	private String other24 = "非留守儿童";//是否留守儿童
	private String other25;//是否进城务工人员随迁子女
	private String other26;//是否孤儿
	private String other27;//是否烈士或优抚子女
	private String other28;//随班就读
	private String other29;//残疾类型
	private String other30 = "否";//是否由政府购买学位
	private String other31 = "否";//是否需要申请资助
	private String other32 = "否";//是否享受一补
	//学生上下学交通方式
	private String other33;//上下学距离
	private String other34;//上下学交通方式
	private String other35;//是否需要乘坐校车
	//学生家庭成员或监护人信息一
	private String other36;//姓名
	private String other37 = "父亲";//关系
	private String other38 = "父亲";//关系说明
	private String other39;//民族
	private String other40;//工作单位
	private String other41;//现住址
	private String other42;//户口所在地
	private String other43;//联系电话
	private String other44 = "是";//是否监护人
	private String other45 = "CARD_TYPE1";//证件类型
	private String other46;//身份证件号
	private String other47;//职务
	//学生家庭成员或监护人信息二
	private String other48;//姓名
	private String other49 = "母亲";//关系
	private String other50 = "母亲";//关系说明
	private String other51;//民族
	private String other52;//工作单位
	private String other53;//现住址
	private String other54;//户口所在地
	private String other55;//联系电话
	private String other56 = "是";//是否监护人
	private String other57 = "CARD_TYPE1";//证件类型
	private String other58;//身份证件号
	private String other59;//职务
	public int getStuId() {
		return stuId;
	}
	public void setStuId(int stuId) {
		this.stuId = stuId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAddressOfBirth() {
		return addressOfBirth;
	}
	public void setAddressOfBirth(String addressOfBirth) {
		this.addressOfBirth = addressOfBirth;
	}
	public String getOther1() {
		return other1;
	}
	public void setOther1(String other1) {
		this.other1 = other1;
	}
	public String getOther2() {
		return other2;
	}
	public void setOther2(String other2) {
		this.other2 = other2;
	}
	public String getOther3() {
		return other3;
	}
	public void setOther3(String other3) {
		this.other3 = other3;
	}
	public String getOther4() {
		return other4;
	}
	public void setOther4(String other4) {
		this.other4 = other4;
	}
	public String getOther5() {
		return other5;
	}
	public void setOther5(String other5) {
		this.other5 = other5;
	}
	public String getOther6() {
		return other6;
	}
	public void setOther6(String other6) {
		this.other6 = other6;
	}
	public String getOther7() {
		return other7;
	}
	public void setOther7(String other7) {
		this.other7 = other7;
	}
	public String getOther8() {
		return other8;
	}
	public void setOther8(String other8) {
		this.other8 = other8;
	}
	public String getOther9() {
		return other9;
	}
	public void setOther9(String other9) {
		this.other9 = other9;
	}
	public String getOther10() {
		return other10;
	}
	public void setOther10(String other10) {
		this.other10 = other10;
	}
	public String getOther11() {
		return other11;
	}
	public void setOther11(String other11) {
		this.other11 = other11;
	}
	public String getOther12() {
		return other12;
	}
	public void setOther12(String other12) {
		this.other12 = other12;
	}
	public String getOther13() {
		return other13;
	}
	public void setOther13(String other13) {
		this.other13 = other13;
	}
	public String getOther14() {
		return other14;
	}
	public void setOther14(String other14) {
		this.other14 = other14;
	}
	public String getOther15() {
		return other15;
	}
	public void setOther15(String other15) {
		this.other15 = other15;
	}
	public String getOther16() {
		return other16;
	}
	public void setOther16(String other16) {
		this.other16 = other16;
	}
	public String getOther17() {
		return other17;
	}
	public void setOther17(String other17) {
		this.other17 = other17;
	}
	public String getOther18() {
		return other18;
	}
	public void setOther18(String other18) {
		this.other18 = other18;
	}
	public String getOther19() {
		return other19;
	}
	public void setOther19(String other19) {
		this.other19 = other19;
	}
	public String getOther20() {
		return other20;
	}
	public void setOther20(String other20) {
		this.other20 = other20;
	}
	public String getOther21() {
		return other21;
	}
	public void setOther21(String other21) {
		this.other21 = other21;
	}
	public String getOther22() {
		return other22;
	}
	public void setOther22(String other22) {
		this.other22 = other22;
	}
	public String getOther23() {
		return other23;
	}
	public void setOther23(String other23) {
		this.other23 = other23;
	}
	public String getOther24() {
		return other24;
	}
	public void setOther24(String other24) {
		this.other24 = other24;
	}
	public String getOther25() {
		return other25;
	}
	public void setOther25(String other25) {
		this.other25 = other25;
	}
	public String getOther26() {
		return other26;
	}
	public void setOther26(String other26) {
		this.other26 = other26;
	}
	public String getOther27() {
		return other27;
	}
	public void setOther27(String other27) {
		this.other27 = other27;
	}
	public String getOther28() {
		return other28;
	}
	public void setOther28(String other28) {
		this.other28 = other28;
	}
	public String getOther29() {
		return other29;
	}
	public void setOther29(String other29) {
		this.other29 = other29;
	}
	public String getOther30() {
		return other30;
	}
	public void setOther30(String other30) {
		this.other30 = other30;
	}
	public String getOther31() {
		return other31;
	}
	public void setOther31(String other31) {
		this.other31 = other31;
	}
	public String getOther32() {
		return other32;
	}
	public void setOther32(String other32) {
		this.other32 = other32;
	}
	public String getOther33() {
		return other33;
	}
	public void setOther33(String other33) {
		this.other33 = other33;
	}
	public String getOther34() {
		return other34;
	}
	public void setOther34(String other34) {
		this.other34 = other34;
	}
	public String getOther35() {
		return other35;
	}
	public void setOther35(String other35) {
		this.other35 = other35;
	}
	public String getOther36() {
		return other36;
	}
	public void setOther36(String other36) {
		this.other36 = other36;
	}
	public String getOther37() {
		return other37;
	}
	public void setOther37(String other37) {
		this.other37 = other37;
	}
	public String getOther38() {
		return other38;
	}
	public void setOther38(String other38) {
		this.other38 = other38;
	}
	public String getOther39() {
		return other39;
	}
	public void setOther39(String other39) {
		this.other39 = other39;
	}
	public String getOther40() {
		return other40;
	}
	public void setOther40(String other40) {
		this.other40 = other40;
	}
	public String getOther41() {
		return other41;
	}
	public void setOther41(String other41) {
		this.other41 = other41;
	}
	public String getOther42() {
		return other42;
	}
	public void setOther42(String other42) {
		this.other42 = other42;
	}
	public String getOther43() {
		return other43;
	}
	public void setOther43(String other43) {
		this.other43 = other43;
	}
	public String getOther44() {
		return other44;
	}
	public void setOther44(String other44) {
		this.other44 = other44;
	}
	public String getOther45() {
		return other45;
	}
	public void setOther45(String other45) {
		this.other45 = other45;
	}
	public String getOther46() {
		return other46;
	}
	public void setOther46(String other46) {
		this.other46 = other46;
	}
	public String getOther47() {
		return other47;
	}
	public void setOther47(String other47) {
		this.other47 = other47;
	}
	public String getOther48() {
		return other48;
	}
	public void setOther48(String other48) {
		this.other48 = other48;
	}
	public String getOther49() {
		return other49;
	}
	public void setOther49(String other49) {
		this.other49 = other49;
	}
	public String getOther50() {
		return other50;
	}
	public void setOther50(String other50) {
		this.other50 = other50;
	}
	public String getOther51() {
		return other51;
	}
	public void setOther51(String other51) {
		this.other51 = other51;
	}
	public String getOther52() {
		return other52;
	}
	public void setOther52(String other52) {
		this.other52 = other52;
	}
	public String getOther53() {
		return other53;
	}
	public void setOther53(String other53) {
		this.other53 = other53;
	}
	public String getOther54() {
		return other54;
	}
	public void setOther54(String other54) {
		this.other54 = other54;
	}
	public String getOther55() {
		return other55;
	}
	public void setOther55(String other55) {
		this.other55 = other55;
	}
	public String getOther56() {
		return other56;
	}
	public void setOther56(String other56) {
		this.other56 = other56;
	}
	public String getOther57() {
		return other57;
	}
	public void setOther57(String other57) {
		this.other57 = other57;
	}
	public String getOther58() {
		return other58;
	}
	public void setOther58(String other58) {
		this.other58 = other58;
	}
	public String getOther59() {
		return other59;
	}
	public void setOther59(String other59) {
		this.other59 = other59;
	}
}
