package com.example.myapplication1;

public class Bill {

    public int id;              // Primary Key (SQLite)
    public int billNo;           // Auto generated (1–9999)
    public String description;
    public String customerName;
    public String mobile;
    public double finalAmount;
    public double firstPayment;
    public double totalDeposit;

    public Bill(int id,
                int billNo,
                String description,
                String customerName,
                String mobile,
                double finalAmount,
                double firstPayment,
                double totalDeposit) {

        this.id = id;
        this.billNo = billNo;
        this.description = description;
        this.customerName = customerName;
        this.mobile = mobile;
        this.finalAmount = finalAmount;
        this.firstPayment = firstPayment;
        this.totalDeposit = totalDeposit;
    }

    public Bill() {

    }
}
