package ru.progwards.java2.lessons.gc;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Heap {
    byte[] heap;
    Map<Integer, Integer> bookedBlocks; // <указатель на блок, размер блока>
    Map<Integer, Integer> freeBlocks;

    Heap(int maxHeapSize) {
        heap = new byte[maxHeapSize];
        bookedBlocks = new TreeMap<>();
        freeBlocks = new TreeMap<>();
        freeBlocks.put(0, maxHeapSize);
    }

    int findToMalloc(int size) {
        for (Integer key : freeBlocks.keySet()) {
            Integer value = freeBlocks.get(key);
            if (value >= size) {
                bookedBlocks.put(key, size);
                freeBlocks.remove(key);
                freeBlocks.put(key + size, value - size);
                for (int i = key; i < key + size; i++) {
                    heap[i] = 1;
                }
                return key;
            }
        }
        return -1;
    }

    public int malloc(int size) throws OutOfMemoryException {
        if (!freeBlocks.isEmpty()) {
            int res = findToMalloc(size);
            if (res != -1) {
                return res;
            } else {
                compact();
                res = findToMalloc(size);
                if (res != -1) {
                    return res;
                } else {
                    throw new OutOfMemoryException(size);
                }
            }
        } else {
            throw new OutOfMemoryException(size);
        }
    }

    public void free(int ptr) throws InvalidPointerException {
        if (!bookedBlocks.isEmpty()) {
            if (bookedBlocks.containsKey(ptr)) {
                Integer value = bookedBlocks.get(ptr);
                freeBlocks.put(ptr, value);
                bookedBlocks.remove(ptr);
                for (int i=ptr; i<ptr+value; i++) {
                    heap[i] = 0;
                }
            } else {
                throw new InvalidPointerException(ptr);
            }
        } else {
            throw new InvalidPointerException(ptr);
        }
    }

    public void defrag() {
        Integer[] keys = new Integer[freeBlocks.size()];
        freeBlocks.keySet().toArray(keys);
        for (int i=0; i<keys.length-1; i++) {
            Integer k1 = keys[i];
            Integer v1 = freeBlocks.get(k1);
            Integer k2 = keys[i+1];
            Integer v2 = freeBlocks.get(k2);
            if (k1 + v1 == k2) {
                freeBlocks.remove(k2);
                freeBlocks.put(k1, v1 + v2);
                i++;
            }
        }
    }

    public void compact() {
        Integer currKey = 0;
        if (!bookedBlocks.isEmpty()) {
            Iterator<Integer> iterator = bookedBlocks.keySet().iterator();
            while (iterator.hasNext()) {
                Integer key = iterator.next();
                Integer value = bookedBlocks.get(key);
                iterator.remove();
                bookedBlocks.put(currKey, value);
                currKey += value;
            }
        }
        if (!freeBlocks.isEmpty()) {
            freeBlocks.clear();
            freeBlocks.put(currKey, freeBlocks.size()-currKey);
        }
        int count = 0;
        for (int i=0; i<heap.length; i++) {
            if (heap[i] == 1) {
                heap[count++] = 1;
            }
        }
    }

    public static void main(String[] args) throws OutOfMemoryException, InvalidPointerException {
        Heap h = new Heap(1_000_000_000);
        h.malloc(100);
        h.malloc(100);
        h.malloc(100);
        h.malloc(100);
        h.malloc(100);
        h.malloc(100);
        h.malloc(100);
        h.free(100);
        h.free(300);
        h.free(400);
        h.defrag();
        System.out.println();
    }
}
