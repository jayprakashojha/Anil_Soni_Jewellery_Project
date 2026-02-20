package com.example.myapplication1;


public class PaymentTransactionBean {

    private long id;              // Primary Key
    private long customerId;      // Foreign Key (Customer ID)
    private double amount;        // Payment Amount
    private String paymentType;   // CASH / UPI / BANK
    private String note;          // Optional remark
    private String paymentDate;   // Stored as String (yyyy-MM-dd HH:mm)

    public PaymentTransactionBean() {
    }

    public PaymentTransactionBean(long id,
                              long customerId,
                              double amount,
                              String paymentType,
                              String note,
                              String paymentDate) {

        this.id = id;
        this.customerId = customerId;
        this.amount = amount;
        this.paymentType = paymentType;
        this.note = note;
        this.paymentDate = paymentDate;
    }

    // Getters

    public long getId() {
        return id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getNote() {
        return note;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    // Setters (future edit feature ke liye useful)

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
