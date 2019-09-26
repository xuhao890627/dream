package com.st.dream.suanfa.dongtaiguihua;

public class Huanqian {

    public static void main(String[] args) {

    }


//    给定数组arr，arr中所有的值都为正数且不重复，每个值代表一种面值的货币，每种面值的货币可以使用任意张，再给定一个正数aim代表要找的钱数，求组成aim的最少货币数
//
//    该问题的经典动态规划方法。如果arr的长度为N，生成行数为N，列数为aim+1的动态规划表dp。dp[i][j]的含义是在可以任意使用arr[0...i]货币的情况下，组成j所需的最小张数。根据这个定义，dp[i][j]的值的计算方法如下：
//
//    当列数为0即在第一列上，表示找到钱数为0时需要的最小张数，易得结果为0
//    当行数为0即在第一行上，表示在只能使用arr[0]货币的情况下，找某个钱的最小张数。比如，arr[0]=2，那么能找开的钱数为2,4,6,8......所以令dp[0][2]=1，dp[0][4]=2，dp[0][6]=3，
//    第一行其他位置代表的钱数一律找不开，所以一律设为32位整数的最大值，我们设这个值为max。
//    剩下的位置依次从左到右，再从上到下计算。假设计算到位置(i,j)，那么dp[i,j]的值可以简化为来自下面的情况：
//    第一种情况就是使用arri货币，那么结果就变成了求dp[i][j-arr[i]]+1
//    第二种情况就是不使用arr[i]货币，那么结果就变成了求dp[i-1][j]
//    最后选择上述情况中较小的值最为最终dp[i][j]的解，所以dp[i][j]=min{dp[i-1][j]，dp[i][j-arr[i]]+1}
//    如果j-arr[i]<0，即发生越界了，说明arr[i]太大，用一张都会超过钱数j，令dp[i][j]=dp[i-1][j]即可，具体代码如下，时间复杂度和额外空间复杂度都为O(Nxaim):



    public static int minCoins(int[] arr,int aim) {
        if (arr==null || arr.length==0 || aim <0) {
            return -1;
        }
        int n=arr.length; //数组长度作为列数
        int max = Integer.MAX_VALUE;
        //创建一个dp数组
        int[][] dp = new int[n][aim+1];
        for (int j=1; j<=aim; j++) {
            dp[0][j] = max;
            //对dp数组上第一行上的列数为arr[0]的背书的位置赋值
            if (j-arr[0] >=0 && dp[0][j-arr[0]] != max) {
                dp[0][j] = dp[0][j-arr[0]] +1;
            }
        }

        int left = 0;
        for (int i=1; i< n; i++) {
            for (int j=1; j<=aim; j++) {
                left = max;
                if (j-arr[i] >=0 && dp[i][j-arr[i]]!=max) {
                    left = dp[i][j-arr[i]] +1;
                }
                dp[i][j] =Math.min(left, dp[i-1][j]);
            }
        }
        return dp[n-1][aim] != max ? dp[n-1][aim]: -1;
    }
}
