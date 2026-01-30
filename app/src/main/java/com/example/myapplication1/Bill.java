package com.example.myapplication1;

import java.util.List;

public class Bill {

    // Primary details
    public int id;              // Primary Key (SQLite)
    public int billNo;           // Auto generated (1–9999)
    public String description;

    // Item details
    public String type;          // Gold / Silver
    public String particular;    // Description
    public double weight;        // Weight (gm)
    public double rate;          // Rate per gm
    public double value;         // weight × rate
    public double makingPercent; // Making charges %
    public double amount;        // Final amount of item

    // Customer & payment details
    public String customerName;
    public String mobile;
    public double finalAmount;
    public double firstPayment;
    public double totalDeposit;
    public double pendingAmount;

    // Item list
    public List<Item> items;

    // Empty constructor (Required for SQLite / Firebase)
    public Bill() {
    }

    // Full constructor (ALL fields included)
    public Bill(int id,
                int billNo,
                String description,
                String type,
                String particular,
                double weight,
                double rate,
                double value,
                double makingPercent,
                double amount,
                String customerName,
                String mobile,
                double finalAmount,
                double firstPayment,
                double totalDeposit,
                double pendingAmount,
                List<Item> items) {

        this.id = id;
        this.billNo = billNo;
        this.description = description;
        this.type = type;
        this.particular = particular;
        this.weight = weight;
        this.rate = rate;
        this.value = value;
        this.makingPercent = makingPercent;
        this.amount = amount;
        this.customerName = customerName;
        this.mobile = mobile;
        this.finalAmount = finalAmount;
        this.firstPayment = firstPayment;
        this.totalDeposit = totalDeposit;
        this.pendingAmount = pendingAmount;
        this.items = items;
    }

    public Bill(int anInt, int anInt1, String string, String string1, String string2, double aDouble, double aDouble1, double aDouble2, double aDouble3, double aDouble4, String string3, String string4, double aDouble5, double aDouble6, double aDouble7, double aDouble8) {
    }
}
