package ru.progwards.java1.lessons.io1;

import java.io.FileReader;
import java.io.FileWriter;

public class Coder {
    public static void codeFile(String inFileName, String outFileName, char[] code, String logName) {
        try {
            FileReader reader = new FileReader(inFileName);
            FileWriter writer = new FileWriter(outFileName);
            String strOut = "";
            try {
                int i;
                while ((i=reader.read()) != -1){
                    char symbol = (char) i;
                    strOut = strOut + code[(int)symbol];
                }
                writer.write(strOut);
            } finally {
                reader.close();
                writer.close();
            }
        } catch (Exception e) {
            try {
                FileWriter logFile = new FileWriter(logName);
                try {
                    logFile.write(e.getMessage());
                } finally {
                    logFile.close();
                }
            } catch (Exception e1) {
                System.out.println("не судьба..");
            }
        }
    }

    public static void main(String[] args) {
        char[] a = new char[66000];
        for (int i=0; i<66000; i++) {
            a[i] = (char)(66000-i);
        }

        try {
            FileWriter writer = new FileWriter("D:\\123.txt");
            writer.write("Java - язык программирования, \n" +
                    "разработанный компанией Sun Microsystems. \n" +
                    "1234 \n");
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        codeFile("D:\\123.txt", "D:\\12345.txt", a, "D:\\123log.txt");
    }
}
