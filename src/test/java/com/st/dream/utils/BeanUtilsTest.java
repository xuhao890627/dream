package com.st.dream.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.http.HttpUtil;
import com.st.dream.sbmybatis.model.User;

import java.util.HashMap;

public class BeanUtilsTest {

    public static void main(String[] args) {
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("test1");
        user1.setPassword("pass1");

        User user2 = new User();
//        user2.setId(2);
        user2.setUsername("test2");
        user2.setPassword("pass2");

//        BeanUtil.copyProperties(user2, user1);

//        editable 限制的类或接口，必须为目标对象的实现接口或父类，用于限制拷贝的属性，例如一个类我只想复制其父类的一些属性，就可以将editable设置为父类。
//        ignoreNullValue 是否忽略空值，当源对象的值为null时，true: 忽略而不注入此值，false: 注入null
//        ignoreProperties 忽略的属性列表，设置一个属性列表，不拷贝这些属性值
//        ignoreError 是否忽略字段注入错误
//        可以通过CopyOptions.create()方法创建一个默认的配置项，通过setXXX方法设置每个配置项

        BeanUtil.copyProperties(user2, user1, true, CopyOptions.create().setIgnoreNullValue(true));
        System.out.println(user1);

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("city", "北京");
        String result= HttpUtil.post("https://www.baidu.com", paramMap);
        System.out.println(result);
    }

}
