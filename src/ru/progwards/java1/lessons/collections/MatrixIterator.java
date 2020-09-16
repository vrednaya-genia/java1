package ru.progwards.java1.lessons.collections;

import java.util.Iterator;

public class MatrixIterator<T> implements Iterator<T> {
    private T[][] array;
    private int pos1;
    private int pos2;

    MatrixIterator(T[][] array) {
        this.array = array;
        pos1 = 0;
        pos2 = 0;
    }

    @Override
    public boolean hasNext() {
        return pos2<array[0].length || pos1<array.length-1;
    }

    @Override
    public T next() {
        if (pos2==array[0].length) {
            pos1 = pos1+1;
            pos2 = 0;
        }
        pos2 = pos2+1;
        return array[pos1][pos2-1];
    }

    public static void main(String[] args) {
        Integer[][] ar = new Integer[3][3];
        ar[0][0]=1;
        ar[0][1]=2;
        ar[0][2]=3;
        ar[1][0]=4;
        ar[1][1]=5;
        ar[1][2]=6;
        ar[2][0]=7;
        ar[2][1]=8;
        ar[2][2]=9;

        MatrixIterator mi = new MatrixIterator(ar);
        while (mi.hasNext()) {
            System.out.println(mi.next());
        }
    }
}
