package ru.progwards.java1.lessons.io1;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.util.Scanner;

public class Coder {
    public static void codeFile(String inFileName, String outFileName, char[] code, String logName) {
        try {
            FileReader reader = new FileReader(inFileName);
            Scanner scanner = new Scanner(reader);
            FileWriter writer = new FileWriter(outFileName);
            String strIn;
            String strOut = "";
            try {
                while (scanner.hasNextLine()) {
                    strIn = scanner.nextLine();
                    // посимвольное кодирование
                    for (int i=0; i<strIn.length(); i++) {
                        char symbol = strIn.charAt(i);
                        strOut = strOut + code[(int)symbol];
                    }
                    writer.write(strOut);
                }
            } finally {
                reader.close();
                scanner.close();
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
        char[] a = new char[128];
        for (int i=0; i<128; i++) {
            a[i] = (char)i;
        }

        codeFile("D:\\123.txt", "D:\\1243.txt", a, "D:\\123log.txt");
    }
}
