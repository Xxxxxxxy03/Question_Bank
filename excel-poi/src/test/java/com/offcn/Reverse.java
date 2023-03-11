package com.offcn;

public class Reverse {
    public static String reverse(int a) {
        /* 1、输入一个整数返回此数的反序列值（如：输入 123 返回  321  ，输入 7890 返回 987 ）？ */
        if (a < 0) return "";
        if (a < 10) return Integer.toString(a);
        //取得这个整数的最后一位
        int last = a - (a / 10) * 10;
        //输出最后一位和前面的倒序数字
        return Integer.toString(last) + reverse(a / 10);
    }

    public  static void main(String[] args) {

        int x = 123023040;
        String rev = reverse(x);
        System.out.println(rev);
        System.out.println(reverse(123));

    }
}