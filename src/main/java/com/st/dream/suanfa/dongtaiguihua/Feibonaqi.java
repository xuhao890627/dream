package com.st.dream.suanfa.dongtaiguihua;

public class Feibonaqi {

    public static void main(String[] args) {
        System.out.println(5);
    }

//    斐波那契数列的形式可以表示为：1,1,2,3,5,8......，
//    也就是除了第一项和第二项为1外，第N项F(N)=F(N-1)+F(N-2)，可以写出暴力递归代码，时间复杂度为O(2^N)

    public static int F (int n) {
        if (n <1) {
            return 0;
        }
        if (n ==1 || n ==2 ) {
            return 1;
        }

        return F(n-1) + F(n-2);
    }
}
