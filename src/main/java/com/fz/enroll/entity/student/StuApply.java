package com.fz.enroll.entity.student;

import com.fz.common.util.DateUtils;
import com.fz.common.util.Utils;
import com.fz.enroll.entity.BaseEntity;
import com.fz.enroll.enum0.BooleanEnum;
import com.fz.enroll.enum0.CardType;
import com.fz.enroll.enum0.StuApplyStatus;
import com.fz.enroll.enum0.StuType;

public class StuApply extends BaseEntity {
	public static final String TIME_FORMAT = "yyyy年MM月dd日";
	public static final String TIME_FORMAT_SHORT = "yyyyMMdd";
	
	private boolean locked;//是否锁定了，true:锁定了不能修改
	private String mobile;//家长手机号
	private Integer infoStatus;//学籍信息表状态
	private String classNo;
	private Integer vaccineStatus;//预防接种表状态
	public boolean getLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getInfoStatus() {
		return infoStatus;
	}
	public void setInfoStatus(Integer infoStatus) {
		this.infoStatus = infoStatus;
	}
	public String getClassNo() {
		return classNo;
	}
	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}
	public Integer getVaccineStatus() {
		return vaccineStatus;
	}
	public void setVaccineStatus(Integer vaccineStatus) {
		this.vaccineStatus = vaccineStatus;
	}
	
	public StuApplyStatus getStatusStr(){
		return StuApplyStatus.valueOf(this.getStatus());
	}
	public StuApplyStatus getInfoStatusStr(){
		return StuApplyStatus.valueOf(this.getInfoStatus());
	}
	public StuApplyStatus getVaccineStatusStr(){
		return StuApplyStatus.valueOf(this.getVaccineStatus());
	}
	public StuType getTypeStr(){
		return StuType.valueOf(this.getType());
	}
	public String getNoStr(){
		if(this.getNo()==0){
			return null;
		}else if(this.getNo()<10){
			return Utils.connectString(this.getYear(),this.getType(),"00",this.getNo());
		}else if(this.getNo()<100){
			return Utils.connectString(this.getYear(),this.getType(),"0",this.getNo());
		}else{
			return Utils.connectString(this.getYear(),this.getType(),this.getNo());
		}
	}
	public BooleanEnum getSexStr(){
		return BooleanEnum.valueOf(this.getSex());
	}
	public String getDateOfBirthStr(){
		if(this.getDateOfBirth()==0){
			return null;
		}
		return DateUtils.timeToString(this.getDateOfBirth(), TIME_FORMAT);
	}
	public String getShortDateOfBirthStr(){
		if(this.getDateOfBirth()==0){
			return null;
		}
		return DateUtils.timeToString(this.getDateOfBirth(), TIME_FORMAT_SHORT);
	}
	public CardType getCardTypeStr(){
		return CardType.valueOf(this.getCardType());
	}
	public String getDateOfReviewStr(){
		if(this.getDateOfReview()==0){
			return null;
		}
		return DateUtils.timeToString(this.getDateOfReview(), TIME_FORMAT);
	}

	private int uid;//'所属用户ID',
	private int year = DateUtils.getCurrentYear();//'招生年份，取当前年，不需要用户输入',
	private int status = 1;//'状态：未提交、初次提交、再次提交、审核通过、审核未通过',
	private int type = 1;//'类别：华师了弟、华师第三代、共建单位子弟',
	private int no;//'报名编号',
	private String name;//'学生姓名',
	private String pinyin;//姓名拼音
	private int sex = 1;//'性别',
	private long dateOfBirth;//'出生日期',
	private String addressOfBirth;//'出生地',
	private String birthplace;//'籍贯',
	private String nation;//'民族',
	private String citizenship;//'国籍',
	private int cardType = 1;//'证件类型',
	private String cardNo;//'证件号',
	private String gatqw;//'港澳台侨外',
	private String jkzt;//'健康状况',
	private String reviewer;//'审核人',
	private long dateOfReview;//'审核日期',
	private String note;//'备注',
	private String message;//给家长留言,
	private String other1;//户口所在地(省)；'other开头的为其它信息，参照对应的word表格',
	private String other2;//户口所在地(市)
	private String other3;//户口所在地(区、县)
	private String other4 = "非农业户口";//户口性质
	private String other5;//户主姓名
	private String other6;//与户主关系
	private String other7;//户口详细地址
	private String other8;//现住址(区)
	private String other9;//现住址(小区)
	private String other10 = "产权";//现住址类型
	private String other11 = "是";//现住址与户口地址是否一致
	private String other12;//邮箱地址
	private String other13;//父亲姓名
	private String other14;//父亲工作单位
	private String other15;//父亲职务
	private String other16;//父亲联系电话
	private String other17;//父亲校园一卡通号码
	private String other18;//母亲姓名
	private String other19;//母亲工作单位
	private String other20;//母亲职务
	private String other21;//母亲联系电话
	private String other22;//母亲校园一卡通号码
	private String other23;//与祖辈关系
	private String other24;//祖辈姓名
	private String other25;//祖辈单位
	private String other26;//祖辈联系电话
	private String other27;//祖辈单位领导姓名
	private String other28;//祖辈单位领导电话
	private String other29;//已废弃
	private String other30;//已废弃
	private String other31;//已废弃
	private String other32;//已废弃
	private String other33;//已废弃
	private String other34 = "是";//
	private String other35 = "中国";//户口所在地(国)
	private String other36;// 备注

	// 新增字段
	private String other37 = "一孩";// 是否独生子女
	private String other38 = "否";// 是否上过幼儿园
	private String other39 = "是";// 是否进城务工人员随迁子女
	private String other40 = "无";// 残疾类型
	private String other41;// 华师第三代（外）祖父、母 的校园一卡通号码
	private String other42;//如果残疾，说明哪里残疾

	// 新增字段20170609（统计字段）
	private String other43;//是否在洪山区新生入学服务平台上报名？
	private String other44;//是否在其他地区新生服务平台上报名？
	private String other45;//其他地区新生服务平台(省)
	private String other46;//其他地区新生服务平台(市)
	private String other47;//其他地区新生服务平台(区、县)
	private String other48 = "否";//是否单亲
	private String other49 = "否";//是否孤儿
	private String other50 = "否";//是否烈士或优抚子女
	private String other51 = "南湖校区"; //校区入口
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		this.setPinyin(Utils.getPinYin(name));
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public long getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(long dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getAddressOfBirth() {
		return addressOfBirth;
	}
	public void setAddressOfBirth(String addressOfBirth) {
		this.addressOfBirth = addressOfBirth;
	}
	public String getBirthplace() {
		return birthplace;
	}
	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getCitizenship() {
		return citizenship;
	}
	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}
	public int getCardType() {
		return cardType;
	}
	public void setCardType(int cardType) {
		this.cardType = cardType;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getGatqw() {
		return gatqw;
	}
	public void setGatqw(String gatqw) {
		this.gatqw = gatqw;
	}
	public String getJkzt() {
		return jkzt;
	}
	public void setJkzt(String jkzt) {
		this.jkzt = jkzt;
	}
	public String getReviewer() {
		return reviewer;
	}
	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}
	public long getDateOfReview() {
		return dateOfReview;
	}
	public void setDateOfReview(long dateOfReview) {
		this.dateOfReview = dateOfReview;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	
	public String getOther41() {
		return other41;
	}
	public void setOther41(String other41) {
		this.other41 = other41;
	}
	public String getOther37() {
		return other37;
	}
	public String getOther38() {
		return other38;
	}
	public String getOther39() {
		return other39;
	}
	public String getOther40() {
		return other40;
	}
	public void setOther37(String other37) {
		this.other37 = other37;
	}
	public void setOther38(String other38) {
		this.other38 = other38;
	}
	public void setOther39(String other39) {
		this.other39 = other39;
	}
	public void setOther40(String other40) {
		this.other40 = other40;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getOther48() { return other48; }
	public void setOther48(String other48) { this.other48 = other48; }
	public String getOther49() { return other49; }
	public void setOther49(String other49) { this.other49 = other49; }
	public String getOther50() { return other50; }
	public void setOther50(String other50) { this.other50 = other50; }
	public String getOther51() { return other51; }
	public void setOther51(String other51) { this.other51 = other51; }
	
}
