package com.fz;

import com.fz.common.util.MD5;
import com.sun.org.apache.xpath.internal.operations.Mod;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {

    public static void main(String[] args) {
//        String str = "2014年07月14日";
//        String reg = "[\u4e00-\u9fa5]";
//        Pattern pat = Pattern.compile(reg);
//        Matcher mat=pat.matcher(str);
//        String repickStr = mat.replaceAll("");
//        System.out.println(repickStr);



//        int no = 721;
//        String str = "";
//        //学校
//        int school = Math.floorMod(no,3);
//        System.out.println(school);
//        String schoolK = no % 12 + "";
//        System.out.println(schoolK);
//
//        //第一场
//        List st1 = Arrays.asList(new String[]{"1","2","3"});
//        //第二场
//        List st2 = Arrays.asList(new String[]{"4","5","6"});
//        //第三场
//        List st3 = Arrays.asList(new String[]{"7","8","9"});
//        //第四场
//        List st4 = Arrays.asList(new String[]{"10","11","0"});
//
//        if(school == 1){
//            if(st1.contains(schoolK)){
//                str += "J01";
//            }else if(st2.contains(schoolK)){
//                str += "J02";
//            }else if(st3.contains(schoolK)){
//                str += "J03";
//            }else{
//                str += "J04";
//            }
//        }else if(school == 2){
//            if(st1.contains(schoolK)){
//                str += "Z01";
//            }else if(st2.contains(schoolK)){
//                str += "Z02";
//            }else if(st3.contains(schoolK)){
//                str += "Z03";
//            }else{
//                str += "Z04";
//            }
//        }else{
//            if(st1.contains(schoolK)){
//                str += "S01";
//            }else if(st2.contains(schoolK)){
//                str += "S02";
//            }else if(st3.contains(schoolK)){
//                str += "S03";
//            }else{
//                str += "S04";
//            }
//        }
//        System.out.println(str);
//
//        //座位号
//        DecimalFormat dft = new DecimalFormat("000");
//        String seatNo = String.valueOf(dft.format(Math.ceil((float)no/12)));
//        System.out.println(str + seatNo);
//
//        if(no > 720){
//            if(school == 1){
//                str = "J04";
//            }else if(school == 2){
//                str = "Z04";
//            }else{
//                str = "S04";
//            }
//            System.out.println(str + seatNo);
//        }

//        String password = "123456";
//        System.out.println(MD5.getMD5String(password));

        // TODO Auto-generated method stub
        new ThreadFezo().start();

        new ThreadFezo().start();
        new ThreadFezo().start();
        new ThreadFezo().start();
        new ThreadFezo().start();
        new ThreadFezo().start();
        new ThreadFezo().start();
        new ThreadFezo().start();


    }

    public void test(){

    }
}
