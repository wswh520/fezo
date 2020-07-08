package com.fz.enroll.student.dao;

import com.fz.base.dao.BaseDAO;
import com.fz.enroll.entity.student.StuSmsInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StuSmsInfoDAO extends BaseDAO<StuSmsInfo> {
    // 修改短信记录
    public void sendSms(@Param("id") Integer id);
    // 查找所有记录
    public List<StuSmsInfo> findAll();

}
