package ru.progwards.java2.lessons.gc;

public class MyBlock implements Comparable<MyBlock> {
    public int ptr;
    public int size;

    MyBlock(int ptr, int size ) {
        this.ptr = ptr;
        this.size = size;
    }

//    @Override
//    public int compareTo(MyBlock o) {
//        return -(1)*Integer.compare(size, o.size);
//    }

    @Override
    public int compareTo(MyBlock o) {
        return Integer.compare(ptr, o.ptr);
    }
}
