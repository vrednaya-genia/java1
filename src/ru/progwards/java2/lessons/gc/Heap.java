package ru.progwards.java2.lessons.gc;

import java.util.*;
import java.util.stream.Collectors;

public class Heap {
    private final byte[] heap;
    private final Map<Integer, Integer> filledBlocks; // <ptr, size>
    private final NavigableMap<Integer, Integer> freeBlocks; // <size, ptr>
    private final Map<Integer, Integer> codePtrs; // <testPtr, myPtr>
    private boolean isBeforeFirstCompact;
    private int freed;

    Heap(int maxHeapSize) {
        heap = new byte[maxHeapSize];
        filledBlocks = new HashMap<>();
        freeBlocks = new TreeMap<>();
        freeBlocks.put(maxHeapSize, 0);
        codePtrs = new HashMap<>();
        isBeforeFirstCompact = true;
        freed = 0;
    }

    int findToMalloc(int size) {
        Map.Entry<Integer, Integer> findEntry = freeBlocks.ceilingEntry(size);
        if (findEntry != null) {
            Integer ptr = findEntry.getValue();
            Integer findSize = findEntry.getKey();
            freeBlocks.remove(findSize);
            if (findSize > size) {
                freeBlocks.put(findSize - size, ptr + size);
            }
            filledBlocks.put(ptr, size);
            if (!isBeforeFirstCompact) {
                codePtrs.put(ptr, null);
            }
            for (int i = ptr; i < ptr+size; i++) {
                heap[i] = 1;
            }
            return ptr;
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
            Integer newPtr = ptr;
            if (!isBeforeFirstCompact) {
                if (codePtrs.get(ptr) != null) {
                    newPtr = codePtrs.get(ptr);
                }
            }
            if (filledBlocks.containsKey(newPtr)) {
                Integer size = filledBlocks.get(newPtr);
                freeBlocks.put(size, newPtr);
                filledBlocks.remove(newPtr);
                if (!isBeforeFirstCompact) {
                    codePtrs.remove(ptr);
                }
                for (int i = newPtr; i < newPtr+size; i++) {
                    heap[i] = 0;
                }
                freed += size;
                if (freed > heap.length*0.7) {
                    freed = 0;
                    defrag();
                }
            } else {
                throw new InvalidPointerException(ptr);
            }
        } else {
            throw new InvalidPointerException(ptr);
        }
    }

    public void defrag() {
        List<Map.Entry<Integer, Integer>> list;
        list = freeBlocks.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toList());
        for (int i=0; i < list.size()-1; i++) {
            Integer ptr1 = list.get(i).getValue();
            Integer size1 = list.get(i).getKey();
            Integer ptr2 = list.get(i+1).getValue();
            if (ptr1 + size1 == ptr2) {
                Integer size2 = list.get(i+1).getKey();
                freeBlocks.remove(size1);
                freeBlocks.remove(size2);
                freeBlocks.put(size1 + size2, ptr1);
                i++;
            }
        }
    }

    public void compact() {
        System.out.println("compact");
        Integer currPtr = 0;
        if (!filledBlocks.isEmpty()) {
            Map<Integer, Integer> code1 = new HashMap<>();
            Map<Integer, Integer> code2 = new HashMap<>();
            if (!isBeforeFirstCompact) {
                for (Map.Entry<Integer, Integer> entry : codePtrs.entrySet()) {
                    if (entry.getValue() == null) {
                        code1.put(entry.getKey(), entry.getValue());
                    } else {
                        code2.put(entry.getValue(), entry.getKey());
                    }
                }
                codePtrs.clear();
            }
            NavigableMap<Integer, Integer> tempBlocks = new TreeMap<>();
            for (Integer ptr : filledBlocks.keySet()) {
                Integer size = filledBlocks.get(ptr);
                tempBlocks.put(currPtr, size);
                // перекодировка
                if (isBeforeFirstCompact) {
                    codePtrs.put(ptr, currPtr);
                } else {
                    if (code1.containsKey(ptr)) {
                        codePtrs.put(ptr, currPtr);
                    }
                    if (code2.containsKey(ptr)) {
                        codePtrs.put(code2.get(ptr), currPtr);
                    }
                }
                //
                currPtr += size;
            }
            isBeforeFirstCompact = false;
            filledBlocks.clear();
            filledBlocks.putAll(tempBlocks);
        }
        if (!freeBlocks.isEmpty()) {
            freeBlocks.clear();
            freeBlocks.put(heap.length-currPtr, currPtr);
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
}
