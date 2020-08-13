package ru.progwards.java1.lessons.arrays;

import java.util.Arrays;

public class Eratosthenes {
    private boolean[] sieve;

    public Eratosthenes(int N) {
        sieve = new boolean[N];
        Arrays.fill(sieve, true);
        sift();
    }

    private void sift() {
        sieve[0] = false;
        sieve[1] = false;

        for (int i=2; i<sieve.length; i++) {
            if (sieve[i]) {
                for (int j=2; i*j<sieve.length; j++) {
                    sieve[i*j]=false;
                }
            }
        }
    }

    public boolean isSimple(int n) {
        return sieve[n];
    }

    public static void main(String[] args) {
        Eratosthenes erat = new Eratosthenes(20);
        System.out.println(erat.isSimple(0));
        System.out.println(erat.isSimple(2));
        System.out.println(erat.isSimple(3));
        System.out.println(erat.isSimple(4));
        System.out.println(erat.isSimple(5));
        System.out.println(erat.isSimple(6));
        System.out.println(erat.isSimple(18));
        System.out.println(erat.isSimple(19));
    }
}
