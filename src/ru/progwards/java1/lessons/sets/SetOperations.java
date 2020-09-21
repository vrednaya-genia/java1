package ru.progwards.java1.lessons.sets;

import java.util.HashSet;
import java.util.Set;

public class SetOperations {
    // объединение множеств
    public static Set<Integer> union(Set<Integer> set1, Set<Integer> set2) {
        Set<Integer> res = new HashSet(set1);
        res.addAll(set2);
        return res;
    }

    // пересечение множеств
    public static Set<Integer> intersection(Set<Integer> set1, Set<Integer> set2) {
        Set<Integer> res = new HashSet(set1);
        res.retainAll(set2);
        return res;
    }

    // разница множеств
    public static Set<Integer> difference(Set<Integer> set1, Set<Integer> set2) {
        Set<Integer> res = new HashSet(set1);
        res.removeAll(set2);
        return res;
    }

    // симметрическая разница
    public static Set<Integer> symDifference(Set<Integer> set1, Set<Integer> set2) {
        Set<Integer> res = union(set1, set2);
        Set<Integer> intersec = intersection(set1, set2);
        res.removeAll(intersec);
        return res;
    }

    public static void main(String[] args) {
        Set<Integer> i1 = Set.of(1,2,3,8,9);
        Set<Integer> i2 = Set.of(2,5,8,9);

        Set<Integer> i3 = union(i1,i2);
        System.out.println(i3);

        Set<Integer> i4 = intersection(i1,i2);
        System.out.println(i4);

        Set<Integer> i5 = difference(i1,i2);
        System.out.println(i5);

        Set<Integer> i6 = symDifference(i1,i2);
        System.out.println(i6);
    }
}
