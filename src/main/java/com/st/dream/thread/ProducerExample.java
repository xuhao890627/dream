package com.st.dream.thread;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 生产者
 * 
 * @author jxu
 *
 */
public class ProducerExample {

    /**
     * 消息缓存队列
     */
    private Queue<String> queue = new ConcurrentLinkedQueue<String>();
    /**
     * 消费线程唤醒信号
     */
    private Object signal = new Object();

    /**
     * 初始化消费者
     */
    public void initCustomer() {
        for (int i = 0; i <= 3; i++) {
            System.out.println("init customer thread" + i);
            Customer c = new Customer(queue, signal);
            Thread t = new Thread(c);
            t.setName("customer-" + i);
            t.start();
        }
    }

    public void addOne(String value) {
        queue.add(value);
        // 唤醒等待的线程，开始干活
        synchronized (signal) {
            signal.notifyAll();
        }
    }

    public static void main(String[] args) {
        ProducerExample pe = new ProducerExample();
        pe.initCustomer();
        for (int i = 0; i <= 10; i++) {
            pe.addOne("son of " + i);
        }
    }


    private class Customer implements Runnable {

        private Queue<String> queue;

        private Object signal;

        public Customer(Queue<String> queue, Object signal) {
            this.queue = queue;
            this.signal = signal;
        }

        @Override
        public void run() {
            for (;;) {
                try {
                    String value = queue.poll();
                    if (value == null) {
                        synchronized (signal) {
                            System.out.println(Thread.currentThread().getName() + " no job, so wait");
                            signal.wait();
                        }
                    } else {
                        System.out.println(Thread.currentThread().getName() + " get the job: " + value);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
