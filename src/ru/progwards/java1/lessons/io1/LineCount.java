package ru.progwards.java1.lessons.io1;

import java.io.FileReader;
import java.util.Scanner;

public class LineCount {
    public static int calcEmpty(String fileName) {
        try {
            FileReader reader = new FileReader(fileName);
            Scanner scanner = new Scanner(reader);
            int res = 0;
            String temp;
            try {
                while (scanner.hasNextLine()) {
                    temp = scanner.nextLine();
                    if (temp.isEmpty()) {
                        res++;
                    }
                }
            } finally {
                reader.close();
                scanner.close();
            }
            return res;
        } catch (Exception e) {
            return -1;
        }
    }

    public static void main(String[] args) {
        System.out.println(calcEmpty("D:\\123.txt"));
    }
}
