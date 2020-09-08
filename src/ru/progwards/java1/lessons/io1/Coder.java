package ru.progwards.java1.lessons.io1;

import java.io.FileReader;
import java.io.FileWriter;
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
                    char s = '\n';
                    strOut = strOut + code[(int)s];
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
        char[] a = new char[2000];
        for (int i=0; i<2000; i++) {
            a[i] = (char)(2000-i);
        }

        try {
            FileWriter writer = new FileWriter("D:\\123.txt");
            writer.write("Java - язык программирования, " +
                    "разработанный компанией Sun Microsystems.");
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        codeFile("D:\\123.txt", "D:\\1234.txt", a, "D:\\123log.txt");
    }
}
