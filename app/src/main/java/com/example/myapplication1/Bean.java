package com.example.myapplication1;

public class Bean {

    public int id;              // Primary Key (SQLite)
    public int billNo;           // Auto generated (1–9999)
    public String description;

    public String customerName;
    public String mobile;
    double finalAmount;
    double totalDeposit;
    double pendingAmount;

    public Bean() {
    }
    public Bean(int id,
                int billNo,
                String description,
                String customerName,
                String mobile,
                double finalAmount,
                double totalDeposit,
                double pendingAmount
                )
    {

        this.id = id;
        this.billNo = billNo;
        this.description = description;
        this.customerName = customerName;
        this.mobile = mobile;
        this.finalAmount = finalAmount;
        this.totalDeposit = totalDeposit;
        this.pendingAmount = pendingAmount;
    }
    public int getId() {
        return id;
    }

    public int getBillNo() {
        return billNo;
    }

    public String getDescription() {
        return description;
    }
    public String getCustomerName()
    {
        return customerName;
    }

}
