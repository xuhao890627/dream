package com.st.dream.designPattern.singleton;


//饿汉式单例
//这样的代码缺点是：第一次加载类的时候会连带着创建Singleton实例，这样的结果与我们所期望的不同，因为创建实例的时候可能并不是我们需要这个实例的时候。
// 同时如果这个Singleton实例的创建非常消耗系统资源，而应用始终都没有使用Singleton实例，那么创建Singleton消耗的系统资源就被白白浪费了。

class SingletonEhan {

    private static SingletonEhan instance = new SingletonEhan();

    private SingletonEhan() {

    }

    public static SingletonEhan getInstance(){
        return instance;
    }
}
