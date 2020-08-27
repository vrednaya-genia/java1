package ru.progwards.java1.lessons.interfaces;

public class CalculateFibonacci {
    private static CacheInfo lastFibo;

    // n from 1
    public static int fiboNumber(int n) {
        if (lastFibo == null) {
            lastFibo = new CacheInfo();
        }

        if (lastFibo.n==n) {
            return lastFibo.fibo;
        }

        if (n==1 || n==2) {
            lastFibo.n = n;
            lastFibo.fibo = 1;
            return 1;
        }

        int fNum = 0;
        int fNum1 = 1;
        int fNum2 = 1;
        for (int i = 3; i<=n; i++) {
            fNum = fNum1 + fNum2;
            fNum1 = fNum2;
            fNum2 = fNum;
        }
        lastFibo.n = n;
        lastFibo.fibo = fNum;
        return fNum;
    }

    public static class CacheInfo {
        public int n;
        public int fibo;
    }

    public static CacheInfo getLastFibo() {
        return lastFibo;
    }

    public static void clearLastFibo() {
        lastFibo = null;
    }

    public static void main(String[] args) {
        System.out.println(fiboNumber(4));
        System.out.println(fiboNumber(29));
        System.out.println(CalculateFibonacci.lastFibo.n);
        System.out.println(CalculateFibonacci.lastFibo.fibo);

        fiboNumber(1);
        CacheInfo a = getLastFibo();
        System.out.println(a.n);
        System.out.println(a.fibo);

        clearLastFibo();

        fiboNumber(1);
    }
}
