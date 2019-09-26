package com.st.dream.utils;

import com.st.dream.sbmybatis.model.User;

public class SerializableUtilTest {

    public static void main(String[] args) {
        User user = new User();
        user.setId(1);
        user.setUsername("jxuTest");
        user.setPassword("hahahah");
        SerializableUtil.writeObject(user, "/Users/jxu/Documents/bb");

        User u = SerializableUtil.readObject("/Users/jxu/Documents/bb");
        System.out.println(u.getUsername());
    }
}
