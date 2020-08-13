package ru.progwards.java1.lessons.arrays;

import java.util.Arrays;

public class DIntArray {
    private int[] intArray;

    public DIntArray(int N) {
        intArray = new int[N];
        for (int i=0; i<N; i++) {
            intArray[i]=i;
        }
    }

    public void add(int num) {
        int[] newIntArray = new int[intArray.length+1];
        System.arraycopy(intArray, 0, newIntArray, 0, intArray.length);
        newIntArray[newIntArray.length-1] = num;

        System.out.println(Arrays.toString(newIntArray));
    }

    public void atInsert(int pos, int num) {
        int[] newIntArray = new int[intArray.length+1];
        System.arraycopy(intArray, 0, newIntArray, 0, pos);
        newIntArray[pos] = num;
        System.arraycopy(intArray, pos, newIntArray, pos+1, intArray.length-pos);

        System.out.println(Arrays.toString(newIntArray));
    }

    public void atDelete(int pos) {
        int[] newIntArray = new int[intArray.length-1];
        System.arraycopy(intArray, 0, newIntArray, 0, pos);
        System.arraycopy(intArray, pos+1, newIntArray, pos, intArray.length-pos-1);

        System.out.println(Arrays.toString(newIntArray));
    }

    public int at(int pos) {
        return intArray[pos];
    }

    public static void main(String[] args) {
        DIntArray a = new DIntArray(7);

        a.add(57);
        a.atInsert(4,51);
        a.atDelete(2);
        System.out.println(a.at(1));
    }
}
