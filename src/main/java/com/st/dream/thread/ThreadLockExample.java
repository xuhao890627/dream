package com.st.dream.thread;

/**
 * 死锁
 * @author jxu
 *
 */
public class ThreadLockExample {

    /**
     * 锁A
     */
    public final static Object lockA = new Object();

    /**
     * 锁B
     */
    public final static Object lockB = new Object();



    public static void main(String[] args) {

        Thread ta = new Thread(new ThreadA());
        ta.setName("ThreadA");
        ta.start();
        Thread tb = new Thread(new ThreadB());
        tb.setName("ThreadB");
        tb.start();

    }

    private static class ThreadA implements Runnable {
        @Override
        public void run() {
            try {
                synchronized (lockA) {
                    System.out.println(Thread.currentThread().getName() + " lock a");
                    Thread.sleep(1000 * 3);
                    System.out.println(Thread.currentThread().getName() + " try lock b");
                    synchronized (lockB) {
                        System.out.println(Thread.currentThread().getName() + " lock b");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private static class ThreadB implements Runnable {

        @Override
        public void run() {
            try {
                synchronized (lockB) {
                    System.out.println(Thread.currentThread().getName() + " lock b");
                    Thread.sleep(1000 * 3);
                    System.out.println(Thread.currentThread().getName() + " try lock a");
                    synchronized (lockA) {
                        System.out.println(Thread.currentThread().getName() + " lock a");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

}
