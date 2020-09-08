package ru.progwards.java1.lessons.test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Test1 {

    class Person {
        public String name;
        public Person(String name) {
            this.name = name;
        }
    }
    abstract class PersonCompare {
        public int compare(Person p1, Person p2) {
            return 0;
        }
    }

    public void testing() {
        PersonCompare personCompare = new PersonCompare () {
            @Override
            public int compare(Person p1, Person p2) {
                return p1.name.compareTo(p2.name);
            }
        } ;    
    }

    private int lineCount(String filename) throws IOException {
        int res = 0;
        try {
            FileReader reader = new FileReader(filename);
            Scanner scanner = new Scanner(reader);
            try {
                while (scanner.hasNextLine()) {
                    String temp = scanner.nextLine();
                    res++;
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("файл не найден");
        }
        return res;
    }

    public static void main(String[] args) throws IOException {
        Test1 t1 = new Test1();
        /*
        System.out.println("Сделаю коммит, запушу в репо: робот, проверяй теперь всё это...");

        double num = 2.45;
        num = num%1;
        System.out.println(num);

        double d = 1;
        double prec = d;
        while (d>0) {
            prec = d;
            d/=2;
        }
        System.out.println(prec); //4.9E-324
        */
        System.out.println(t1.lineCount("D:\\123.txt"));
    }
}
