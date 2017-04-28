package com.example.adgal.astateliving.model;

/**
 * Created by Sulav.Timsina on 3/8/2017.
 */

public class RoomMateItem {

    int alreadyInApt;
    int matesNeeded;
    char preferredGender;
    int numOfMales;
    int numOfFemales;
    String aptName;
    int aptNumber;

    public RoomMateItem(int matesNeeded, String aptName) {
        this.matesNeeded = matesNeeded;
        this.aptName = aptName;
    }

    public RoomMateItem(int alreadyInApt, int matesNeeded, char preferredGender, int numOfMales, int numOfFemales, String aptName, int aptNumber) {
        this.alreadyInApt = alreadyInApt;
        this.matesNeeded = matesNeeded;
        this.preferredGender = preferredGender;
        this.numOfMales = numOfMales;
        this.numOfFemales = numOfFemales;
        this.aptName = aptName;
        this.aptNumber = aptNumber;
    }

    public int getAlreadyInApt() {
        return alreadyInApt;
    }

    public void setAlreadyInApt(int alreadyInApt) {
        this.alreadyInApt = alreadyInApt;
    }

    public int getMatesNeeded() {
        return matesNeeded;
    }

    public void setMatesNeeded(int matesNeeded) {
        this.matesNeeded = matesNeeded;
    }

    public char getPreferredGender() {
        return preferredGender;
    }

    public void setPreferredGender(char preferredGender) {
        this.preferredGender = preferredGender;
    }

    public int getNumOfMales() {
        return numOfMales;
    }

    public void setNumOfMales(int numOfMales) {
        this.numOfMales = numOfMales;
    }

    public int getNumOfFemales() {
        return numOfFemales;
    }

    public void setNumOfFemales(int numOfFemales) {
        this.numOfFemales = numOfFemales;
    }

    public String getAptName() {
        return aptName;
    }

    public void setAptName(String aptName) {
        this.aptName = aptName;
    }

    public int getAptNumber() {
        return aptNumber;
    }

    public void setAptNumber(int aptNumber) {
        this.aptNumber = aptNumber;
    }
}
