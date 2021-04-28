package ru.progwards.java2.lessons.trees;

import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

public class TreeTest {
    static final int ITERATIONS = 1000;

    public static void main(String[] args) throws TreeException {
//        BinaryTree<Integer, String> testIterator = new BinaryTree<>();
//        for (int i = 0; i < 4; i++) {
//            int key = ThreadLocalRandom.current().nextInt();
//            //int key = i;
//            testIterator.add(key, "key=" + key);
//        }
//        for (BinaryTree.TreeLeaf leaf : testIterator) {
//            System.out.println(leaf.toString());
//        }

        TreeMap<Integer, Integer> map = new TreeMap<>();
        AvlTree<Integer, String> tree = new AvlTree<>();

        System.out.println("Random data");
        System.out.println("  put");
            long start = System.currentTimeMillis();
            for (int i = 0; i < ITERATIONS; i++) {
                int key = ThreadLocalRandom.current().nextInt();
                map.put(key, key);
            }
            long stop = System.currentTimeMillis();
        System.out.println("    TreeMap: " + (stop-start));
            start = System.currentTimeMillis();
            for (int i = 0; i < ITERATIONS; i++) {
                int key = ThreadLocalRandom.current().nextInt();
                tree.put(key, "key=" + key);
            }
            stop = System.currentTimeMillis();
        System.out.println("    AvlTree: " + (stop-start));
        System.out.println("  find");
            start = System.currentTimeMillis();
            for (int i = 0; i < ITERATIONS; i++) {

            }
            stop = System.currentTimeMillis();
        System.out.println("    TreeMap: " + (stop-start));
            start = System.currentTimeMillis();
            for (int i = 0; i < ITERATIONS; i++) {

            }
            stop = System.currentTimeMillis();
        System.out.println("    AvlTree: " + (stop-start));
        System.out.println("  delete");
            start = System.currentTimeMillis();
            for (Integer i : map.keySet()) {

            }
            stop = System.currentTimeMillis();
        System.out.println("    TreeMap: " + (stop-start));
            start = System.currentTimeMillis();
            for (Integer i : map.keySet()) {
                tree.find(i);
                tree.delete(i);
            }
            stop = System.currentTimeMillis();
        System.out.println("    AvlTree: " + (stop-start));
        System.out.println("Sorted data");
        System.out.println("  put");
            start = System.currentTimeMillis();
            for (int i = 0; i < ITERATIONS; i++) {
                map.put(i, i);
            }
            stop = System.currentTimeMillis();
        System.out.println("    TreeMap: " + (stop-start));
            start = System.currentTimeMillis();
            for (int i = 0; i < ITERATIONS; i++) {
                tree.put(i, "key=" + i);
            }
            stop = System.currentTimeMillis();
        System.out.println("    AvlTree: " + (stop-start));
        System.out.println("  find");
            start = System.currentTimeMillis();
            for (int i = 0; i < ITERATIONS; i++) {

            }
            stop = System.currentTimeMillis();
        System.out.println("    TreeMap: " + (stop-start));
            start = System.currentTimeMillis();
            for (int i = 0; i < ITERATIONS; i++) {

            }
            stop = System.currentTimeMillis();
        System.out.println("    AvlTree: " + (stop-start));
        System.out.println("  delete");
            start = System.currentTimeMillis();
            for (Integer i : map.keySet()) {

            }
            stop = System.currentTimeMillis();
        System.out.println("    TreeMap: " + (stop-start));
            start = System.currentTimeMillis();
            for (Integer i : map.keySet()) {
                tree.find(i);
                tree.delete(i);
            }
            stop = System.currentTimeMillis();
        System.out.println("    AvlTree: " + (stop-start));
    }
}
