package com.fz.enroll.entity.student;

import com.fz.enroll.entity.BaseEntity;
import com.fz.enroll.enum0.StuApplyStatus;

public class StuVaccine extends BaseEntity {

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
	
	private int stuId;
	private int status = 1;//状态：未提交、初次提交
	/**
	 * {@link com.fz.enroll.enum0.BooleanEnum}
	 */
	private Integer other1;//是否有接种证
	private Integer other2;//是否全种
	private Integer other3;//乙肝疫苗1
	private Integer other4;//乙肝疫苗2
	private Integer other5;//乙肝疫苗3
	private Integer other6;//卡介苗
	private Integer other7;//脊灰疫苗1
	private Integer other8;//脊灰疫苗2
	private Integer other9;//脊灰疫苗3
	private Integer other10;//脊灰疫苗4
	private Integer other11;//百白破疫苗1
	private Integer other12;//百白破疫苗2
	private Integer other13;//百白破疫苗3
	private Integer other14;//百白破疫苗4
	private Integer other15;//白破疫苗
	private Integer other16;//麻风疫苗
	private Integer other17;//麻腮疫苗
	private Integer other18;//乙脑疫苗1
	private Integer other19;//乙脑疫苗2
	private Integer other20;//A群流脑疫苗1
	private Integer other21;//A群流脑疫苗2
	private Integer other22;//A+C群流脑疫苗1
	private Integer other23;//A+C群流脑疫苗2
	private Integer other24;//甲肝疫苗
	private String other25;//儿童编码
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
	public Integer getOther1() {
		return other1;
	}
	public void setOther1(Integer other1) {
		this.other1 = other1;
	}
	public Integer getOther2() {
		return other2;
	}
	public void setOther2(Integer other2) {
		this.other2 = other2;
	}
	public Integer getOther3() {
		return other3;
	}
	public void setOther3(Integer other3) {
		this.other3 = other3;
	}
	public Integer getOther4() {
		return other4;
	}
	public void setOther4(Integer other4) {
		this.other4 = other4;
	}
	public Integer getOther5() {
		return other5;
	}
	public void setOther5(Integer other5) {
		this.other5 = other5;
	}
	public Integer getOther6() {
		return other6;
	}
	public void setOther6(Integer other6) {
		this.other6 = other6;
	}
	public Integer getOther7() {
		return other7;
	}
	public void setOther7(Integer other7) {
		this.other7 = other7;
	}
	public Integer getOther8() {
		return other8;
	}
	public void setOther8(Integer other8) {
		this.other8 = other8;
	}
	public Integer getOther9() {
		return other9;
	}
	public void setOther9(Integer other9) {
		this.other9 = other9;
	}
	public Integer getOther10() {
		return other10;
	}
	public void setOther10(Integer other10) {
		this.other10 = other10;
	}
	public Integer getOther11() {
		return other11;
	}
	public void setOther11(Integer other11) {
		this.other11 = other11;
	}
	public Integer getOther12() {
		return other12;
	}
	public void setOther12(Integer other12) {
		this.other12 = other12;
	}
	public Integer getOther13() {
		return other13;
	}
	public void setOther13(Integer other13) {
		this.other13 = other13;
	}
	public Integer getOther14() {
		return other14;
	}
	public void setOther14(Integer other14) {
		this.other14 = other14;
	}
	public Integer getOther15() {
		return other15;
	}
	public void setOther15(Integer other15) {
		this.other15 = other15;
	}
	public Integer getOther16() {
		return other16;
	}
	public void setOther16(Integer other16) {
		this.other16 = other16;
	}
	public Integer getOther17() {
		return other17;
	}
	public void setOther17(Integer other17) {
		this.other17 = other17;
	}
	public Integer getOther18() {
		return other18;
	}
	public void setOther18(Integer other18) {
		this.other18 = other18;
	}
	public Integer getOther19() {
		return other19;
	}
	public void setOther19(Integer other19) {
		this.other19 = other19;
	}
	public Integer getOther20() {
		return other20;
	}
	public void setOther20(Integer other20) {
		this.other20 = other20;
	}
	public Integer getOther21() {
		return other21;
	}
	public void setOther21(Integer other21) {
		this.other21 = other21;
	}
	public Integer getOther22() {
		return other22;
	}
	public void setOther22(Integer other22) {
		this.other22 = other22;
	}
	public Integer getOther23() {
		return other23;
	}
	public void setOther23(Integer other23) {
		this.other23 = other23;
	}
	public Integer getOther24() {
		return other24;
	}
	public void setOther24(Integer other24) {
		this.other24 = other24;
	}
	public String getOther25() {
		return other25;
	}
	public void setOther25(String other25) {
		this.other25 = other25;
	}
}
