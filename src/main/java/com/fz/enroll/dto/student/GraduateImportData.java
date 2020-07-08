package com.fz.enroll.dto.student;

import com.fz.common.util.MD5;
import com.fz.enroll.entity.student.GraStuInfo;
import com.fz.enroll.entity.user.User;
import com.fz.enroll.enum0.BooleanEnum;
import com.fz.enroll.enum0.SexEnum;
import com.fz.enroll.enum0.StuType;
import com.fz.enroll.enum0.UserType;

/*
 * 毕业生信息导入
 */
public class GraduateImportData {

	public GraduateImportData(int line,boolean isDataValid,boolean isXbxhValid){
		this.line = line;
		this.isDataValid = isDataValid;
		this.isXbxhValid = isXbxhValid;
	}
	
	public GraStuInfo convert2Info(int uid,int year){
		GraStuInfo info = new GraStuInfo();
		info.setUid(uid);
		info.setYear(year);
		info.setXbxh(this.getXbxh());
		info.setBj(this.getBj());
		info.setXm(this.getXm());
		info.setXb(this.getXb().val());
		info.setJg1(this.getJg1());
		info.setJg2(this.getJg2());
		info.setCsd(this.getCsd());
		info.setMz(this.getMz());
		info.setHkxz(this.getHkxz());
		info.setSfzh(this.getSfzh());
		info.setDz(this.getDz().val());
		info.setSl(this.getSl().val());
		info.setTl(this.getTl().val());
		info.setZl(this.getZl().val());
		info.setJzxz(this.getJzxz());
		info.setJtzz(this.getJtzz());
		info.setHkszd(this.getHkszd());
		info.setHkdz1(this.getHkdz1());
		info.setHkdz2(this.getHkdz2());
		info.setFuxm(this.getFuxm());
		info.setFudw(this.getFudw());
		info.setFudh(this.getFudh());
		info.setMuxm(this.getMuxm());
		info.setMudw(this.getMudw());
		info.setMudh(this.getMudh());
		info.setSylb(this.getSylb().val());
		
		return info;
	}
	public User convert2User(){
		User user = new User();
		user.setUsername(this.getXbxh());
		user.setName(this.getXm());
		int len = this.getSfzh().length();
		user.setPassword(MD5.getMD5String(this.getSfzh().substring(len-6,len)));
		user.setType(UserType.GRADUATE.val());
		return user;
	}

	private int line;
	private boolean isDataValid;//所有数据内容是否有效
	private String errorMsg;
	private boolean isXbxhValid;//身份证号是否可用（isDataValid为true并且cardNo未被使用）
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public boolean isDataValid() {
		return isDataValid;
	}
	public void setDataValid(boolean isDataValid) {
		this.isDataValid = isDataValid;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public boolean isXbxhValid() {
		return isXbxhValid;
	}
	public void setXbxhValid(boolean isXbxhValid) {
		this.isXbxhValid = isXbxhValid;
	}
	
	private String xbxh;//校编学号
	private String bj;//班级
	private String xm;//学生姓名
	private SexEnum xb;//性别
	private String jg1;//籍贯省
	private String jg2;//籍贯市
	private String csd;//出生地
	private String mz;//民族
	private String hkxz;//户口性质
	private String sfzh;//身份证号
	private BooleanEnum dz;//独生子女
	private BooleanEnum sl;//视力残疾
	private BooleanEnum tl;//听力残疾
	private BooleanEnum zl;//智力残疾
	private String jzxz;//居住性质
	private String jtzz;//居住地址
	private String hkszd;//户口所在地
	private String hkdz1;//户口地址
	private String hkdz2;//户口地址
	private String fuxm;//父亲姓名
	private String fudw;//父亲工作单位
	private String fudh;//父亲电话
	private String muxm;//母亲姓名
	private String mudw;//母亲工作单位
	private String mudh;//母亲电话
	private StuType sylb;//生源类别 '类别：1子弟、2第三代、3其他生源'
	public String getXbxh() {
		return xbxh;
	}
	public void setXbxh(String xbxh) {
		this.xbxh = xbxh;
	}
	public String getBj() {
		return bj;
	}
	public void setBj(String bj) {
		this.bj = bj;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public SexEnum getXb() {
		return xb;
	}
	public void setXb(SexEnum xb) {
		this.xb = xb;
	}
	public String getJg1() {
		return jg1;
	}
	public void setJg1(String jg1) {
		this.jg1 = jg1;
	}
	public String getJg2() {
		return jg2;
	}
	public void setJg2(String jg2) {
		this.jg2 = jg2;
	}
	public String getCsd() {
		return csd;
	}
	public void setCsd(String csd) {
		this.csd = csd;
	}
	public String getMz() {
		return mz;
	}
	public void setMz(String mz) {
		this.mz = mz;
	}
	public String getHkxz() {
		return hkxz;
	}
	public void setHkxz(String hkxz) {
		this.hkxz = hkxz;
	}
	public String getSfzh() {
		return sfzh;
	}
	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}
	public BooleanEnum getDz() {
		return dz;
	}
	public void setDz(BooleanEnum dz) {
		this.dz = dz;
	}
	public BooleanEnum getSl() {
		return sl;
	}
	public void setSl(BooleanEnum sl) {
		this.sl = sl;
	}
	public BooleanEnum getTl() {
		return tl;
	}
	public void setTl(BooleanEnum tl) {
		this.tl = tl;
	}
	public BooleanEnum getZl() {
		return zl;
	}
	public void setZl(BooleanEnum zl) {
		this.zl = zl;
	}
	public String getJzxz() {
		return jzxz;
	}
	public void setJzxz(String jzxz) {
		this.jzxz = jzxz;
	}
	public String getJtzz() {
		return jtzz;
	}
	public void setJtzz(String jtzz) {
		this.jtzz = jtzz;
	}
	public String getHkszd() {
		return hkszd;
	}
	public void setHkszd(String hkszd) {
		this.hkszd = hkszd;
	}
	public String getHkdz1() {
		return hkdz1;
	}
	public void setHkdz1(String hkdz1) {
		this.hkdz1 = hkdz1;
	}
	public String getHkdz2() {
		return hkdz2;
	}
	public void setHkdz2(String hkdz2) {
		this.hkdz2 = hkdz2;
	}
	public String getFuxm() {
		return fuxm;
	}
	public void setFuxm(String fuxm) {
		this.fuxm = fuxm;
	}
	public String getFudw() {
		return fudw;
	}
	public void setFudw(String fudw) {
		this.fudw = fudw;
	}
	public String getFudh() {
		return fudh;
	}
	public void setFudh(String fudh) {
		this.fudh = fudh;
	}
	public String getMuxm() {
		return muxm;
	}
	public void setMuxm(String muxm) {
		this.muxm = muxm;
	}
	public String getMudw() {
		return mudw;
	}
	public void setMudw(String mudw) {
		this.mudw = mudw;
	}
	public String getMudh() {
		return mudh;
	}
	public void setMudh(String mudh) {
		this.mudh = mudh;
	}
	public StuType getSylb() {
		return sylb;
	}
	public void setSylb(StuType sylb) {
		this.sylb = sylb;
	}
}
