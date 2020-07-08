package com.fz.enroll.entity.student;

public class TbStuno {
    private int id;//	int	11	0	0	0	-1	0									0	0	0	0	-1	0	0
    private String noIndex;//	varchar	255	0	-1	0	0	0							utf8	utf8_general_ci	0	0	0	0	0	0	0
    private String no;//	smallint	6	0	0	0	0	0				报名编号					0	0	0	0	0	0	0
    private String name;//	varchar	255	0	0	0	0	0				学生姓名			utf8	utf8_general_ci	0	0	0	0	0	0	0

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoIndex() {
        return noIndex;
    }

    public void setNoIndex(String noIndex) {
        this.noIndex = noIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
