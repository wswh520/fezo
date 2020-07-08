package com.fz.enroll.student.dao;

import java.util.Map;

import com.fz.base.dao.BaseDAO;
import com.fz.enroll.entity.student.StuApply;

public interface StuApplyListDAO extends BaseDAO<StuApply> {

	/**
	 * 审核
	 * 		可以审核的前提是该表单的状态为以下其中一种：
				SUBMIT_TWICE(3),//再次提交
				REVIEW_PASS(4),//审核通过
				REVIEW_REFUSE(5),//审核未通过
				REVIEW_WAITING(6),//待录取
	 * @param entity
	 * @return
	 */
	public int review(StuApply entity);
	public int updateMsg(StuApply entity);
	public int batchReview(Map<String,Object> params);
}
