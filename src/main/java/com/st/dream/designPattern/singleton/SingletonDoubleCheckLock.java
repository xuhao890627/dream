package com.st.dream.designPattern.singleton;


// 双重锁单例模式
public class SingletonDoubleCheckLock {

//    另外，需要注意 uniqueInstance 采用 volatile 关键字修饰也是很有必要。
//    uniqueInstance 采用 volatile 关键字修饰也是很有必要的， uniqueInstance = new Singleton(); 这段代码其实是分为三步执行：
//    为 uniqueInstance 分配内存空间
//    初始化 uniqueInstance
//    将 uniqueInstance 指向分配的内存地址
//    但是由于 JVM 具有指令重排的特性，执行顺序有可能变成 1->3->2。指令重排在单线程环境下不会出先问题，但是在多线程环境下会导致一个线程获得还没有初始化的实例。例如，线程 T1 执行了 1 和 3，此时 T2 调用 getUniqueInstance() 后发现 uniqueInstance 不为空，因此返回 uniqueInstance，但此时 uniqueInstance 还未被初始化。
//    使用 volatile 可以禁止 JVM 的指令重排，保证在多线程环境下也能正常运行。
    private volatile static SingletonDoubleCheckLock instance = null;

    private SingletonDoubleCheckLock() {

    }

    private static SingletonDoubleCheckLock getInstance(){
//        if (instance == null) {
//            synchronized (SingletonDoubleCheckLock.class) {
//                instance = new SingletonDoubleCheckLock();
//            }
//        }

    //这种写法的问题
//        当 instance 为 null 时，两个线程可以并发地进入 if 语句内部。然后，一个线程进入 synchronized 块来初始化 instance，而另一个线程则被阻断。
//        当第一个线程退出 synchronized 块时，等待着的线程进入并创建另一个 Singleton 对象。注意：当第二个线程进入 synchronized 块时，它并没有检查 instance 是否非 null。

        //所以我们要对instance 进行第二次检查，这就是双重锁的由来

        if (instance == null) {
            synchronized (SingletonDoubleCheckLock.class) {
                if (instance == null)
                instance = new SingletonDoubleCheckLock();
            }
        }
        return instance;

        //双重锁并不是没有bug
    }
}
