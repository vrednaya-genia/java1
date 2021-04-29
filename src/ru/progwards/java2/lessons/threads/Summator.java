package ru.progwards.java2.lessons.threads;

import java.math.BigInteger;

public class Summator {
    static class MyThread extends Thread {
        int n;
        int m;
        int s;

        MyThread(int n, int m) {
            this.n = n;
            this.m = m;
            this.s = 0;
        }

        @Override
        public void run() {
            for (int i = n; i <= m; i++) {
                s = s + i;
            }
        }
    }

    int count;
    MyThread[] threads;

    Summator(int count) {
        this.count = count;
        threads = new MyThread[count];
    }

    public BigInteger sum(BigInteger number) {
        BigInteger c = new BigInteger(String.valueOf(count));
        BigInteger d = number.divide(c);
        BigInteger mod = number.mod(c);
        BigInteger n = BigInteger.ONE;
        BigInteger m = d;
        for (int i = 0; i < count-1; i++) {
            threads[i] = new MyThread(n.intValue(), m.intValue());
            threads[i].start();
            n = n.add(d);
            m = m.add(d);
        }
        threads[count-1] = new MyThread(n.intValue(), m.add(mod).intValue());
        threads[count-1].start();

        BigInteger res = BigInteger.ZERO;
        for (int i = 0; i < count; i++) {
            try {
                threads[i].join();
                res = res.add(new BigInteger(String.valueOf(threads[i].s)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Summator summator = new Summator(2);
        System.out.println(summator.sum(BigInteger.valueOf(10)));
    }
}
