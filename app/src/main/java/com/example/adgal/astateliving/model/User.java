package com.example.adgal.astateliving.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sulav.Timsina on 4/27/2017.
 */

public class User implements Parcelable {

    String uid;
    String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    String email;

    public String getAptName() {
        return aptName;
    }

    public void setAptName(String aptName) {
        this.aptName = aptName;
    }

    String aptName;

    public String getUid() {
        return uid;

    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    String fullName;
    boolean isADriver;

    String gender;
    int age;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isADriver() {
        return isADriver;
    }

    public void setADriver(boolean ADriver) {
        isADriver = ADriver;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public User() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (isADriver() != user.isADriver()) return false;
        if (getAge() != user.getAge()) return false;
        if (getUid() != null ? !getUid().equals(user.getUid()) : user.getUid() != null)
            return false;
        if (getPhone() != null ? !getPhone().equals(user.getPhone()) : user.getPhone() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(user.getEmail()) : user.getEmail() != null)
            return false;
        if (getAptName() != null ? !getAptName().equals(user.getAptName()) : user.getAptName() != null)
            return false;
        if (getFullName() != null ? !getFullName().equals(user.getFullName()) : user.getFullName() != null)
            return false;
        return getGender() != null ? getGender().equals(user.getGender()) : user.getGender() == null;

    }

    @Override
    public int hashCode() {
        int result = getUid() != null ? getUid().hashCode() : 0;
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getAptName() != null ? getAptName().hashCode() : 0);
        result = 31 * result + (getFullName() != null ? getFullName().hashCode() : 0);
        result = 31 * result + (isADriver() ? 1 : 0);
        result = 31 * result + (getGender() != null ? getGender().hashCode() : 0);
        result = 31 * result + getAge();
        return result;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.phone);
        dest.writeString(this.email);
        dest.writeString(this.aptName);
        dest.writeString(this.fullName);
        dest.writeByte(this.isADriver ? (byte) 1 : (byte) 0);
        dest.writeString(this.gender);
        dest.writeInt(this.age);
    }

    protected User(Parcel in) {
        this.uid = in.readString();
        this.phone = in.readString();
        this.email = in.readString();
        this.aptName = in.readString();
        this.fullName = in.readString();
        this.isADriver = in.readByte() != 0;
        this.gender = in.readString();
        this.age = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
