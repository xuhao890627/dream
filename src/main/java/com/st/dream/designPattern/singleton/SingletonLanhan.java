package com.st.dream.designPattern.singleton;

// 懒汉模式
//线程安全问题
//        这是如果两个线程A和B同时执行了该方法，然后以如下方式执行：
//        A进入if判断，此时instance为null，因此进入if内
//        B进入if判断，此时A还没有创建instance，因此instance也为null，因此B也进入if内
//        A创建了一个instance并返回
//        B也创建了一个instance并返回

public class SingletonLanhan {

    private static SingletonLanhan instance = null;

    private SingletonLanhan(){

    }

    public static SingletonLanhan getInstance(){
        if (instance == null) {
            instance = new SingletonLanhan();
        }
        return instance;
    }
}
