package com.st.dream.suanfa.dongtaiguihua;

public class Muniushengxiaoniu {

    public static void main(String[] args) {
        System.out.println(Cow(5));
    }

//    假设农场中成熟的母牛每年只会生1头小母牛，并且永远都不会死。第一年农场有一只成熟的母牛，从第二年开始，母牛开始生小母牛。
//    每只小母牛三年以后成熟又可以生小母牛。给定整数N，求N年后母牛的数量
//
//    所有的牛都不会死，所以第N-1年的牛会活到第N年；那么成熟的牛的数量该如何估计呢，就是N-3年的牛的数量，期间出生的牛都不会成熟，所以C(N)=C(N-1)+C(N-3)，
//    初始项为C(1)=1,C(2)=2,C(3)=3,又是类似于斐波那契数列，代码如下：

    public static int Cow(int n) {
        if (n <1) {
            return 0;
        }
        if( n==1 || n==2 || n==3 ) {
            return n;
        }
        return Cow(n-1) + Cow(n-3);
    }

}
