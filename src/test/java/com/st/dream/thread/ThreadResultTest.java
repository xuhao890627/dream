package com.st.dream.thread;

import com.st.dream.sbmybatis.model.User;

import java.util.Random;
import java.util.concurrent.*;

public class ThreadResultTest {

    // 多线程 等到结束之后获取线程状态

    public static void main(String[] args) {

        ExecutorService threadPool = Executors.newCachedThreadPool();
        CompletionService  cs = new ExecutorCompletionService (threadPool);
        cs.submit(task1(1));
        cs.submit(task2(2));
        cs.submit(task3(3));
        cs.submit(task4(4));
        try {
            Object o = cs.take().get();
            System.out.println("aaaaaaa--- >" + o);
            Object o1 = cs.take().get();
            System.out.println("bbbbbbb--- >" + o1);
            Object o2 = cs.take().get();
            System.out.println("ccccccc--- >" + o2);
            Object o3 = cs.take().get();
            System.out.println("ddddddd--- >" + o3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("都结束了");
        threadPool.shutdown();
    }

    public static Callable<Integer> task1(final int no) {
        final Random rand = new Random();
        Callable<Integer> task = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int time = rand.nextInt(100)*100;
//                map.put(Integer.valueOf(no), time);
                System.out.println("Thread-"+ no +" sleep :"+ time);
                Thread.sleep(time);
                return no;
            }
        };
        return task;
    }

    public static Callable<Integer> task2(final int no) {
        final Random rand = new Random();
        Callable<Integer> task = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int time = rand.nextInt(100)*100;
//                map.put(Integer.valueOf(no), time);
                System.out.println("Thread-"+ no +" wonima :"+ time);
                Thread.sleep(time);
                return no;
            }
        };
        return task;
    }

    public static Callable<User> task3(final int no) {
        final Random rand = new Random();
        Callable<User> task = new Callable<User>() {
            @Override
            public User call() throws Exception {

                User user = new User();
                int time = rand.nextInt(100)*100;
//                map.put(Integer.valueOf(no), time);
                System.out.println("Thread-"+ no +" wonima :"+ time);
                Thread.sleep(time);
                user.setUsername(no + "");
                user.setPassword("XXXXXXXXXXXXXXX");
                return user;
            }
        };
        return task;
    }

    public static Callable<String> task4(final int no) {
        final Random rand = new Random();
        Callable<String> task = new Callable<String>() {
            @Override
            public String call() throws Exception {

                int time = rand.nextInt(100)*100;
//                map.put(Integer.valueOf(no), time);
                System.out.println("Thread-"+ no +" wonima :"+ time);
                Thread.sleep(time);
                return "YYYYYYYYY";
            }
        };
        return task;
    }
}
