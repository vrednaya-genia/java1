package ru.progwards.java1.lessons.bigints;

import java.math.BigInteger;

public class ArrayInteger {
    byte[] digits;

    ArrayInteger(int n) {
        digits = new byte[n];
    }

    void fromInt(BigInteger value) {
        String str = value.toString();
        int k = str.length();
        this.digits = new byte[k];
        for (int i=0; i<k; i++) {
            char ch = str.charAt(k-i-1);
            String temp = String.valueOf(ch);
            byte b = Byte.parseByte(temp);
            this.digits[i]=b;
        }
    }

    BigInteger toInt() {
        String str = "";
        for (int i=0; i<digits.length; i++) {
            str = digits[i] + str; //Byte.toString(digits[i])+str;
        }
        return new BigInteger(str);
    }

    boolean add(ArrayInteger num) {
        // если массивы digits разной длины, то увеличиваем меньший до большиго
        // с заполнением разницы нулями
        int objLen = this.digits.length;
        int numLen = num.digits.length;
        if (objLen != numLen) {
            // увеличение digits у параметра
            if (objLen > numLen) {
                int diff = objLen - numLen;
                byte[] temp = new byte[numLen];
                System.arraycopy(num.digits, 0, temp, 0, numLen);
                num.digits = new byte[numLen+diff];
                System.arraycopy(temp, 0, num.digits, 0, temp.length);
            // увеличение digits у экземпляра, из которого вызван метод
            } else {
                int diff = numLen - objLen;
                byte[] temp = new byte[objLen];
                System.arraycopy(this.digits, 0, temp, 0, objLen);
                this.digits = new byte[objLen+diff];
                System.arraycopy(temp, 0, this.digits, 0, temp.length);
            }
        }

        byte addition = 0;
        for (int i=0; i<digits.length; i++) {
            if (this.digits[i]+num.digits[i]+addition<=9) {
                this.digits[i] = (byte)(this.digits[i]+num.digits[i]+addition);
                addition = 0;
            } else {
                this.digits[i] = (byte)(this.digits[i]+num.digits[i]+addition-10);
                addition = 1;
            }
        }
        if (addition==1) {
            for (int i=0; i<digits.length; i++) {
                this.digits[i] = 0;
            }
            return false;
        } else {
            return true;
        }
    }

    public static void main(String[] args) {
        ArrayInteger a1 = new ArrayInteger(8);
        a1.fromInt(new BigInteger("15696999"));

        ArrayInteger a2 = new ArrayInteger(6);
        a2.fromInt(new BigInteger("199999"));

        //System.out.println(a1.add(a2));

        BigInteger b1 = a1.toInt();
        System.out.println(b1);
    }
}
