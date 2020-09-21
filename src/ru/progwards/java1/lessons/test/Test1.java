package ru.progwards.java1.lessons.test;


import java.io.*;
import java.util.*;

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

    public List<Integer> listAction(List<Integer> list) {
        list.remove(Collections.min(list));
        list.add(0, list.size());
        list.add(2, Collections.max(list));
        return list;
    }

    public String invertWords(String sentence) {
        String res = "";
        String[] words = sentence.split("\\s");
        for (int i=0; i<words.length; i++) {
            res = res + words[words.length-1-i] + ".";
        }
        res = res.substring(0,res.length()-1);
        return res;
    }

    public String setStars(String filename) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            String res = "";
            long pos = 10;
            while (raf.getFilePointer()<raf.length()) {
                int temp = raf.read();
                if (raf.getFilePointer() == pos) {
                    raf.seek(pos-1);
                    raf.writeBytes("*");
                    pos = pos + 10;
                    res = res + (char)temp;
                }
            }
            return res;
        } catch (Exception e) {
            throw new IOException(e.getClass().getName());
        }
    }

    public void scanLines() {
        try (Scanner scanner = new Scanner(System.in)) {
            String str = scanner.nextLine();
            while (!str.equals("/stop")) {
                if (str.contains("как дела")) {
                    System.out.println("Хорошо");
                }
                if (str.contains("Привет")) {
                    System.out.println("Здравствуйте!");
                }
                if (!(str.contains("как дела")||str.contains("Привет"))) {
                    System.out.println(str);
                }
                str = scanner.nextLine();
            }
        }
    }

    public Set<Integer> a2set(int[] a) {
        Integer[] a1 = new Integer[a.length];
        for (int i=0; i<a.length; i++) {
            a1[i]=a[i];
        }
        Set<Integer> res = new HashSet<>();
        for (int i=0; i<a.length; i++) {
            res.add(a1[i]);
        }
        return res;
    }

//    public TreeSet<User> createSet() {
//        TreeSet<User> treeSet = new TreeSet<>(new Comparator<User>() {
//            @Override
//            public int compare(User u1, User u2) {
//                if (Integer.compare(u1.id, u2.id)==1) {
//                    return -1;
//                }
//                if (Integer.compare(u1.id, u2.id)==-1) {
//                    return 1;
//                }
//                return Integer.compare(u1.id, u2.id);
//            }
//        });
//        return treeSet;
//    }

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

        //System.out.println(t1.lineCount("D:\\123.txt"));
        //System.out.println(t1.invertWords("Буря мглою небо кроет"));

        try (FileWriter writer = new FileWriter("D:\\123.txt")) {
            writer.write("0123456789012345678A012345678B01");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(t1.setStars("D:\\123.txt"));
*/
        //t1.scanLines();

        int[] a = {1,1,1,3,5,1};
        System.out.println(t1.a2set(a));

    }
}
