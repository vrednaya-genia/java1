package ru.progwards.java2.lessons.basetypes;

import java.util.Iterator;

public class DoubleHashTable<K extends HashValue, V> implements Iterable<V> {
    K[] keyTable;
    V[] valueTable;
    boolean[] deleted;
    int initCapacity = 101;
    int capacity;
    int realSize;

    DoubleHashTable() {
        keyTable = (K[])new HashValue[initCapacity];
        valueTable = (V[])new Object[initCapacity];
        deleted = new boolean[initCapacity];
        capacity = initCapacity;
        realSize = 0;
    }

    boolean isPrime(int number) {
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    void resizeTable() {
        K[] tempKeyTable = (K[])new HashValue[capacity];
        V[] tempValueTable = (V[])new Object[capacity];
        System.arraycopy(keyTable, 0, tempKeyTable, 0, capacity);
        System.arraycopy(valueTable, 0, tempValueTable, 0, capacity);

        capacity = 2*capacity;
        while (!isPrime(capacity)) {
            capacity++;
        }
        keyTable = (K[])new HashValue[capacity];
        valueTable = (V[])new Object[capacity];
        deleted = new boolean[capacity];
        realSize = 0;

        for (int i = 0; i < tempValueTable.length; i++) {
           if (tempValueTable[i] != null) {
               K key = tempKeyTable[i];
               V value = tempValueTable[i];
               add(key, value);
           }
        }
    }

    public int getHashDiv(int k) {
        return k % capacity;
    }

    public int getHashMul(int k) {
        final double A = 0.6180339887;
        final double N = capacity;
        double d = A*k;
        return (int)(N*(d-Math.floor(d)));
    }

    int step(int index, int step) {
        index += step;
        if (index < 0) {
            index += capacity-1;
        }
        if (index > capacity-1) {
            index -= capacity-1;
        }
        return index;
    }

    public void add(K key, V value) {
        int k = key.getHash();
        int index = getHashMul(k);
        int step = getHashDiv(k);
        int collision = 0;
        while (collision < capacity/10) {
            if (valueTable[index] == null && !deleted[index]) {
                keyTable[index] = key;
                valueTable[index] = value;
                realSize++;
                return;
            }
            index = step(index, step);
            collision++;
        }
        resizeTable();
        add(key, value);
    }

    public V get(K key) {
        int k = key.getHash();
        int index = getHashMul(k);
        int step = getHashDiv(k);
        int collision = 0;
        while (collision < capacity/10) {
            if (key.equals(keyTable[index])) {
                return valueTable[index];
            }
            index = step(index, step);
            collision++;
        }
        System.out.println("В таблице нет данных по ключу " + key.toString());
        return null;
    }

    public void remove(K key) {
        int k = key.getHash();
        int index = getHashMul(k);
        int step = getHashDiv(k);
        int collision = 0;
        while (collision < capacity/10) {
            if (key.equals(keyTable[index])) {
                valueTable[index] = null;
                deleted[index] = true;
                realSize--;
                return;
            }
            index = step(index, step);
            collision++;
        }
        System.out.println("Удаление по ключу " + key.toString() + " невозможно!");
    }

    public void change(K key1, K key2) {
        V value1 = get(key1);
        remove(key1);
        add(key2, value1);
    }

    public int size() {
        return realSize;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i=0; i < capacity; i++) {
            if (valueTable[i] != null) {
                str.append("<").append(keyTable[i]).append(", ").append(valueTable[i]).append(">").append("\n");
            }
        }
        return str.toString();
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public Iterator<V> iterator() {
        return new DoubleHashTableIterator<>(valueTable);
    }

    public static void main(String[] args) {
        DoubleHashTable<IntKey, Integer> t = new DoubleHashTable<>();
        for (int i=0; i<12; i++) {
            t.add(new IntKey(2*i),i);
        }
        t.change(new IntKey(0), new IntKey(11));

        System.out.println(t.toString());
        for (Integer integer : t) {
            System.out.println(integer);
        }

        DoubleHashTable<StringKey, Integer> t2 = new DoubleHashTable<>();
        for (int i=0; i<105; i++) {
            t2.add(new StringKey(Integer.toString(2*i)),i);
        }
    }
}
