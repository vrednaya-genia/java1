package ru.progwards.java2.lessons.gc;

public class MyBlock {
    public int ptr;
    public int size;

    public MyBlock(int ptr, int size) {
        this.ptr = ptr;
        this.size = size;
    }
}
