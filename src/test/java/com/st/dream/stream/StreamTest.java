package com.st.dream.stream;

import java.util.Arrays;
import java.util.List;

import static java.lang.System.out;

public class StreamTest {

    public static void main(String[] args) {
        List<Integer> number = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17);

//        number.parallelStream().forEachOrdered(out::println);
        number.parallelStream().forEach(e -> print(e));    //并行 4个一组提升效率

//        number.stream().forEach(e -> print(e));
    }

    public static void print(Integer i)  {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        out.println(i + "---> xxx") ;
    }
}
