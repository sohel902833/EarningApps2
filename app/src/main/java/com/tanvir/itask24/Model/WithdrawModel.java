package com.tanvir.itask24.Model;

public class WithdrawModel {
    String userId,time,payment,coins,accountNo,state;

    public WithdrawModel(){}
    public WithdrawModel(String userId, String time, String payment, String coins, String accountNo, String state) {
        this.userId = userId;
        this.time = time;
        this.payment = payment;
        this.coins = coins;
        this.accountNo = accountNo;
        this.state = state;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
