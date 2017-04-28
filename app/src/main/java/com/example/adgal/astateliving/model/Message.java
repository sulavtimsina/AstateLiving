package com.example.adgal.astateliving.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sulav.Timsina on 4/28/2017.
 */

public class Message implements Parcelable {
    String email;
    String uid;
    String message;
    String phone;

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;

        Message message1 = (Message) o;

        if (getEmail() != null ? !getEmail().equals(message1.getEmail()) : message1.getEmail() != null)
            return false;
        if (getUid() != null ? !getUid().equals(message1.getUid()) : message1.getUid() != null)
            return false;
        if (getMessage() != null ? !getMessage().equals(message1.getMessage()) : message1.getMessage() != null)
            return false;
        return getPhone() != null ? getPhone().equals(message1.getPhone()) : message1.getPhone() == null;

    }

    @Override
    public int hashCode() {
        int result = getEmail() != null ? getEmail().hashCode() : 0;
        result = 31 * result + (getUid() != null ? getUid().hashCode() : 0);
        result = 31 * result + (getMessage() != null ? getMessage().hashCode() : 0);
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        return result;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.email);
        dest.writeString(this.uid);
        dest.writeString(this.message);
        dest.writeString(this.phone);
    }

    public Message() {
    }

    protected Message(Parcel in) {
        this.email = in.readString();
        this.uid = in.readString();
        this.message = in.readString();
        this.phone = in.readString();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
