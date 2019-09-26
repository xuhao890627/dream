package com.st.dream.collection;

import com.st.dream.pojo.UserAAA;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapTest {

    public static void main(String[] args){
        HashMap map = new HashMap();

        ConcurrentHashMap map1 = new ConcurrentHashMap();

        UserAAA user = new UserAAA();

        ClassLoader classLoader = null;

        String s = "sss";
        user.setUsername(s);
        user.setPassword("ddd");

    }

    public void print() {
        System.out.println("xxxxxx");
    }
}
