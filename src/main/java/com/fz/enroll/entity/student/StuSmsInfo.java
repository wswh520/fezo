package com.fz.enroll.entity.student;

import com.fz.enroll.entity.BaseEntity;

import java.util.Date;

public class StuSmsInfo extends BaseEntity {
    private String name;
    private String phone;
    private String status;
    private Date time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
