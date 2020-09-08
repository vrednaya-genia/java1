package ru.progwards.java1.lessons.io1;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class CharFilter {
    public static void filterFile(String inFileName, String outFileName, String filter) throws Exception {
        try {
            FileReader reader = new FileReader(inFileName);
            Scanner scanner = new Scanner(reader);
            FileWriter writer = new FileWriter(outFileName);
            String strIn;
            String strOut = "";
            boolean isWrite;
            try {
                while (scanner.hasNextLine()) {
                    strIn = scanner.nextLine();
                    for (int i=0; i<strIn.length(); i++) {
                        isWrite = true;
                        for (int j=0; j<filter.length(); j++) {
                            if (strIn.charAt(i) == filter.charAt(j)) {
                                isWrite = false;
                                break;
                            }
                        }
                        if (isWrite) {
                            strOut = strOut + strIn.charAt(i);
                        }
                    }
                    writer.write(strOut);
                }
            } finally {
                reader.close();
                scanner.close();
                writer.close();
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            FileWriter writer = new FileWriter("D:\\123.txt");
            writer.write("Java - строго типизированный объектно-ориентированный язык программирования, " +
                    "разработанный компанией Sun Microsystems (в последующем приобретённой компанией Oracle).");
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String fil = " -,.()";
        filterFile("D:\\123.txt", "D:\\1243.txt", fil);
    }
}
