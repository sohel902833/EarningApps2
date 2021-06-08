package com.tanvir.itask24.Model;

public class Setting {
    int coinValue;
    long firstCaptchaDelayTime,secondCaptchaDelayTime,thirdCaptchaDelayTime,fourthCaptchaDelayTime,watchDelayTime;
    int cap1,cap2,cap3,cap4,offer,minW,bkash,paytm;
    String showOffer;
    public Setting(int bkash,int paytm,int coinValue, long firstCaptchaDelayTime, long secondCaptchaDelayTime, long thirdCaptchaDelayTime,long fourthCaptchaDelayTime, long watchDelayTime,
        int cap1,int cap2,int cap3,int cap4,int offer,int minW,String showOffer) {
        this.bkash=bkash;
        this.paytm=paytm;
        this.coinValue = coinValue;
        this.firstCaptchaDelayTime = firstCaptchaDelayTime;
        this.secondCaptchaDelayTime = secondCaptchaDelayTime;
        this.thirdCaptchaDelayTime = thirdCaptchaDelayTime;
        this.fourthCaptchaDelayTime = fourthCaptchaDelayTime;
        this.cap1=cap1;
        this.cap2=cap2;
        this.cap3=cap3;
        this.cap4=cap4;
        this.offer=offer;
        this.minW=minW;
        this.showOffer=showOffer;
    }

    public int getBkash() {
        return bkash;
    }

    public void setBkash(int bkash) {
        this.bkash = bkash;
    }

    public int getPaytm() {
        return paytm;
    }

    public void setPaytm(int paytm) {
        this.paytm = paytm;
    }

    public int getCoinValue() {
        return coinValue;
    }

    public void setCoinValue(int coinValue) {
        this.coinValue = coinValue;
    }

    public long getFirstCaptchaDelayTime() {
        return firstCaptchaDelayTime;
    }

    public void setFirstCaptchaDelayTime(long firstCaptchaDelayTime) {
        this.firstCaptchaDelayTime = firstCaptchaDelayTime;
    }

    public long getSecondCaptchaDelayTime() {
        return secondCaptchaDelayTime;
    }

    public void setSecondCaptchaDelayTime(long secondCaptchaDelayTime) {
        this.secondCaptchaDelayTime = secondCaptchaDelayTime;
    }

    public long getThirdCaptchaDelayTime() {
        return thirdCaptchaDelayTime;
    }

    public void setThirdCaptchaDelayTime(long thirdCaptchaDelayTime) {
        this.thirdCaptchaDelayTime = thirdCaptchaDelayTime;
    }

    public long getWatchDelayTime() {
        return watchDelayTime;
    }

    public void setWatchDelayTime(long fourthCaptchaDelayTime) {
        this.watchDelayTime = fourthCaptchaDelayTime;
    }

    public int getCap1() {
        return cap1;
    }

    public void setCap1(int cap1) {
        this.cap1 = cap1;
    }

    public int getCap2() {
        return cap2;
    }

    public void setCap2(int cap2) {
        this.cap2 = cap2;
    }

    public int getCap3() {
        return cap3;
    }

    public void setCap3(int cap3) {
        this.cap3 = cap3;
    }


    public int getOffer() {
        return offer;
    }

    public void setOffer(int offer) {
        this.offer = offer;
    }

    public int getMinW() {
        return minW;
    }

    public long getFourthCaptchaDelayTime() {
        return fourthCaptchaDelayTime;
    }

    public void setFourthCaptchaDelayTime(long fourthCaptchaDelayTime) {
        this.fourthCaptchaDelayTime = fourthCaptchaDelayTime;
    }

    public int getCap4() {
        return cap4;
    }

    public void setCap4(int cap4) {
        this.cap4 = cap4;
    }

    public void setMinW(int minW) {
        this.minW = minW;
    }

    public String getShowOffer() {
        return showOffer;
    }

    public void setShowOffer(String showOffer) {
        this.showOffer = showOffer;
    }
}
