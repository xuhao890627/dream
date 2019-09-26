package com.st.dream.thread;

public class ThreadJoinTest {

    public static void main(String[] args) {
        new Thread(new ThreadA()).start();
    }

    public static void printA() {
        System.out.println(Thread.currentThread().getName() + "A");
    }

    public static void printB() {
        System.out.println(Thread.currentThread().getName() + "B");
    }

    public static void printC() {
        System.out.println(Thread.currentThread().getName() + "C");
    }

    static class ThreadA implements Runnable {

        @Override
        public void run() {
            new Thread(new ThreadB()).start();
            ThreadJoinTest.printA();
        }
    }

    static class ThreadB implements Runnable {

        @Override
        public void run() {
            new Thread(new ThreadC()).start();
            ThreadJoinTest.printB();
        }
    }

    static class ThreadC implements Runnable {

        @Override
        public void run() {
            ThreadJoinTest.printC();
        }
    }
}
