package ru.progwards.java1.lessons.interfaces;

public class Animal implements FoodCompare, CompareWeight {
    double weight;

    enum AnimalKind {ANIMAL, COW, HAMSTER, DUCK}
    enum FoodKind {UNKNOWN, HAY, CORN}

    public Animal(double weight) { // конструктор
        this.weight = weight;
    }

    public AnimalKind getKind() {
        return AnimalKind.ANIMAL;
    }

    public FoodKind getFoodKind() {
        return FoodKind.UNKNOWN;
    }

    @Override
    public String toString() {
        return "I am " + this.getKind() + ", eat " + this.getFoodKind();
    }

    public double getWeight() {
        return this.weight;
    }

    public double getFoodCoeff() {
        return 0.02;
    }

    public double calculateFoodWeight() {
        return this.weight*this.getFoodCoeff();
    }

    public String toStringFull() {
        return "I am " + this.getKind() + ", eat " + this.getFoodKind() + " " + this.calculateFoodWeight();
    }

    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject == null || getClass() != anObject.getClass()) {
            return false;
        }
        Animal a = (Animal) anObject;
        return Double.compare(this.weight, a.weight) == 0;
    }

    public double getFood1kgPrice() {
        FoodKind fk = getFoodKind();
        switch (fk) {
            case HAY: return 20.0;
            case CORN: return 50.0;
            case UNKNOWN: return 0.0;
        }
        return 0.0;
    }

    public double getFoodPrice() {
        return calculateFoodWeight() * getFood1kgPrice();
    }

    @Override
    public int compareFoodPrice(Animal animal) {
        return Double.compare(this.getFoodPrice(), animal.getFoodPrice());
    }

    @Override
    public CompareResult compareWeight(CompareWeight smthHasWeight) {
        Animal a = (Animal) smthHasWeight;
        int res = Double.compare(this.getWeight(), a.getWeight());
        switch (res) {
            case -1: return CompareResult.LESS;
            case 0: return CompareResult.EQUAL;
            case 1: return CompareResult.GREATER;
        }
        return CompareResult.LESS;
    }

    public static void main(String[] args) {
        Cow cow1 = new Cow(90.0);
        Hamster hamster1 = new Hamster(20.0);
        Duck duck1 = new Duck(10.0);

        System.out.println(cow1.toStringFull());

        System.out.println(cow1.compareFoodPrice(hamster1));
        System.out.println(duck1.compareFoodPrice(cow1));

        System.out.println(cow1.compareWeight(duck1));
    }
}
