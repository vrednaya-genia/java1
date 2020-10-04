package ru.progwards.java1.lessons.maps;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class FiboMapCache {
    private Map<Integer, BigDecimal> fiboCache;

    public FiboMapCache(boolean cacheOn) {
        if (cacheOn) {
            fiboCache = new HashMap();
        } else {
            clearCache();
        }
    }

    public BigDecimal fiboNumber(int n) {
        if (fiboCache!=null) {                // cacheOn true
            if (fiboCache.containsKey(n)) {
                return fiboCache.get(n);
            } else {
                if (n==1 || n==2) {
                    fiboCache.put(n, BigDecimal.ONE);
                    return BigDecimal.ONE;
                }
                if (fiboCache.containsKey(n-1) && fiboCache.containsKey(n-2)) {
                    BigDecimal num = fiboCache.get(n-1).add(fiboCache.get(n-2));
                    fiboCache.put(n, num);
                    return num;
                } else {
                    long fNum = fiboNum(n);
                    fiboCache.put(n, BigDecimal.valueOf(fNum));
                    return BigDecimal.valueOf(fNum);
                }
            }
        } else {                             // cacheOn false
            if (n==1 || n==2) {
                return BigDecimal.ONE;
            }
            long fNum = fiboNum(n);
            return BigDecimal.valueOf(fNum);
        }
    }

    public static long fiboNum(int n) {
        long fNum = 0;
        long fNum1 = 1;
        long fNum2 = 1;
        for (int i = 3; i<=n; i++) {
            fNum = fNum1 + fNum2;
            fNum1 = fNum2;
            fNum2 = fNum;
        }
        return fNum;
    }

    public void clearCache() {
        fiboCache = null;
    }

    public static void test() {
        FiboMapCache test1 = new FiboMapCache(true);
        long start = System.currentTimeMillis();
        for (int i=1; i<=1000; i++) {
            test1.fiboNumber(i);
        }
        long end = System.currentTimeMillis()-start;
        System.out.println("fiboNumber cacheOn=true время выполнения " + end);

        FiboMapCache test2 = new FiboMapCache(false);
        start = System.currentTimeMillis();
        for (int i=1; i<=1000; i++) {
            test2.fiboNumber(i);
        }
        end = System.currentTimeMillis()-start;
        System.out.println("fiboNumber cacheOn=false время выполнения " + end);
    }

    public static void main(String[] args) {
        test();
    }
}
