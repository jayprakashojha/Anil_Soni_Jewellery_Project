package com.example.myapplication1;

public class CustomerBean {

    private long id;
    private String billNo;
    private String description;
    private String customerName;
    private String mobile;

    private double grandTotal;
    private double totalDeposit;
    private double pendingAmount;

    public CustomerBean() {}

    public CustomerBean(long id,
                        String billNo,
                        String description,
                        String customerName,
                        String mobile,
                        double grandTotal,
                        double totalDeposit,
                        double pendingAmount) {

        this.id = id;
        this.billNo = billNo;
        this.description = description;
        this.customerName = customerName;
        this.mobile = mobile;
        this.grandTotal = grandTotal;
        this.totalDeposit = totalDeposit;
        this.pendingAmount = pendingAmount;
    }

    public long getId() { return id; }

    public String getBillNo() { return billNo; }

    public String getDescription() { return description; }

    public String getCustomerName() { return customerName; }

    public String getMobile() { return mobile; }

    public double getGrandTotal() { return grandTotal; }

    public double getTotalDeposit() { return totalDeposit; }

    public double getPendingAmount() { return pendingAmount; }
}
