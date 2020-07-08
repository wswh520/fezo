package com.fz;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {

    public static void main(String[] args) {
        String str = "2014年07月14日";
        String reg = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(reg);
        Matcher mat=pat.matcher(str);
        String repickStr = mat.replaceAll("");
        System.out.println(repickStr);
    }
}
