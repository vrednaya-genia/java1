package ru.progwards.java1.lessons.test;

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

    public static void main(String[] args){
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
    }
}
