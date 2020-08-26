package ru.progwards.java1.lessons.interfaces;

public class Food implements CompareWeight {
    private int weight;

    public Food(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return this.weight;
    }

    public CompareResult compareWeight(CompareWeight smthHasWeight) {
        Food a = (Food) smthHasWeight;
        int res = Integer.compare(this.getWeight(), a.getWeight());
        switch (res) {
            case -1: return CompareResult.LESS;
            //case 0: return CompareResult.EQUAL;
            case 1: return CompareResult.GREATER;
        }
        return CompareResult.EQUAL;
    }

    public static void main(String[] args) {
        Food a = new Food(20);
        Food b = new Food(2);

        System.out.println(a.compareWeight(b));
    }
}
