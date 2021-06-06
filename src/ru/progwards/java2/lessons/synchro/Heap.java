package ru.progwards.java2.lessons.synchro;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class InvalidPointerException extends Exception {
    InvalidPointerException(int ptr) {
        super("Неверный указатель! " + ptr + " не является началом блока!");
    }
}

class OutOfMemoryException extends Exception {
    OutOfMemoryException(int size) {
        super("Нет свободного блока размера " + size);
    }
}

class MyBlock {
    public int ptr;
    public int size;

    public MyBlock(int ptr, int size) {
        this.ptr = ptr;
        this.size = size;
    }
}

// У меня не получилось сделать 3 задание
public class Heap {
    private final byte[] heap;
    private Map<Integer, Integer> filledBlocks; // <ptr, size>
    private NavigableSet<MyBlock> freeBlocks;
    private Map<Integer, Integer> codePtrs; // <testPtr, myPtr>
    private boolean isBeforeCompact;
    private final Lock lock;

    Heap(int maxHeapSize) {
        heap = new byte[maxHeapSize];
        filledBlocks = new ConcurrentHashMap<>();
        freeBlocks = new ConcurrentSkipListSet<>(Comparator.comparingInt(m -> m.size));
        freeBlocks.add(new MyBlock(0, maxHeapSize));
        codePtrs = new ConcurrentHashMap<>();
        isBeforeCompact = true;
        lock = new ReentrantLock();
    }

    int findToMalloc(int size) {
        MyBlock findBlock = new MyBlock(-1, size);
        MyBlock currBlock = freeBlocks.ceiling(findBlock);
        if (currBlock != null) {
            int currPtr = currBlock.ptr;
            int currSize = currBlock.size;
            lock.lock();
            try {
                filledBlocks.put(currPtr, size);
                freeBlocks.remove(currBlock);
                if (currSize > size) {
                    freeBlocks.add(new MyBlock(currPtr + size, currSize - size));
                }
                if (!isBeforeCompact) {
                    codePtrs.put(currPtr, null);
                }
            } finally {
                lock.unlock();
            }
            for (int i = currPtr; i < currPtr + size; i++) {
                heap[i] = 1;
            }
            return currPtr;
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
            if (!isBeforeCompact) {
                if (codePtrs.get(ptr) != null) {
                    newPtr = codePtrs.get(ptr);
                }
            }
            if (filledBlocks.containsKey(newPtr)) {
                Integer size = filledBlocks.get(newPtr);
                lock.lock();
                try {
                    filledBlocks.remove(newPtr);
                    if (!isBeforeCompact) {
                        codePtrs.remove(ptr);
                    }
                    MyBlock block = new MyBlock(newPtr, size);
                    freeBlocks.add(block);
                    defrag(block);
                } finally {
                    lock.unlock();
                }
                //Thread thread = new Thread(() -> defrag(block));
                //thread.start();
                for (int i = newPtr; i < newPtr+size; i++) {
                    heap[i] = 0;
                }
            } else {
                throw new InvalidPointerException(ptr);
            }
        } else {
            throw new InvalidPointerException(ptr);
        }
    }

    public void defrag(MyBlock block) {
        /*
        NavigableSet<MyBlock> freetemp = new TreeSet<>(Comparator.comparingInt(m -> m.ptr));
        freetemp.addAll(freeBlocks);
        MyBlock[] blocks = new MyBlock[freetemp.size()];
        freetemp.toArray(blocks);
        for (int i=0; i < blocks.length-1; i++) {
            int newPtr = blocks[i].ptr;
            if (blocks[i].ptr + blocks[i].size == blocks[i+1].ptr) {
                int newSize = blocks[i].size + blocks[i+1].size;
                freeBlocks.remove(blocks[i]);
                freeBlocks.remove(blocks[i+1]);
                i++;
                while (blocks[i].ptr + blocks[i].size == blocks[i+1].ptr) {
                    newSize += blocks[i+1].size;
                    freeBlocks.remove(blocks[i+1]);
                    i++;
                }
                freeBlocks.add(new MyBlock(newPtr, newSize));
            }
        }*/
        NavigableSet<MyBlock> freetemp = new TreeSet<>(Comparator.comparingInt(m -> m.ptr));
        freetemp.addAll(freeBlocks);
        MyBlock left = freetemp.lower(block);
        MyBlock right = freetemp.higher(block);
        if (left == null && right == null) {
            return;
        }
        int newPtr = block.ptr;
        int newSize = block.size;
        freeBlocks.remove(block);
        if (left != null && left.ptr + left.size == block.ptr) {
            newPtr = left.ptr;
            newSize += left.size;
            freeBlocks.remove(left);
        }
        if (right != null && block.ptr + block.size == right.ptr) {
            newSize += right.size;
            freeBlocks.remove(right);
        }
        freeBlocks.add(new MyBlock(newPtr, newSize));
    }

    public void compact() {
        System.out.println("compact");
        Integer currPtr = 0;
        if (!filledBlocks.isEmpty()) {
            Map<Integer, Integer> tempBlocks = new TreeMap<>();
            for (Integer ptr : filledBlocks.keySet()) {
                Integer size = filledBlocks.get(ptr);
                tempBlocks.put(currPtr, size);
                if (isBeforeCompact) {
                    codePtrs.put(ptr, currPtr);
                }
                currPtr += size;
            }
            filledBlocks.clear();
            filledBlocks.putAll(tempBlocks);
        }
        isBeforeCompact = false;
        if (!freeBlocks.isEmpty()) {
            freeBlocks.clear();
            freeBlocks.add(new MyBlock(currPtr, heap.length-currPtr));
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
