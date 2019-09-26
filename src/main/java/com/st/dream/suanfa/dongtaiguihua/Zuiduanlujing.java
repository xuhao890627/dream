package com.st.dream.suanfa.dongtaiguihua;

public class Zuiduanlujing {

    public static void main(String[] args) {
        int[][] m = new int[4][4];
        m[0][0] = 1;
        m[0][1] = 3;
        m[0][2] = 5;
        m[0][3] = 9;

        m[1][0] = 8;
        m[1][1] = 1;
        m[1][2] = 3;
        m[1][3] = 4;

        m[2][0] = 5;
        m[2][1] = 0;
        m[2][2] = 6;
        m[2][3] = 1;

        m[3][0] = 8;
        m[3][1] = 8;
        m[3][2] = 4;
        m[3][3] = 0;

        System.out.println(minPathSum(m));
    }

//    这是一道经典的动态规划问题。假设矩阵m的大小为MxN，行数为M，列数为N。先生成大小和m一样的矩阵dp，dp[i][j]的值表示从左上角走到[i][j]位置的最小路径和。
//    对于m的第一行的所有位置来说，从(0,0)位置到(0,j)位置只能往右走，所以(0,0)位置到(0,j)位置的路径和就是m[0][0...j]这些值的累加结果。同理，对于m的第一列的所有位置来说，从(0,0)位置走到(i,0)位置只能往下走。
//    除第一行和第一列的其他位置外，都有左边位置(i-1,j)和上边位置(i,j-1)，所以dp[i,j]=min{dp[i-1,j],dp[i,j-1]}+m[i,j]；以上面的例子来说，最终生成的dp矩阵如下：

    public static int minPathSum(int[][] m){
        if (m == null || m.length == 0) {
            return 0;
        }
        //构建一个dp数组
        int row = m.length;
        int col = m[0].length;
        int[][] dp = new int[row][col];
        //对dp数组的第一列赋值
        for (int i=1; i< row; i++) {
            dp[i][0] = dp[i-1][0] + m[i][0];
        }
        //对dp数组的第一列进行赋值
        for (int j=1; j< col; j++) {
            dp[0][j] = dp[0][j-1] + m[0][j];
        }
        //对除了第一行和第一列意外的点进行赋值
        for (int i=1; i< row; i++) {
            for (int j=1; j<col; j++) {
                dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) + m[i][j];
            }
        }

        return dp[row-1][col-1];
    }
}
