package com.example.myapplication1;

import java.util.List;

public class FullBill {

    private CustomerBean customer;
    private List<BillItemBean> items;

    public FullBill(CustomerBean customer, List<BillItemBean> items) {
        this.customer = customer;
        this.items = items;
    }

    public CustomerBean getCustomer() { return customer; }

    public List<BillItemBean> getItems() { return items; }
}
