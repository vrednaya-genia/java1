package ru.progwards.java1.lessons.interfaces;

public class CalculateFibonacci {
    private static CacheInfo lastFibo = new CacheInfo();

    // n from 1
    public static int fiboNumber(int n) {
        if (lastFibo.n==n) {        // при инициализации lastFibo.n и lastFibo.fibo равны 0
            return lastFibo.fibo;   // поэтому при первом вычислении lastFibo.n==n не может быть true
        }

        if (n==1) return 1;
        if (n==2) return 1;

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
        System.out.println(fiboNumber(5));
        System.out.println(CalculateFibonacci.lastFibo.n);
        System.out.println(CalculateFibonacci.lastFibo.fibo);
    }
}
