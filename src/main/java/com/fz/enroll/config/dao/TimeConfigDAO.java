package com.fz.enroll.config.dao;

import com.fz.enroll.entity.config.TimeConfig;

public interface TimeConfigDAO {

	public TimeConfig queryByType(int type);
	public void saveOrUpdate(TimeConfig entity);
}
