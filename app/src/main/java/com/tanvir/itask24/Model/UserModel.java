package com.tanvir.itask24.Model;

public class UserModel {
    String name,phone,email,profileImage,videoState;
    long cap1LastPlay,cap2LastPlay,cap3LastPlay,cap4LastPlay,watchLastPlay;
    int coins;
    String myReferCode;

    public UserModel (){

    }


    public UserModel(String name, String phone, String email,String profileImage,String videoState, long cap1LastPlay, long cap2LastPlay, long cap3LastPlay,long cap4LastPlay, long watchLastPlay, int coins,String myReferCode) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.profileImage=profileImage;
        this.videoState=videoState;
        this.cap1LastPlay = cap1LastPlay;
        this.cap2LastPlay = cap2LastPlay;
        this.cap3LastPlay = cap3LastPlay;
        this.cap4LastPlay=cap4LastPlay;
        this.watchLastPlay = watchLastPlay;
        this.coins = coins;
        this.myReferCode=myReferCode;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getVideoState() {
        return videoState;
    }

    public void setVideoState(String videoState) {
        this.videoState = videoState;
    }

    public long getCap1LastPlay() {
        return cap1LastPlay;
    }
    public void setCap1LastPlay(long cap1LastPlay) {
        this.cap1LastPlay = cap1LastPlay;
    }
    public long getCap2LastPlay() {
        return cap2LastPlay;
    }
    public void setCap2LastPlay(long cap2LastPlay) {
        this.cap2LastPlay = cap2LastPlay;
    }
    public long getCap3LastPlay() {
        return cap3LastPlay;
    }
    public void setCap3LastPlay(long cap3LastPlay) {
        this.cap3LastPlay = cap3LastPlay;
    }

    public long getCap4LastPlay() {
        return cap4LastPlay;
    }

    public void setCap4LastPlay(long cap4LastPlay) {
        this.cap4LastPlay = cap4LastPlay;
    }

    public long getWatchLastPlay() {
        return watchLastPlay;
    }
    public void setWatchLastPlay(long watchLastPlay) {
        this.watchLastPlay = watchLastPlay;
    }
    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String getMyReferCode() {
        return myReferCode;
    }

    public void setMyReferCode(String myReferCode) {
        this.myReferCode = myReferCode;
    }
}
