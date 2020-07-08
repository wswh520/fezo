package com.fz.enroll.dto.student;

import com.fz.common.util.Utils;
import com.fz.enroll.entity.student.GraStuInfo;
import com.fz.enroll.enum0.BooleanEnum;
import com.fz.enroll.enum0.StuType;

public class GraStuInfoAdd  {
	private Integer getBooleanInt(String value){
		value = Utils.clearBlank(value);
		if(value==null){
			return null;
		}
		BooleanEnum be = BooleanEnum.valueOf(value);
		return be==null?null:be.val();
	}
	private Integer getTypeInt(String type){
		type = Utils.clearBlank(type);
		if(type==null){
			return null;
		}
		StuType stuType = StuType.valueOf(type);
		return stuType==null?null:stuType.val();
	}
	public GraStuInfo convert2Entity(){
		try{
			GraStuInfo entity=new GraStuInfo();
			entity.setId(Integer.valueOf(this.getId()));
			entity.setXmN(Utils.clearBlank(this.getXmN()));
			entity.setJg1N(Utils.clearBlank(this.getJg1N()));
			entity.setJg2N(Utils.clearBlank(this.getJg2N()));
			entity.setCsdN(Utils.clearBlank(this.getCsdN()));
			entity.setMzN(Utils.clearBlank(this.getMzN()));
			entity.setHkxzN(Utils.clearBlank(this.getHkxzN()));
			entity.setSfzhN(Utils.clearBlank(this.getSfzhN()));
			entity.setDzN(this.getBooleanInt(this.getDzN()));
			if(this.getDisability()!=null){
				entity.setSlN(BooleanEnum.FALSE.val());
				entity.setTlN(BooleanEnum.FALSE.val());
				entity.setZlN(BooleanEnum.FALSE.val());
				if(this.getDisability().equals("视力残疾")){
					entity.setSlN(BooleanEnum.TRUE.val());
				}else if(this.getDisability().equals("听力残疾")){
					entity.setTlN(BooleanEnum.TRUE.val());
				}else if(this.getDisability().equals("智力残疾")){
					entity.setZlN(BooleanEnum.TRUE.val());
				}
			}
			entity.setJzxzN(Utils.clearBlank(this.getJzxzN()));
			entity.setJtzzN(Utils.clearBlank(this.getJtzzN()));
			entity.setHkszdN(Utils.clearBlank(this.getHkszdN()));
			entity.setHkdz1N(Utils.clearBlank(this.getHkdz1N()));
			entity.setHkdz2N(Utils.clearBlank(this.getHkdz2N()));
			entity.setFuxmN(Utils.clearBlank(this.getFuxmN()));
			entity.setFudwN(Utils.clearBlank(this.getFudwN()));
			entity.setFudhN(Utils.clearBlank(this.getFudhN()));
			entity.setMuxmN(Utils.clearBlank(this.getMuxmN()));
			entity.setMudwN(Utils.clearBlank(this.getMudwN()));
			entity.setMudhN(Utils.clearBlank(this.getMudhN()));
			entity.setSylbN(this.getTypeInt(this.getSylbN()));
			entity.setZw(Utils.clearBlank(this.getZw()));
			entity.setTc(Utils.clearBlank(this.getTc()));
			entity.setShs(Utils.clearBlank(this.getShs()));
			entity.setJl(Utils.clearBlank(this.getJl()));
			return entity;
		}catch(Exception e){
			return null;
		}
	}
	private String id;//
	private String xmN;//学生姓名
	private String jg1N;//籍贯省
	private String jg2N;//籍贯市
	private String csdN;//出生地
	private String mzN;//民族
	private String hkxzN;//户口性质
	private String sfzhN;//身份证号
	private String dzN;//独生子女
//	private String slN;//视力残疾
//	private String tlN;//听力残疾
//	private String zlN;//智力残疾
	private String jzxzN;//居住性质
	private String jtzzN;//居住地址
	private String hkszdN;//户口所在地
	private String hkdz1N;//户口地址
	private String hkdz2N;//户口地址
	private String fuxmN;//父亲姓名
	private String fudwN;//父亲工作单位
	private String fudhN;//父亲电话
	private String muxmN;//母亲姓名
	private String mudwN;//母亲工作单位
	private String mudhN;//母亲电话
	private String sylbN;//生源类别 '类别：1子弟、2第三代、3其他生源'
	private String zw;//任何职务
	private String tc;//特长
	private String shs;//三好生
	private String jl;//所获奖励
	private String disability;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getXmN() {
		return xmN;
	}
	public void setXmN(String xmN) {
		this.xmN = xmN;
	}
	public String getJg1N() {
		return jg1N;
	}
	public void setJg1N(String jg1n) {
		jg1N = jg1n;
	}
	public String getJg2N() {
		return jg2N;
	}
	public void setJg2N(String jg2n) {
		jg2N = jg2n;
	}
	public String getCsdN() {
		return csdN;
	}
	public void setCsdN(String csdN) {
		this.csdN = csdN;
	}
	public String getMzN() {
		return mzN;
	}
	public void setMzN(String mzN) {
		this.mzN = mzN;
	}
	public String getHkxzN() {
		return hkxzN;
	}
	public void setHkxzN(String hkxzN) {
		this.hkxzN = hkxzN;
	}
	public String getSfzhN() {
		return sfzhN;
	}
	public void setSfzhN(String sfzhN) {
		this.sfzhN = sfzhN;
	}
	public String getDzN() {
		return dzN;
	}
	public void setDzN(String dzN) {
		this.dzN = dzN;
	}
//	public String getSlN() {
//		return slN;
//	}
//	public void setSlN(String slN) {
//		this.slN = slN;
//	}
//	public String getTlN() {
//		return tlN;
//	}
//	public void setTlN(String tlN) {
//		this.tlN = tlN;
//	}
//	public String getZlN() {
//		return zlN;
//	}
//	public void setZlN(String zlN) {
//		this.zlN = zlN;
//	}
	public String getJzxzN() {
		return jzxzN;
	}
	public void setJzxzN(String jzxzN) {
		this.jzxzN = jzxzN;
	}
	public String getJtzzN() {
		return jtzzN;
	}
	public void setJtzzN(String jtzzN) {
		this.jtzzN = jtzzN;
	}
	public String getHkszdN() {
		return hkszdN;
	}
	public void setHkszdN(String hkszdN) {
		this.hkszdN = hkszdN;
	}
	public String getHkdz1N() {
		return hkdz1N;
	}
	public void setHkdz1N(String hkdz1n) {
		hkdz1N = hkdz1n;
	}
	public String getHkdz2N() {
		return hkdz2N;
	}
	public void setHkdz2N(String hkdz2n) {
		hkdz2N = hkdz2n;
	}
	public String getFuxmN() {
		return fuxmN;
	}
	public void setFuxmN(String fuxmN) {
		this.fuxmN = fuxmN;
	}
	public String getFudwN() {
		return fudwN;
	}
	public void setFudwN(String fudwN) {
		this.fudwN = fudwN;
	}
	public String getFudhN() {
		return fudhN;
	}
	public void setFudhN(String fudhN) {
		this.fudhN = fudhN;
	}
	public String getMuxmN() {
		return muxmN;
	}
	public void setMuxmN(String muxmN) {
		this.muxmN = muxmN;
	}
	public String getMudwN() {
		return mudwN;
	}
	public void setMudwN(String mudwN) {
		this.mudwN = mudwN;
	}
	public String getMudhN() {
		return mudhN;
	}
	public void setMudhN(String mudhN) {
		this.mudhN = mudhN;
	}
	public String getSylbN() {
		return sylbN;
	}
	public void setSylbN(String sylbN) {
		this.sylbN = sylbN;
	}
	public String getZw() {
		return zw;
	}
	public void setZw(String zw) {
		this.zw = zw;
	}
	public String getTc() {
		return tc;
	}
	public void setTc(String tc) {
		this.tc = tc;
	}
	public String getShs() {
		return shs;
	}
	public void setShs(String shs) {
		this.shs = shs;
	}
	public String getJl() {
		return jl;
	}
	public void setJl(String jl) {
		this.jl = jl;
	}
	public String getDisability() {
		return disability;
	}
	public void setDisability(String disability) {
		this.disability = disability;
	}
}
