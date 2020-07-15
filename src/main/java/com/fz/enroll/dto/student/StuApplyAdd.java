package com.fz.enroll.dto.student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fz.common.util.Utils;
import com.fz.enroll.entity.student.StuApply;
import com.fz.enroll.enum0.BooleanEnum;
import com.fz.enroll.enum0.CardType;
import com.fz.enroll.enum0.StuType;

public class StuApplyAdd {
	
	public StuApply convert2Entity(){
		try {
			StuApply entity = new StuApply();
			if (this.getId() != null) {
				entity.setId(Integer.valueOf(this.getId()));
			}
			if(this.getType()!=null){
				entity.setType(StuType.valueOf(this.getType()).val());
			}
			entity.setName(Utils.emptyToNull(this.getName()));
			entity.setSex(BooleanEnum.valueOf(this.getSex()).val());
			entity.setCardType(CardType.valueOf(this.getCardType()).val());
			entity.setCardNo(Utils.emptyToNull(this.getCardNo()));
			if(entity.getCardType()==CardType.CARD_TYPE1.val()){
				entity.setDateOfBirth(this.getTimeFromStr(entity.getCardNo().substring(6,14),"yyyyMMdd"));
			}else{
				entity.setDateOfBirth(this.getTimeFromStr(this.getDateOfBirth(),StuApply.TIME_FORMAT));
			}
			entity.setAddressOfBirth(Utils.emptyToNull(this.getAddressOfBirth()));
			entity.setBirthplace(Utils.emptyToNull(this.getBirthplace()));
			entity.setNation(Utils.emptyToNull(this.getNation()));
			entity.setCitizenship(Utils.emptyToNull(this.getCitizenship()));
			entity.setGatqw(Utils.emptyToNull(this.getGatqw()));
			entity.setJkzt(Utils.emptyToNull(this.getJkzt()));
			
			if((entity.getId()==0&&entity.getType()==0)
					||entity.getName()==null
					||entity.getDateOfBirth()==0
					||entity.getAddressOfBirth()==null
					||entity.getBirthplace()==null
					||entity.getNation()==null
					||entity.getCitizenship()==null
					||entity.getCardType()==0
					||entity.getCardNo()==null){
				return null;
			}
			
			entity.setCardNo(entity.getCardNo().toUpperCase());
			entity.setName(entity.getName().replaceAll(" ", ""));
			entity.setUid(Utils.getCurrentUid());
			entity.setYear(this.getYear());
			
			entity.setOther1(Utils.removeLastChar(this.getOther1(), "省"));
			entity.setOther2(Utils.removeLastChar(this.getOther2(), "市"));
			entity.setOther3(Utils.removeLastChar(this.getOther3(), "区"));
			entity.setOther3(Utils.removeLastChar(entity.getOther3(), "县"));
			entity.setOther4(Utils.emptyToNull(this.getOther4()));
			entity.setOther5(Utils.emptyToNull(this.getOther5()));
			entity.setOther6(Utils.emptyToNull(this.getOther6()));
			entity.setOther7(Utils.emptyToNull(this.getOther7()));
			entity.setOther8(Utils.removeLastChar(this.getOther8(),"区"));
			entity.setOther9(Utils.emptyToNull(this.getOther9()));
			entity.setOther10(Utils.emptyToNull(this.getOther10()));
			entity.setOther11(Utils.emptyToNull(this.getOther11()));
			entity.setOther12(Utils.emptyToNull(this.getOther12()));
			entity.setOther13(Utils.emptyToNull(this.getOther13()));
			entity.setOther14(Utils.emptyToNull(this.getOther14()));
			entity.setOther15(Utils.emptyToNull(this.getOther15()));
			entity.setOther16(Utils.emptyToNull(this.getOther16()));
			entity.setOther17(Utils.emptyToNull(this.getOther17()));
			entity.setOther18(Utils.emptyToNull(this.getOther18()));
			entity.setOther19(Utils.emptyToNull(this.getOther19()));
			entity.setOther20(Utils.emptyToNull(this.getOther20()));
			entity.setOther21(Utils.emptyToNull(this.getOther21()));
			entity.setOther22(Utils.emptyToNull(this.getOther22()));
			entity.setOther23(Utils.emptyToNull(this.getOther23()));
			entity.setOther24(Utils.emptyToNull(this.getOther24()));
			entity.setOther25(Utils.emptyToNull(this.getOther25()));
			entity.setOther26(Utils.emptyToNull(this.getOther26()));
			entity.setOther27(Utils.emptyToNull(this.getOther27()));
			entity.setOther28(Utils.emptyToNull(this.getOther28()));
			entity.setOther29(Utils.emptyToNull(this.getOther29()));
			entity.setOther30(Utils.emptyToNull(this.getOther30()));
			entity.setOther31(Utils.emptyToNull(this.getOther31()));
			entity.setOther32(Utils.emptyToNull(this.getOther32()));
			entity.setOther33(Utils.emptyToNull(this.getOther33()));
			entity.setOther34(Utils.emptyToNull(this.getOther34()));
			entity.setOther35(Utils.emptyToNull(this.getOther35()));
			entity.setOther36(Utils.emptyToNull(this.getOther36()));
			entity.setOther37(Utils.emptyToNull(this.getOther37()));
			entity.setOther38(Utils.emptyToNull(this.getOther38()));
			entity.setOther39(Utils.emptyToNull(this.getOther39()));
			entity.setOther40(Utils.emptyToNull(this.getOther40()));
			entity.setOther41(Utils.emptyToNull(this.getOther41()));
			entity.setOther42(Utils.emptyToNull(this.getOther42()));
			entity.setOther43(Utils.emptyToNull(this.getOther43()));
			entity.setOther44(Utils.emptyToNull(this.getOther44()));
			entity.setOther45(Utils.emptyToNull(this.getOther45()));
			entity.setOther46(Utils.emptyToNull(this.getOther46()));
			entity.setOther47(Utils.emptyToNull(this.getOther47()));
			entity.setOther48(Utils.emptyToNull(this.getOther48()));
			entity.setOther49(Utils.emptyToNull(this.getOther49()));
			entity.setOther50(Utils.emptyToNull(this.getOther50()));
			entity.setOther51(Utils.emptyToNull(this.getOther51()));
			entity.setOther52(Utils.emptyToNull(this.getOther52()));
			entity.setOther53(Utils.emptyToNull(this.getOther53()));
			entity.setOther54(Utils.emptyToNull(this.getOther54()));
			entity.setOther55(Utils.emptyToNull(this.getOther55()));
			entity.setOther56(Utils.emptyToNull(this.getOther56()));
			entity.setOther57(Utils.emptyToNull(this.getOther57()));
			entity.setOther58(Utils.emptyToNull(this.getOther58()));
			entity.setOther59(Utils.emptyToNull(this.getOther59()));
			
			return entity;
		} catch (Exception e) {
			return null;
		}
	}
	private int getYear(){
		SimpleDateFormat sp = new SimpleDateFormat("yyyy");
		return Integer.valueOf(sp.format(new Date()));
	}
	private long getTimeFromStr(String timeStr,String formatStr) throws ParseException{
		SimpleDateFormat sp = new SimpleDateFormat(formatStr);
		return sp.parse(timeStr).getTime();
	}
	
	private String id;//
//	private int uid;//'所属用户ID',
//	private int year;//'招生年份，取当前年，不需要用户输入',
//	private int status;//'状态：未提交、初次提交、再次提交、审核通过、审核未通过',
	private String type;//'类别：华师了弟、华师第三代、共建单位子弟',
	private String name;//'学生姓名',
	private String sex;//'性别',
	private String dateOfBirth;//'出生日期',
	private String addressOfBirth;//'出生地',
	private String birthplace;//'籍贯',
	private String nation;//'民族',
	private String citizenship;//'国籍',
	private String cardType;//'证件类型',
	private String cardNo;//'证件号',
	private String gatqw;//'港澳台侨外',
	private String jkzt;//'健康状况',
//	private String reviewer;//'审核人',
//	private long dateOfReview;//'审核日期',
//	private String note;//'备注',
	private String other1;//'other开头的为其它信息，参照对应的word表格',
	private String other2;
	private String other3;
	private String other4;
	private String other5;
	private String other6;
	private String other7;
	private String other8;
	private String other9;
	private String other10;
	private String other11;
	private String other12;
	private String other13;
	private String other14;
	private String other15;
	private String other16;
	private String other17;
	private String other18;
	private String other19;
	private String other20;
	private String other21;
	private String other22;
	private String other23;
	private String other24;
	private String other25;
	private String other26;
	private String other27;
	private String other28;
	private String other29;
	private String other30;
	private String other31;
	private String other32;
	private String other33;
	private String other34;
	private String other35;
	private String other36;
	// 新增字段
	private String other37;// 是否独生子女
	private String other38;// 是否上过幼儿园
	private String other39;// 是否进城务工人员随迁子女
	private String other40;// 残疾类型
	private String other41;// 华师第三代（外）祖父、母 的校园一卡通号码
	private String other42;//如果残疾，说明哪里残疾
	// 新增字段20170609（统计字段）
	private String other43;//是否在洪山区新生入学服务平台上报名？
	private String other44;//是否在其他地区新生服务平台上报名？
	private String other45;//其他地区新生服务平台(省)
	private String other46;//其他地区新生服务平台(市)
	private String other47;//其他地区新生服务平台(区、县)
	private String other48;//是否单亲
	private String other49;//是否孤儿
	private String other50;//是否烈士或优抚子女
	private String other51;//校区入口
	//新增字段20200708
	private String other52;//父亲最高学历
	private String other53;//母亲最高学历
	private String other54; //报名类别
	//新增监护人信息
	private String other55; //监护人姓名
	private String other56; //监护人工作单位(院系或部门)
	private String other57; //监护人职务
	private String other58; //监护人联系电话
	private String other59; //监护人最高学历
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
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
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
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
	public String getOther42() {
		return other42;
	}
	public void setOther42(String other42) {
		this.other42 = other42;
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
	public String getOther48() { return other48; }
	public void setOther48(String other48) { this.other48 = other48; }
	public String getOther49() { return other49; }
	public void setOther49(String other49) { this.other49 = other49; }
	public String getOther50() { return other50; }
	public void setOther50(String other50) { this.other50 = other50; }
	public String getOther51() { return other51; }
	public void setOther51(String other51) { this.other51 = other51; }
	public String getOther52() { return other52; }
	public void setOther52(String other52) { this.other52 = other52; }
	public String getOther53() { return other53; }
	public void setOther53(String other53) { this.other53 = other53; }
	public String getOther54() { return other54; }
	public void setOther54(String other54) { this.other54 = other54; }

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
