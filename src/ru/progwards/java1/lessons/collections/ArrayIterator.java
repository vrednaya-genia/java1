package ru.progwards.java1.lessons.collections;

import java.util.Iterator;

public class ArrayIterator<T> implements Iterator<T> {
    private T[] array;
    private int pos;

    ArrayIterator(T[] array) {
        this.array = array;
        pos = 0;
    }

    @Override
    public boolean hasNext() {
        return pos<array.length;
    }

    @Override
    public T next() {
        pos = pos+1;
        return array[pos-1];
    }
}
