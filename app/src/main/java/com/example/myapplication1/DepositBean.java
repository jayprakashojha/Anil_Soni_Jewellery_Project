package com.example.myapplication1;

public class DepositBean {

    private long id;            // auto increment
    private long customerId;
    private long billId;
    private double depositAmount;
    private String date;

    public DepositBean() {}

    public DepositBean(long customerId, long billId, double depositAmount, String date) {
        this.customerId = customerId;
        this.billId = billId;
        this.depositAmount = depositAmount;
        this.date = date;
    }

    public long getId() { return id; }
    public long getCustomerId() { return customerId; }
    public long getBillId() { return billId; }
    public double getDepositAmount() { return depositAmount; }
    public String getDate() { return date; }

    public void setId(long id) { this.id = id; }
}