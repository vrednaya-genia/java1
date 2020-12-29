package ru.progwards.java2.lessons.recursion;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GoodsWithLambda {
    private List<Goods> list = new ArrayList<>();

    public void setGoods(List<Goods> list) {
        this.list.addAll(list);
    }

    public List<Goods> sortByName() {
        return list.stream().sorted(Comparator.comparing(x -> x.name)).collect(Collectors.toList());
    }// вернуть список, отсортированный по наименованию

    public List<Goods> sortByNumber() {
        return list.stream().sorted(Comparator.comparing(x -> x.number)).collect(Collectors.toList());
    }// вернуть список, отсортированный по артикулу, без учета регистра

    public List<Goods> sortByPartNumber() {
        return list.stream().sorted(Comparator.comparing(x -> x.number.substring(0, 3))).collect(Collectors.toList());
    }// вернуть список, отсортированный по первым 3-м символам артикула, без учета регистра

    public List<Goods> sortByAvailabilityAndNumber() {
        return null;
    }// вернуть список, отсортированный по количеству, а для одинакового количества,
    // по артикулу, без учета регистра

    public List<Goods> expiredAfter(Instant date) {
        return null;
    }// вернуть список, с товаром, который будет просрочен после указанной даты,
    // отсортированный по дате годности

    public List<Goods> countLess(int count) {
        return null;
    }// вернуть список, с товаром, количество на складе которого меньше указанного,
    // отсортированный по количеству

    public List<Goods> countBetween(int count1, int count2) {
        return null;
    }// вернуть список, с товаром, количество на складе которого больше count1 и меньше count2,
    // отсортированный по количеству
}
