package ru.progwards.java1.lessons.files;

public class OrderItem {
    public String goodsName;//наименование товара
    public int count;//количество
    public double price;//цена за единицу

    OrderItem(String goodsName, int count, double price) {
        this.goodsName = goodsName;
        this.count = count;
        this.price = price;
    }
}
