package com.championclub_balirmath.com.Model;

public class WalletModel {
    private String senderName, paymentID;
    private long sendingTime, payAmount;

    public WalletModel() {
    }

    public WalletModel(String senderName, String paymentID, long sendingTime, long payAmount) {
        this.senderName = senderName;
        this.paymentID = paymentID;
        this.sendingTime = sendingTime;
        this.payAmount = payAmount;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public long getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(long sendingTime) {
        this.sendingTime = sendingTime;
    }

    public long getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(long payAmount) {
        this.payAmount = payAmount;
    }
}
