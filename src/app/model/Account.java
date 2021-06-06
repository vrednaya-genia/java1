package app.model;

import java.util.Date;

//POJO
public class Account {
    private String id;
    private String holder;
    private Date date;
    private double amount;
    private int pin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public String toString() {
        return id + ", " + holder + ", " + date.getTime() + ", " + amount + ", " + pin;
    }

    public static Account parseAccount(String s) throws NumberFormatException {
        Account account = new Account();
        String[] sArray = s.split(",");
        if (sArray.length == 5) {
            account.setId(sArray[0].trim());
            account.setHolder(sArray[1].trim());
            account.setDate(new Date(Long.parseLong(sArray[2].trim())));
            account.setAmount(Double.parseDouble(sArray[3].trim()));
            account.setPin(Integer.parseInt(sArray[4].trim()));
        } else {
            throw new NumberFormatException();
        }
        return account;
    }
}