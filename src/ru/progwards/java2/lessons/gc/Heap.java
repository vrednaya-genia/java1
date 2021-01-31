package ru.progwards.java2.lessons.gc;

import java.util.Map;
import java.util.TreeMap;

public class Heap {
    private final byte[] heap;
    private final Map<Integer, Integer> filledBlocks;
    private final Map<Integer, Integer> freeBlocks;

    Heap(int maxHeapSize) {
        heap = new byte[maxHeapSize];
        filledBlocks = new TreeMap<>();
        freeBlocks = new TreeMap<>();
        freeBlocks.put(0, maxHeapSize);
    }

    int findToMalloc(int size) {
        for (Integer key : freeBlocks.keySet()) {
            Integer value = freeBlocks.get(key);
            if (value >= size) {
                filledBlocks.put(key, size);
                freeBlocks.remove(key);
                if (value > size) {
                    freeBlocks.put(key + size, value - size);
                }
                for (int i = key; i < key+size; i++) {
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
        if (!filledBlocks.isEmpty()) {
            if (filledBlocks.containsKey(ptr)) {
                Integer value = filledBlocks.get(ptr);
                freeBlocks.put(ptr, value);
                filledBlocks.remove(ptr);
                for (int i = ptr; i < ptr+value; i++) {
                    heap[i] = 0;
                }
                defrag();
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
            if (k1 + v1 == k2) {
                Integer v2 = freeBlocks.get(k2);
                freeBlocks.remove(k2);
                freeBlocks.put(k1, v1 + v2);
                i++;
            }
        }
    }

    public void compact() {
        //Map<Integer, Integer> tempBlocks = new TreeMap<>();
        Integer currKey = 0;
        if (!filledBlocks.isEmpty()) {
            for (Integer value : filledBlocks.values()) {
                //tempBlocks.put(currKey, value);
                currKey += value;
            }
            //filledBlocks.clear();
            //filledBlocks.putAll(tempBlocks);
        }
        if (!freeBlocks.isEmpty()) {
            freeBlocks.clear();
            freeBlocks.put(currKey, heap.length-currKey);
        }
        int count = 0;
        for (int i=0; i < heap.length; i++) {
            if (heap[i] == 1) {
                heap[count++] = 1;
            }
        }
        for (int i=count; i < heap.length; i++) {
            heap[i] = 0;
        }
    }

    public void getBytes(int ptr, byte[] bytes) {
        //System.arraycopy(this.bytes, ptr, bytes, 0, size);
    }

    public void setBytes(int ptr, byte[] bytes) {
        //System.arraycopy(bytes, 0, this.bytes, ptr, size);
    }
}
