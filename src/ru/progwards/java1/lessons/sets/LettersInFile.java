package ru.progwards.java1.lessons.sets;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.TreeSet;

public class LettersInFile {
    public static String process(String fileName) throws IOException {
        String result = "";
        TreeSet<Character> res = new TreeSet();
        try (FileInputStream reader = new FileInputStream(fileName)) {
            InputStreamReader test = new InputStreamReader(reader);
            int b = test.read();
            while (b != -1) {
                char s = (char) b;
                if (Character.isLetter(s)) {
                    res.add(s);
                }
                b = test.read();
            }
            Iterator<Character> it = res.iterator();
            while (it.hasNext()) {
                result += it.next();
            }
        } catch (IOException e) {
            throw new IOException("что-то пошло не так.. " + e.getMessage());
        }
        return result;
    }

    public static void main(String[] args) {
        try (FileWriter writer = new FileWriter("D:\\123.txt")) {
            writer.write("It's a nice day, isn't it? Java супер) super");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println(process("D:\\123.txt"));
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
