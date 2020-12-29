package ru.progwards.java1.lessons.test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Test1 {

    static class Person {
        public String name;
        public Date birth;
        public double salary;

        public Person(String name) {
            this.name = name;
        }

        Person(String name, Date birth, double salary) {
            this.name = name;
            this.birth = birth;
            this. salary = salary;
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

    public List<Integer> filter(List<Integer> list) {
        int sum = 0;
        ListIterator<Integer> li = list.listIterator();
        while (li.hasNext()) {
            Integer it = li.next();
            sum += it;
        }
        sum /= 100;
        ListIterator<Integer> li1 = list.listIterator();
        while (li1.hasNext()) {
            Integer it = li1.next();
            if (it>sum) {
                li1.remove();
            }
        }

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

    HashMap<Integer, String> a2map(int[] akey, String[] aval) {
        HashMap<Integer, String> res = new HashMap();
        for (int i=0; i<akey.length; i++) {
            res.put(akey[i], aval[i]);
        }
        return res;
    }

    public Set<Integer> a2set(int[] a) {
        Integer[] a1 = new Integer[a.length];
        for (int i=0; i<a.length; i++) {
            a1[i]=a[i];
        }
        //ArrayDeque<Integer> res = new ArrayDeque();
        Set<Integer> res = new HashSet<>();
        for (int i=0; i<a1.length; i++) {
            res.add(a1[i]);
        }
        return res;
    }

    void checkAndAdd(TreeMap<Integer, String> map, Integer key, String value) {
        if (!map.isEmpty() && !map.containsKey(key)) {
            if (key > map.firstKey() && key < map.lastKey()) {
                map.put(key, value);
            }
        }
    }

    int fillHoles(Map<Integer, String> map, int size) {
        int count = 0;
        for (int i=1; i<=size; i++) {
            if (!map.containsKey(i)) {
                map.put(i, "Число "+i);
                count++;
            }
        }
        return count;
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

//    String figDetect(Figure fig) {
//        Square sq = new Square(1.0);
//        Round rd = new Round(1.0);
//
//        if (fig != null && fig.getClass() == sq.getClass()) {
//            Square ob = (Square) fig;
//            return "Сторона квадрата "+ ob.getSide();
//        } else if (fig != null && fig.getClass() == rd.getClass()) {
//            Round ob = (Round) fig;
//            return "Диаметр круга "+ ob.getDiameter();
//        } else {
//            return "Неизвестная фигура";
//        }
//    }

    Instant createInstant() {
        ZonedDateTime zdt = ZonedDateTime.parse("2020-01-01T15:00:00.000000001+03:00[Europe/Moscow]");
        return Instant.from(zdt);
    }

    ZonedDateTime parseZDT(String str) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss.SSS Z zzzz").withZone(ZoneOffset.UTC);
        //ZoneId zone = ZoneId.of("Europe/Moscow");
        //OffsetDateTime odt = OffsetDateTime.parse(str, dtf);
        ZonedDateTime zdt = ZonedDateTime.parse(str, dtf); //odt.atZoneSameInstant(zone);
        return zdt;
    }

    String createFolder(String name) {
        File file = new File(name);
        if (!file.exists()) {
            if (file.mkdir()) {
                //System.out.println("Directory is created!");
            } else {
                //System.out.println("Failed to create directory!");
            }
        }
        Path path1 = Paths.get(name);
//        try {
//            Files.createDirectory(path1);
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
        path1 = path1.toAbsolutePath();
        path1 = path1.getParent();
        path1 = path1.getParent();
        return path1.toString();
    }

    boolean replaceF(String name) {
        boolean res = true;
        try {
            Path path1 = Paths.get(name);
            String str = Files.readString(path1);
            str = str.replace('F', 'f');
            Files.writeString(path1, str);
        } catch (IOException e) {
            res = false;
        }
        return res;
    }

    String swapWords(String sentance) {
        StringTokenizer st = new StringTokenizer(sentance, " .,-!\n");
        String res = "";
        while (st.hasMoreTokens()) {
            if (st.countTokens()>=2) {
                String t1 = st.nextToken();
                String t2 = st.nextToken();
                res += " " + t2 + " " + t1;
            } else {
                break;
            }
        }
        if (st.hasMoreTokens()) {
            res += " " + st.nextToken();
        }

        return res.trim();
    }

    void printPersons(Person[] persons) {
        for (int i=0; i<persons.length; i++) {
            System.out.format(new Locale("ru", "RU"), "|%1$-10s|%2$td/%2$tm/%2$tY|%3$,10.2f|", persons[i].name, persons[i].birth, persons[i].salary);
        }
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
        //System.out.println(t1.lineCount("D:\\123.txt"));
        //System.out.println(t1.invertWords("Буря мглою небо кроет"));
        try (FileWriter writer = new FileWriter("D:\\123.txt")) {
            writer.write("0123456789012345678A012345678B01");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(t1.setStars("D:\\123.txt"));
        t1.scanLines();
        int[] a = {1,1,1,3,5,1};
        System.out.println(t1.a2set(a));
        t1.createInstant();

        System.out.println(t1.createFolder("abc"));
        System.out.println(t1.parseZDT("01.01.2020 16:27:14.444 +0300 Europe/Moscow"));
        Date date = new Date(86, 1, 28);
        System.out.println(new Date(86, 1, 28));
        ZoneId zid1 = ZoneId.of("Europe/Moscow");
        System.out.println(zid1.getRules().getOffset(Instant.now()));


        //System.out.println(t1.swapWords("Убитых словом, добивают молчанием. (c) Уильям Шекспир."));
        Person[] a = new Person[1];
        Date date = new Date(86, 1, 28);
        a[0] = new Person("Вася", date, 200000.00);
        t1.printPersons(a);
   */
    }
}
