package com.st.dream.designPattern.singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SingletonTest {

    public static void main(String[] args){
        SingleTonEnum instance = SingleTonEnum.getInstance();
        int xx = 1 << 3;
        System.out.println(xx);

        List<String> list = new ArrayList<String>();
        list.add("aaaaa ");
        list.add(" bbbbb");
        list.add("ccccc");
        list.add("ddddd");
        String collect = list.stream().map(e -> e.trim()).collect(Collectors.joining(","));


        System.out.println(collect);
    }
}
