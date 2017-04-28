package com.example.adgal.astateliving.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RoomMate implements Parcelable {


    private String uid;
    private String title;
    private String description;
    private String noOfMates;
    private String googleUid;   //google email

    private String placeId;
    private int rent;           //montly rent
    private String place;
    private String preferredGender;
    private int maleCountInHouse;       //no. of males already in apt
    private int femaleCountInHouse;     //no. of females already in apt
    private double lat;     //latitude of apt
    private double lng;     //longitude of apt
    private String placeName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoomMate)) return false;

        RoomMate roomMate = (RoomMate) o;

        if (getRent() != roomMate.getRent()) return false;
        if (getMaleCountInHouse() != roomMate.getMaleCountInHouse()) return false;
        if (getFemaleCountInHouse() != roomMate.getFemaleCountInHouse()) return false;
        if (Double.compare(roomMate.getLat(), getLat()) != 0) return false;
        if (Double.compare(roomMate.getLng(), getLng()) != 0) return false;
        if (getUid() != null ? !getUid().equals(roomMate.getUid()) : roomMate.getUid() != null)
            return false;
        if (getTitle() != null ? !getTitle().equals(roomMate.getTitle()) : roomMate.getTitle() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(roomMate.getDescription()) : roomMate.getDescription() != null)
            return false;
        if (getNoOfMates() != null ? !getNoOfMates().equals(roomMate.getNoOfMates()) : roomMate.getNoOfMates() != null)
            return false;
        if (getGoogleUid() != null ? !getGoogleUid().equals(roomMate.getGoogleUid()) : roomMate.getGoogleUid() != null)
            return false;
        if (getPlaceId() != null ? !getPlaceId().equals(roomMate.getPlaceId()) : roomMate.getPlaceId() != null)
            return false;
        if (getPlace() != null ? !getPlace().equals(roomMate.getPlace()) : roomMate.getPlace() != null)
            return false;
        if (getPreferredGender() != null ? !getPreferredGender().equals(roomMate.getPreferredGender()) : roomMate.getPreferredGender() != null)
            return false;
        return getPlaceName() != null ? getPlaceName().equals(roomMate.getPlaceName()) : roomMate.getPlaceName() == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getUid() != null ? getUid().hashCode() : 0;
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getNoOfMates() != null ? getNoOfMates().hashCode() : 0);
        result = 31 * result + (getGoogleUid() != null ? getGoogleUid().hashCode() : 0);
        result = 31 * result + (getPlaceId() != null ? getPlaceId().hashCode() : 0);
        result = 31 * result + getRent();
        result = 31 * result + (getPlace() != null ? getPlace().hashCode() : 0);
        result = 31 * result + (getPreferredGender() != null ? getPreferredGender().hashCode() : 0);
        result = 31 * result + getMaleCountInHouse();
        result = 31 * result + getFemaleCountInHouse();
        temp = Double.doubleToLongBits(getLat());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getLng());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (getPlaceName() != null ? getPlaceName().hashCode() : 0);
        return result;
    }

    public String getPlaceName() {

        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;

    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getMaleCountInHouse() {
        return maleCountInHouse;
    }

    public void setMaleCountInHouse(int maleCountInHouse) {
        this.maleCountInHouse = maleCountInHouse;
    }

    public int getFemaleCountInHouse() {
        return femaleCountInHouse;
    }

    public void setFemaleCountInHouse(int femaleCountInHouse) {
        this.femaleCountInHouse = femaleCountInHouse;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPreferredGender() {
        return preferredGender;
    }

    public void setPreferredGender(String preferredGender) {
        this.preferredGender = preferredGender;
    }

    public String getNoOfMates() {
        return noOfMates;
    }

    public void setNoOfMates(String noOfMates) {
        this.noOfMates = noOfMates;
    }

    public String getTitle() {
        return title;
    }

    public String getGoogleUid() {
        return googleUid;
    }

    public void setGoogleUid(String googleUid) {
        this.googleUid = googleUid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUid() {
        return uid;
    }
    /**
    *set the google email address of use
    **/
    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    // empty public constructor as required by Firebase
    public RoomMate() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.noOfMates);
        dest.writeString(this.googleUid);
        dest.writeString(this.placeId);
        dest.writeInt(this.rent);
        dest.writeString(this.place);
        dest.writeString(this.preferredGender);
        dest.writeInt(this.maleCountInHouse);
        dest.writeInt(this.femaleCountInHouse);
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lng);
        dest.writeString(this.placeName);
    }

    protected RoomMate(Parcel in) {
        this.uid = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.noOfMates = in.readString();
        this.googleUid = in.readString();
        this.placeId = in.readString();
        this.rent = in.readInt();
        this.place = in.readString();
        this.preferredGender = in.readString();
        this.maleCountInHouse = in.readInt();
        this.femaleCountInHouse = in.readInt();
        this.lat = in.readDouble();
        this.lng = in.readDouble();
        this.placeName = in.readString();
    }

    public static final Creator<RoomMate> CREATOR = new Creator<RoomMate>() {
        @Override
        public RoomMate createFromParcel(Parcel source) {
            return new RoomMate(source);
        }

        @Override
        public RoomMate[] newArray(int size) {
            return new RoomMate[size];
        }
    };
}
