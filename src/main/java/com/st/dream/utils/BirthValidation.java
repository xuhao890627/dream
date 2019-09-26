package com.st.dream.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BirthValidation {

    public static void main(String[] args) throws Exception {
        String birth = "320683890413670";

        Integer age = new BirthValidation().getAge(birth);
        System.out.println("age is " + age);
    }

    public String getBirthEight(String id) throws Exception {
        String reg15 = "^(\\d{6})(\\d{6})[0-9X]{3}$";
        String reg18 = "^(\\d{6})(\\d{8})[0-9X]{4}$";

        String s = Optional.ofNullable(id).orElseThrow(() -> new Exception("身份证号码为空"));

        if (!s.matches(reg15) && !s.matches(reg18)) {
            throw new Exception("非法的身份证号码!");
        }
        id = s.trim();
        Pattern pattern = null;
        if (id.length() == 18) {
            pattern = Pattern.compile(reg18);
        }else {
            pattern = Pattern.compile(reg15);
        }
        Matcher matcher = pattern.matcher(id);
        if (matcher.find()) {
            String group = matcher.group(2);
            return group.length()== 6 ? ("19" + group) : group;
        }
        return null;
    }

    public Integer getAge(String birthDay) throws Exception {
        String birth = getBirthEight(birthDay);

        String currentDateEight = getCurrentDateEight();

        Integer currentYear = Integer.parseInt(currentDateEight.substring(0,4));

        Integer currentMonthDay = Integer.parseInt(currentDateEight.substring(4));

        Integer birthYear  = Integer.parseInt(birth.substring(0,4));

        Integer birthMonthDay = Integer.parseInt(birth.substring(4));

        return (currentYear - birthYear) + ((currentMonthDay - birthMonthDay > 0 ) ? 0 : -1);
    }

    public String getCurrentDateEight() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        return df.format(new Date());
    }

}
