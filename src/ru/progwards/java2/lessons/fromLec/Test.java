package ru.progwards.java2.lessons.fromLec;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Test {
    enum CompareResult {LESS, EQUAL, GREATER}
    public static <T extends Comparable<T>> CompareResult compare(T o1, T o2) {
        if (o1.compareTo(o2) < 0) {
            return CompareResult.LESS;
        }
        if (o1.compareTo(o2) > 0) {
            return CompareResult.GREATER;
        }
        return CompareResult.EQUAL;
    }
    public static <T> void swap(List<T> list, int ind1, int ind2) {
        T temp = list.get(ind1);
        list.set(ind1, list.get(ind2));
        list.set(ind2, temp);
    }
    public static <T> List<T> from(T[] arr) {
        List<T> res = new ArrayList<>();
        for (int i=0; i< arr.length; i++) {
            res.add(arr[i]);
        }
        return res;
    }

    class Person {
        private String name;

        private Person(String name) {
            this.name = name;
        }

        private void setName(String name) {
            this.name = name;
        }
    }
    void callSetName(Person person, String name) {
        Class prsn = Person.class;
        try {
            Method setName = prsn.getDeclaredMethod("setName", String.class);
            setName.setAccessible(true);
            setName.invoke(person, name);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
    }
    void setName(Person person, String name) {
        Class prsn = Person.class;
        try {
            Field nm = prsn.getDeclaredField("name");
            nm.setAccessible(true);
            nm.set(person, name);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
    }
    Person callConstructor(String name) {
        Class prsn = Person.class;
        try {
            Constructor cnstr = prsn.getDeclaredConstructor(String.class);
            cnstr.setAccessible(true);
            Object person = cnstr.newInstance(name);
            return (Person) person;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    class Greetings {
        void hello() {}
        void goodday() {}
        void goodnight() {}
        void hi() {}
    }
    void printAnnotation() {
        Method[] methods = Greetings.class.getDeclaredMethods();
        for (Method m : methods) {
            if (m.isAnnotationPresent(AnnotationTest.class)) {
                AnnotationTest annotation = m.getAnnotation(AnnotationTest.class);
                System.out.println(m.getName() + " " + annotation.text());
            }
        }
    }

    public static void main(String[] args) {
        //Test t = new Test();
        System.out.println(compare(1, 2));

    }
}

