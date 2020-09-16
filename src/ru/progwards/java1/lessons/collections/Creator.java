package ru.progwards.java1.lessons.collections;

import java.util.ArrayList;
import java.util.Collection;

public class Creator {
    public static Collection<Integer> fillEven(int n) {
        Collection<Integer> res = new ArrayList();
        for (int i=1; i<=n; i++) {
            res.add(i*2);
        }
        return res;
    }

    public static Collection<Integer> fillOdd(int n) {
        Collection<Integer> res = new ArrayList();
        for (int i=n; i>=1; i--) {
            res.add(i*2 - 1);
        }
        return res;
    }

    public static Collection<Integer> fill3(int n) {
        Collection<Integer> res = new ArrayList();
        /*
        for (int i=0; i<n; i++) {
            res.add(i);
            res.add(i*i);
            res.add(i*i*i);
        }
        */
        int j;
        for (int i=0; i<n; i++) {
            j = i*3;
            res.add(j);
            res.add(j*j);
            res.add(j*j*j);
        }
        return res;
    }

    public static void main(String[] args) {
        Collection<Integer> res = fillEven(-1);
        for (Integer i : res) {
            System.out.println(i);
        }

        res = fillOdd(0);
        for (Integer i : res) {
            System.out.println(i);
        }

        res = fill3(3);
        for (Integer i : res) {
            System.out.println(i);
        }
    }
}
