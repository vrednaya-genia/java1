package ru.progwards.java1.lessons.classes;

public class Animal {
    double weight;

    enum AnimalKind {ANIMAL, COW, HAMSTER, DUCK};
    enum FoodKind {UNKNOWN, HAY, CORN};

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
        return getWeight()*getFoodCoeff();
    }

    public String toStringFull() {
        return "I am " + getKind() + ", eat " + getFoodKind() + " " + calculateFoodWeight();
    }

    public static void main(String[] args) {
        Cow cow1 = new Cow(90.0);
        Hamster hamster1 = new Hamster(20.0);
        Duck duck1 = new Duck(10.0);

        System.out.println(cow1.toString());
        System.out.println(cow1.toStringFull());

        System.out.println(hamster1.toString());
        System.out.println(hamster1.toStringFull());

        System.out.println(duck1.toString());
        System.out.println(duck1.toStringFull());
    }
}
