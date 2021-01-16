package ru.progwards.java2.lessons.fromLec;

import java.util.ArrayList;
import java.util.List;

public class Test {
    enum CompareResult {LESS, EQUAL, GREATER}

    public static <T extends Comparable<T>> CompareResult compare(T o1, T o2) {
        if (o1.compareTo(o2) < 0) {
            return CompareResult.LESS;
        }
        if (o1.compareTo(o2) > 0) {
            return CompareResult.GREATER;
        }
        return CompareResult.EQUAL;
    }

    public static <T> void swap(List<T> list, int ind1, int ind2) {
        T temp = list.get(ind1);
        list.set(ind1, list.get(ind2));
        list.set(ind2, temp);
    }

    public static <T> List<T> from(T[] arr) {
        List<T> res = new ArrayList<>();
        for (int i=0; i< arr.length; i++) {
            res.add(arr[i]);
        }
        return res;
    }

    public static void main(String[] args) {
        //Test t = new Test();
        System.out.println(compare(1, 2));
    }
}

