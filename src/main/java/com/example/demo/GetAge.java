package com.example.demo;


import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class GetAge{
    public int returnAge;
//    private String strBirthdayArr1;

    public static int getAgeByBirth(String birthday) {
        int returnAge = 0;
        String[] strBirthdayArr1 = birthday.split(" ");
        String[] strBirthdayArr2=strBirthdayArr1[0].split("-");
        String birthYear1 = strBirthdayArr2[0];
        String birthMonth1 = strBirthdayArr2[1];
        String birthDay1 = strBirthdayArr2[2];
        int birthYear = Integer.parseInt(birthYear1);
        int birthMonth = Integer.parseInt(birthMonth1);
        int birthDay = Integer.parseInt(birthDay1);
        LocalDate d =LocalDate.now();
        int nowYear = d.getYear();
        int nowMonth = d.getMonthValue() ;
        int nowDay = d.getDayOfMonth();
        System.out.println(nowYear+'/'+ nowMonth+ '/'+ nowDay);
        if(nowYear == birthYear)
    {
        returnAge = 0;//同年 则为0岁
    }
    else
    {
        int ageDiff = nowYear - birthYear ; //年之差
        if(ageDiff > 0)
        {
            if(nowMonth == birthMonth)
            {
                int dayDiff = nowDay - birthDay;//日之差
                if(dayDiff < 0)
                {
                    returnAge = ageDiff - 1;
                }
                else
                {
                    returnAge = ageDiff ;
                }
            }
            else
            {
                int monthDiff = nowMonth - birthMonth;//月之差
                if(monthDiff < 0)
                {
                    returnAge = ageDiff - 1;
                }
                else
                {
                    returnAge = ageDiff ;
                }
            }
        }
        else
        {
            returnAge = -1;//返回-1 表示出生日期输入错误 晚于今天
        }
    }

    return returnAge;//返回周岁年龄
    }


//
//    var d = new Date();
//    var nowYear = d.getYear();
//    var nowMonth = d.getMonth() + 1;
//    var nowDay = d.getDate();
//
//    if(nowYear == birthYear)
//    {
//        returnAge = 0;//同年 则为0岁
//    }
//    else
//    {
//        var ageDiff = nowYear - birthYear ; //年之差
//        if(ageDiff > 0)
//        {
//            if(nowMonth == birthMonth)
//            {
//                var dayDiff = nowDay - birthDay;//日之差
//                if(dayDiff < 0)
//                {
//                    returnAge = ageDiff - 1;
//                }
//                else
//                {
//                    returnAge = ageDiff ;
//                }
//            }
//            else
//            {
//                var monthDiff = nowMonth - birthMonth;//月之差
//                if(monthDiff < 0)
//                {
//                    returnAge = ageDiff - 1;
//                }
//                else
//                {
//                    returnAge = ageDiff ;
//                }
//            }
//        }
//        else
//        {
//            returnAge = -1;//返回-1 表示出生日期输入错误 晚于今天
//        }
//    }

//    return returnAge;//返回周岁年龄
//    private static int getAgeByBirth(Date birthday) {
//        int age = 0;
//        try {
//            Calendar now = Calendar.getInstance();
//            now.setTime(new Date());// 当前时间
//
//            Calendar birth = Calendar.getInstance();
//            birth.setTime(birthday);
//
//            if (birth.after(now)) {//如果传入的时间，在当前时间的后面，返回0岁
//                age = 0;
//            } else {
//                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
//                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
//                    age += 1;
//                }
//            }
//            return age;
//        } catch (Exception e) {//兼容性更强,异常后返回数据
//            return 0;
//        }
//    }



}
