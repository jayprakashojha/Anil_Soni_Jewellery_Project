package com.example.myapplication1;

public class BillItemBean {

    private long id;
    private long customerId;

    private String type;
    private double weight;
    private double rate;
    private double value;
    private double making;
    private double amount;

    public BillItemBean() {}

    public BillItemBean(long id,
                        long customerId,
                        String type,
                        double weight,
                        double rate,
                        double value,
                        double making,
                        double amount) {

        this.id = id;
        this.customerId = customerId;
        this.type = type;
        this.weight = weight;
        this.rate = rate;
        this.value = value;
        this.making = making;
        this.amount = amount;
    }

    public long getId() { return id; }

    public long getCustomerId() { return customerId; }

    public String getType() { return type; }

    public double getWeight() { return weight; }

    public double getRate() { return rate; }

    public double getValue() { return value; }

    public double getMaking() { return making; }

    public double getAmount() { return amount; }

    private String date;
    public String getDate() {
        return date;
    }


    public void setDate(String date) {
        this.date = date;
    }

    public BillItemBean(long id, long customerId, String type, double weight, double rate, double value, double making, double amount, String date) {
        this.id = id;
        this.customerId = customerId;
        this.type = type;
        this.weight = weight;
        this.rate = rate;
        this.value = value;
        this.making = making;
        this.amount = amount;
        this.date = date; // 🔹 Yeh assign hona zaroori hai
    }
}
