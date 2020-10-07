package ru.progwards.java1.lessons.maps;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class FiboMapCache {
    private Map<Integer, BigDecimal> fiboCache = new HashMap();
    boolean isOn;

    public FiboMapCache(boolean cacheOn) {
        isOn = cacheOn;
    }

    public BigDecimal fiboNumber(int n) {
        if (isOn) {                // cacheOn true
            if (fiboCache==null) {
                fiboCache = new HashMap();
            }
            if (fiboCache.containsKey(n)) {
                return fiboCache.get(n);
            }
            BigDecimal fn = fiboNum(n);
            fiboCache.put(n, fn);
            return fn;
        } else {                             // cacheOn false
            return fiboNum(n);
        }
    }

    public static BigDecimal fiboNum(int n) {
        if (n==1 || n==2) {
            return BigDecimal.ONE;
        }
        BigDecimal fNum = BigDecimal.ZERO;
        BigDecimal fNum1 = BigDecimal.ONE;
        BigDecimal fNum2 = BigDecimal.ONE;
        for (int i = 3; i<=n; i++) {
            fNum = fNum1.add(fNum2);
            fNum1 = fNum2;
            fNum2 = fNum;
        }
        return fNum;
    }

    public void clearCahe() {
        fiboCache = null;
    }

    public static void test() {
        FiboMapCache test1 = new FiboMapCache(true);
        long start = System.currentTimeMillis();
        for (int i=1; i<=1000; i++) {
            test1.fiboNumber(i);
            test1.clearCahe();
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
