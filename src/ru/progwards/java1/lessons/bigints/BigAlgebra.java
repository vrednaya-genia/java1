package ru.progwards.java1.lessons.bigints;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BigAlgebra {
    public static BigDecimal fastPow(BigDecimal num, int pow) {
        String powBinStr = Integer.toBinaryString(pow);
        int k = powBinStr.length();
        BigDecimal res;
        // первая итерация
        int temp = pow >> k-1;
        if ((temp & 0b0000_0000_0000_0001) == 1) {
            res = num.multiply(num);
        } else {
            res = BigDecimal.ONE;
        }
        // алгоритм
        for (int i=1; i<k-1; i++) {
            temp = pow >> k-i-1;
            if ((temp & 0b0000_0000_0000_0001) == 1) {
                res = res.multiply(num);
                res = res.multiply(res);
            } else {
                res = res.multiply(res);
            }
        }
        // последняя итерация
        if ((pow & 0b0000_0000_0000_0001) == 1) {
            res = res.multiply(num);
        }
        return res;
    }

    public static BigInteger fibonacci(int n) {
        BigInteger res = new BigInteger("1");

        if (n==1 || n==2) return res;

        BigInteger fn1 = new BigInteger("1");
        BigInteger fn2 = new BigInteger("1");
        for (int i=3; i<=n; i++) {
            res = fn1.add(fn2);
            fn1 = fn2;
            fn2 = res;
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(fastPow(new BigDecimal(2),0));
        System.out.println(fibonacci(6));
    }
}
