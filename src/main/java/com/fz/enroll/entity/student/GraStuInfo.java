package com.fz.enroll.entity.student;


import com.fz.common.util.Utils;
import com.fz.enroll.entity.BaseEntity;
import com.fz.enroll.enum0.BooleanEnum;
import com.fz.enroll.enum0.StuApplyStatus;
import com.fz.enroll.enum0.StuType;

public class GraStuInfo extends BaseEntity {
	public static final String HKDZ_SPLIT = "，";
	/*
1、将信息导入
2、学长核对，核对后直接锁定
3、老师复核


状态：未核对、待复核、核对无误
	如果家长核对时有修改则变为待复核，否则变为核对无识


信息导出
表格打印
分两张表浏览

	 * */
	private boolean locked;//是否锁定了，true:锁定了不能修改
	public boolean getLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public BooleanEnum getXbStr(){
		return BooleanEnum.valueOf(this.getXb());
	}
	public BooleanEnum getDzStr(){
		return BooleanEnum.valueOf(this.getDz());
	}
	public BooleanEnum getSlStr(){
		return BooleanEnum.valueOf(this.getSl());
	}
	public BooleanEnum getTlStr(){
		return BooleanEnum.valueOf(this.getTl());
	}
	public BooleanEnum getZlStr(){
		return BooleanEnum.valueOf(this.getZl());
	}
	public StuType getSylbStr(){
		return StuType.valueOf(this.getSylb());
	}
	public StuApplyStatus getStatusStr(){
		return StuApplyStatus.valueOf(this.getStatus());
	}
	public BooleanEnum getDzNStr(){
		return BooleanEnum.valueOf(this.getDzN());
	}
	public BooleanEnum getSlNStr(){
		return BooleanEnum.valueOf(this.getSlN());
	}
	public BooleanEnum getTlNStr(){
		return BooleanEnum.valueOf(this.getTlN());
	}
	public BooleanEnum getZlNStr(){
		return BooleanEnum.valueOf(this.getZlN());
	}
	public StuType getSylbNStr(){
		return StuType.valueOf(this.getSylbN());
	}

	private int uid;//'所属用户ID',
	private int year;//年份
	private String pinyin;//姓名拼音
	private int status = StuApplyStatus.SUBMIT_NONE.val();//'状态：未核对、待复核、核对无误',
	private String xbxh;//校编学号
	private String bj;//班级
	private String xm;//学生姓名
	private Integer xb;//性别
	private String jg1;//籍贯省
	private String jg2;//籍贯市
	private String csd;//出生地
	private String mz;//民族
	private String hkxz;//户口性质
	private String sfzh;//身份证号
	private Integer dz;//独生子女
	private Integer sl;//视力残疾
	private Integer tl;//听力残疾
	private Integer zl;//智力残疾
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
	private Integer sylb;//生源类别 '类别：1子弟、2第三代、3其他生源'
	
	//核对情况
	private String xmN;//学生姓名
	private String jg1N;//籍贯省
	private String jg2N;//籍贯市
	private String csdN;//出生地
	private String mzN;//民族
	private String hkxzN;//户口性质
	private String sfzhN;//身份证号
	private Integer dzN;//独生子女
	private Integer slN;//视力残疾
	private Integer tlN;//听力残疾
	private Integer zlN;//智力残疾
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
	private Integer sylbN;//生源类别 '类别：1子弟、2第三代、3其他生源'
	private String zw;//任何职务
	private String tc;//特长
	private String shs;//三好生
	private String jl;//所获奖励
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
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
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
		this.setPinyin(Utils.getPinYin(xm));
	}
	public Integer getXb() {
		return xb;
	}
	public void setXb(Integer xb) {
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
	public Integer getDz() {
		return dz;
	}
	public void setDz(Integer dz) {
		this.dz = dz;
	}
	public Integer getSl() {
		return sl;
	}
	public void setSl(Integer sl) {
		this.sl = sl;
	}
	public Integer getTl() {
		return tl;
	}
	public void setTl(Integer tl) {
		this.tl = tl;
	}
	public Integer getZl() {
		return zl;
	}
	public void setZl(Integer zl) {
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
	public Integer getSylb() {
		return sylb;
	}
	public void setSylb(Integer sylb) {
		this.sylb = sylb;
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
	public Integer getDzN() {
		return dzN;
	}
	public void setDzN(Integer dzN) {
		this.dzN = dzN;
	}
	public Integer getSlN() {
		return slN;
	}
	public void setSlN(Integer slN) {
		this.slN = slN;
	}
	public Integer getTlN() {
		return tlN;
	}
	public void setTlN(Integer tlN) {
		this.tlN = tlN;
	}
	public Integer getZlN() {
		return zlN;
	}
	public void setZlN(Integer zlN) {
		this.zlN = zlN;
	}
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
	public Integer getSylbN() {
		return sylbN;
	}
	public void setSylbN(Integer sylbN) {
		this.sylbN = sylbN;
	}
}
