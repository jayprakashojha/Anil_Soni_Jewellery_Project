package com.example.myapplication1;


public class Item {

    public String type;          // Gold / Silver
    public String particular;    // Description
    public double weight;        // Weight (gm)
    public double rate;          // Rate per gm
    public double value;         // weight × rate
    public double makingPercent; // Making charges %
    public double amount;        // Final amount of item

    public Item() {
        // Empty constructor required
    }

    public Item(String type,
                String particular,
                double weight,
                double rate,
                double value,
                double makingPercent,
                double amount) {

        this.type = type;
        this.particular = particular;
        this.weight = weight;
        this.rate = rate;
        this.value = value;
        this.makingPercent = makingPercent;
        this.amount = amount;
    }
}
