package com.st.dream.thread;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UseQueue {

    public static void main(String[] args) {

        ArrayList list = new ArrayList();
        ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();
        queue.add("aaaaa");
        queue.offer("bbbbb");
        System.out.println("xxxxxx" + queue.poll());

        int a= -129;
        byte b = (byte)a;
        System.out.println(b);
    }
}
