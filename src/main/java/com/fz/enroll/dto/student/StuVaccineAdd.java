package com.fz.enroll.dto.student;

import com.fz.enroll.entity.student.StuVaccine;
import com.fz.enroll.enum0.BooleanEnum;

public class StuVaccineAdd {
	
	public StuVaccine convert2Entity(){
		try {
			StuVaccine entity = new StuVaccine();
			if (this.getId() != null) {
				entity.setId(Integer.valueOf(this.getId()));
			}
			entity.setOther1(BooleanEnum.valueOf(this.getOther1()).val());
//			entity.setOther2(BooleanEnum.valueOf(this.getOther2()).val());
			entity.setOther3(BooleanEnum.valueOf(this.getOther3()).val());
			entity.setOther4(BooleanEnum.valueOf(this.getOther4()).val());
			entity.setOther5(BooleanEnum.valueOf(this.getOther5()).val());
			entity.setOther6(BooleanEnum.valueOf(this.getOther6()).val());
			entity.setOther7(BooleanEnum.valueOf(this.getOther7()).val());
			entity.setOther8(BooleanEnum.valueOf(this.getOther8()).val());
			entity.setOther9(BooleanEnum.valueOf(this.getOther9()).val());
			entity.setOther10(BooleanEnum.valueOf(this.getOther10()).val());
			entity.setOther11(BooleanEnum.valueOf(this.getOther11()).val());
			entity.setOther12(BooleanEnum.valueOf(this.getOther12()).val());
			entity.setOther13(BooleanEnum.valueOf(this.getOther13()).val());
			entity.setOther14(BooleanEnum.valueOf(this.getOther14()).val());
			entity.setOther15(BooleanEnum.valueOf(this.getOther15()).val());
			entity.setOther16(BooleanEnum.valueOf(this.getOther16()).val());
			entity.setOther17(BooleanEnum.valueOf(this.getOther17()).val());
			entity.setOther18(BooleanEnum.valueOf(this.getOther18()).val());
			entity.setOther19(BooleanEnum.valueOf(this.getOther19()).val());
			entity.setOther20(BooleanEnum.valueOf(this.getOther20()).val());
			entity.setOther21(BooleanEnum.valueOf(this.getOther21()).val());
			entity.setOther22(BooleanEnum.valueOf(this.getOther22()).val());
			entity.setOther23(BooleanEnum.valueOf(this.getOther23()).val());
			entity.setOther24(BooleanEnum.valueOf(this.getOther24()).val());
			entity.setOther25(this.getOther25());

			entity.setOther2(entity.getOther3()&entity.getOther4()&entity.getOther5()&entity.getOther6()
								&entity.getOther7()&entity.getOther8()&entity.getOther9()&entity.getOther10()
								&entity.getOther11()&entity.getOther12()&entity.getOther13()&entity.getOther14()
								&entity.getOther15()&entity.getOther16()&entity.getOther17()&entity.getOther18()
								&entity.getOther19()&entity.getOther20()&entity.getOther21()&entity.getOther22()
								&entity.getOther23()&entity.getOther24());//是否全种
			return entity;
		} catch (Exception e) {
			return null;
		}
	}
	
	private String id;
	private String other1;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
}
