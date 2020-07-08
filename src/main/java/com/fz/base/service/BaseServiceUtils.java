package com.fz.base.service;

import org.springframework.transaction.interceptor.TransactionAspectSupport;


public class BaseServiceUtils {

	/**
	 * 回滚
	 */
	public static void setRollbackOnly() {
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚
	}

}
