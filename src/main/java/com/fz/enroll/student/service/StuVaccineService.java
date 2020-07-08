package com.fz.enroll.student.service;

import com.fz.common.res.Response;
import com.fz.enroll.entity.student.StuVaccine;
import com.fz.enroll.enum0.StuApplyStatus;

public interface StuVaccineService {

	public Response loadService(String stuId);
	/**
	 * 修改预防接种证查验登记表信息
	 * 		1、家长只能在相应的时间段内修改对应状态的自己的信息
									SUBMIT_NONE(1),//未提交
	 * 		2、招生老师任何时刻可以修改相应状态的信息
									SUBMIT_ONCE(2),//初次提交
	 * @param entity
	 * @return
	 */
	public Response saveService(StuVaccine entity);
	
	public Response submitService(String id,StuApplyStatus targetStatus);

}
