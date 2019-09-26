package com.st.dream.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class IsAysncDemo {

    private static List<Future<String>> futureList = new ArrayList<Future<String>>();

    private static int result = -1;

    public static void main(String[] args) {
        IsAysncDemo demo = new IsAysncDemo();
        demo.generate(1);
        demo.timer();
//        demo.getResult(futureList);
        try {
            int exeResult = demo.getExeResult("thread-0");
            System.out.println("结果出来了:" + exeResult);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public int getExeResult(String threadId) throws ExecutionException, InterruptedException {
        for (Future<String> future : futureList) {

//            if (threadId.equals(future.get())){
                if (future.isDone() && !future.isCancelled()) {
                    System.out.println("执行完了,返回99");
                    result = 99;
                } else {
                    System.out.println("没执行完，返回-1");
                }
//            }
        }
        return result;
    }


    public void generate(int threadNum){
        ExecutorService service = Executors.newFixedThreadPool(threadNum);
        for (int i = 0; i < threadNum; i++) {
            Callable<String> job = getCounts(i);
            Future<String> f = service.submit(job);
            futureList.add(f);
        }
        //关闭线程池,不影响线程的执行
        service.shutdown();
    }

    public void timer() {
        int time = 5;
        try {
            System.out.println("定时器休息" + time +"s");
            Thread.sleep(time * 1000);
            System.out.println("定时器结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Callable<String> getCounts(final int i) {
        final int time = 20;
        System.out.println("执行getCount需要时间"+ time +"s");
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(1000 * time);
                System.out.println("getCount 执行结束拉，等很久了吧");
                return "thread-" + i;
            }
        };
    }

    public void getResult(List<Future<String>> fList) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(getCollectJob(fList));
        service.shutdown();
    }

    public Runnable getCollectJob(final List<Future<String>> fList) {
        return new Runnable() {
            @Override
            public void run() {
                for (Future<String> future : fList) {
                    if (future.isDone() && !future.isCancelled()) {
                        System.out.println("执行完了,返回99");
                        result = 99;
                    } else {
                        System.out.println("没执行完，返回-1");
                    }
                }
            }
        };
    }
}
