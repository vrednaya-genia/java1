package ru.progwards.java2.lessons.trees;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
import static java.nio.file.Files.readString;

public class TreeTest {
    static final int ITERATIONS = 500_000;
    long start;
    long stop;
    long result1;
    long result2;
    TreeMap<Integer, Integer> map = new TreeMap<>();
    AvlTree<Integer, String> tree = new AvlTree<>();
    TreeMap<String, String> mapStr = new TreeMap<>();
    AvlTree<String, String> treeStr = new AvlTree<>();

    private void testSorted() {
        System.out.println("  put");
        start = System.currentTimeMillis();
        for (int i = 0; i < ITERATIONS; i++)
            tree.put(i, "key=" + i);
        stop = System.currentTimeMillis();
        System.out.println("    AvlTree: " + (stop-start));
        start = System.currentTimeMillis();
        for (int i = 0; i < ITERATIONS; i++)
            map.put(i, i);
        stop = System.currentTimeMillis();
        System.out.println("    TreeMap: " + (stop-start));


        System.out.println("  find");
        start = System.currentTimeMillis();
        for (int i = 0; i < ITERATIONS; i++)
            tree.find(i);
        stop = System.currentTimeMillis();
        System.out.println("    AvlTree: " + (stop-start));
        start = System.currentTimeMillis();
        for (int i = 0; i < ITERATIONS; i++)
            map.get(i);
        stop = System.currentTimeMillis();
        System.out.println("    TreeMap: " + (stop-start));


        System.out.println("  delete");
        start = System.currentTimeMillis();
        //for (int i = 0; i < ITERATIONS; i++) {
        for (int i = ITERATIONS-1; i > -1; i--) {
            try {
                tree.delete(i);
            } catch (TreeException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
        stop = System.currentTimeMillis();
        System.out.println("    AvlTree: " + (stop-start));
        start = System.currentTimeMillis();
        for (int i = 0; i < ITERATIONS; i++)
            map.remove(i);
        stop = System.currentTimeMillis();
        System.out.println("    TreeMap: " + (stop-start));
    }

    private void testRandom() {
        result1 = 0;
        result2 = 0;
        ArrayList<Integer> keys = new ArrayList<>();

        System.out.println("  put");
        for (int i = 0; i < ITERATIONS; i++) {
            //int key = (ThreadLocalRandom.current().nextInt()/100_000_000);
            int key = (ThreadLocalRandom.current().nextInt());
            if (!map.containsKey(key)) {
                start = System.currentTimeMillis();
                tree.put(key, "key=" + key);
                stop = System.currentTimeMillis();
                result1 += stop - start;

                start = System.currentTimeMillis();
                map.put(key, key);
                stop = System.currentTimeMillis();
                result2 += stop - start;

                keys.add(key);
            }
        }
        System.out.println("  (iterations " + keys.size() + ")");
        System.out.println("    AvlTree: " + result1);
        System.out.println("    TreeMap: " + result2);


        System.out.println("  find");
        start = System.currentTimeMillis();
        for (int key : keys)
            tree.find(key);
        stop = System.currentTimeMillis();
        System.out.println("    AvlTree: " + (stop-start));
        start = System.currentTimeMillis();
        for (int key : keys)
            map.get(key);
        stop = System.currentTimeMillis();
        System.out.println("    TreeMap: " + (stop-start));


        System.out.println("  delete");
        start = System.currentTimeMillis();
        for (int key : keys) {
            try {
                tree.delete(key);
            } catch (TreeException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
        stop = System.currentTimeMillis();
        System.out.println("    AvlTree: " + (stop-start));
        start = System.currentTimeMillis();
        for (int key : keys)
            map.remove(key);
        stop = System.currentTimeMillis();
        System.out.println("    TreeMap: " + (stop-start));
    }

    public void testStr(String [] strArr) {
        result1 = 0;
        result2 = 0;
        System.out.println("  put" );
        for (String s : strArr) {
            if (!map.containsKey(s)) {
                start = System.currentTimeMillis();
                treeStr.put(s, s);
                stop = System.currentTimeMillis();
                result1 += stop - start;

                start = System.currentTimeMillis();
                mapStr.put(s, s);
                stop = System.currentTimeMillis();
                result2 += stop - start;
            }
        }
        System.out.println("    AvlTree: " + result1);
        System.out.println("    TreeMap: " + result2);


        result1 = 0;
        result2 = 0;
        System.out.println("  find");
        for (String key : strArr) {
            if (mapStr.containsKey(key)) {
                start = System.currentTimeMillis();
                treeStr.find(key);
                stop = System.currentTimeMillis();
                result1 += stop - start;

                start = System.currentTimeMillis();
                mapStr.get(key);
                stop = System.currentTimeMillis();
                result2 += stop - start;
            }
        }
        System.out.println("    AvlTree: " + result1);
        System.out.println("    TreeMap: " + result2);


        result1 = 0;
        result2 = 0;
        System.out.println("  delete");
        for (String key : strArr) {
            if (mapStr.containsKey(key)) {
                start = System.currentTimeMillis();
                try {
                    treeStr.delete(key);
                } catch (TreeException e) {
                    System.out.println(e.getMessage());
                }
                stop = System.currentTimeMillis();
                result1 += stop - start;

                start = System.currentTimeMillis();
                mapStr.remove(key);
                stop = System.currentTimeMillis();
                result2 += stop - start;
            }
        }
        System.out.println("    AvlTree: " + result1);
        System.out.println("    TreeMap: " + result2);
    }

    public static void main(String[] args) {
        TreeTest tt = new TreeTest();
        System.out.println("Iterations " + ITERATIONS);
        System.out.println("SORTED DATA");
        tt.testSorted();
        System.out.println("RANDOM DATA");
        tt.testRandom();
        System.out.println("STRING DATA");
        String [] strArr = {"A"};
        try {
            String str = readString(Paths.get("wiki.train.tokens"));
            strArr = str.split("[^A-Za-zА-Яа-я]+");
        } catch (Exception e) {
            e.printStackTrace();
        }
        tt.testStr(strArr);
    }
}

