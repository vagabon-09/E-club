package com.championclub_balirmath.com.Model;

public class WalletModel {
    private String upi;
    private long totalAmount;

    public WalletModel() {
    }

    public WalletModel(String upi, long totalAmount) {
        this.upi = upi;
        this.totalAmount = totalAmount;
    }

    public String getUpi() {
        return upi;
    }

    public void setUpi(String upi) {
        this.upi = upi;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }
}
