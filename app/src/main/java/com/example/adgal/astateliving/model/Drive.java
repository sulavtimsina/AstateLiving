package com.example.adgal.astateliving.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ADGAL on 4/29/2017.
 */

public class Drive implements Parcelable{
    String uid;
    String from;
    String to;
    String date;
    String email,b,c,d,e,f,g,h;
    int i,j,k,l,m,n,o,p,q;

    @Override
    public boolean equals(Object o1) {
        if (this == o1) return true;
        if (!(o1 instanceof Drive)) return false;

        Drive drive = (Drive) o1;

        if (getI() != drive.getI()) return false;
        if (getJ() != drive.getJ()) return false;
        if (getK() != drive.getK()) return false;
        if (getL() != drive.getL()) return false;
        if (getM() != drive.getM()) return false;
        if (getN() != drive.getN()) return false;
        if (getO() != drive.getO()) return false;
        if (getP() != drive.getP()) return false;
        if (getQ() != drive.getQ()) return false;
        if (getAaa() != drive.getAaa()) return false;
        if (getBbb() != drive.getBbb()) return false;
        if (getCcc() != drive.getCcc()) return false;
        if (getDdd() != drive.getDdd()) return false;
        if (getEee() != drive.getEee()) return false;
        if (getFff() != drive.getFff()) return false;
        if (isAa() != drive.isAa()) return false;
        if (isBb() != drive.isBb()) return false;
        if (isCc() != drive.isCc()) return false;
        if (isDd() != drive.isDd()) return false;
        if (isEe() != drive.isEe()) return false;
        if (getUid() != null ? !getUid().equals(drive.getUid()) : drive.getUid() != null)
            return false;
        if (getFrom() != null ? !getFrom().equals(drive.getFrom()) : drive.getFrom() != null)
            return false;
        if (getTo() != null ? !getTo().equals(drive.getTo()) : drive.getTo() != null) return false;
        if (getDate() != null ? !getDate().equals(drive.getDate()) : drive.getDate() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(drive.getEmail()) : drive.getEmail() != null) return false;
        if (getB() != null ? !getB().equals(drive.getB()) : drive.getB() != null) return false;
        if (getC() != null ? !getC().equals(drive.getC()) : drive.getC() != null) return false;
        if (getD() != null ? !getD().equals(drive.getD()) : drive.getD() != null) return false;
        if (getE() != null ? !getE().equals(drive.getE()) : drive.getE() != null) return false;
        if (getF() != null ? !getF().equals(drive.getF()) : drive.getF() != null) return false;
        if (getG() != null ? !getG().equals(drive.getG()) : drive.getG() != null) return false;
        if (getH() != null ? !getH().equals(drive.getH()) : drive.getH() != null) return false;
        return getTime() != null ? getTime().equals(drive.getTime()) : drive.getTime() == null;

    }

    @Override
    public int hashCode() {
        int result = getUid() != null ? getUid().hashCode() : 0;
        result = 31 * result + (getFrom() != null ? getFrom().hashCode() : 0);
        result = 31 * result + (getTo() != null ? getTo().hashCode() : 0);
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getB() != null ? getB().hashCode() : 0);
        result = 31 * result + (getC() != null ? getC().hashCode() : 0);
        result = 31 * result + (getD() != null ? getD().hashCode() : 0);
        result = 31 * result + (getE() != null ? getE().hashCode() : 0);
        result = 31 * result + (getF() != null ? getF().hashCode() : 0);
        result = 31 * result + (getG() != null ? getG().hashCode() : 0);
        result = 31 * result + (getH() != null ? getH().hashCode() : 0);
        result = 31 * result + getI();
        result = 31 * result + getJ();
        result = 31 * result + getK();
        result = 31 * result + getL();
        result = 31 * result + getM();
        result = 31 * result + getN();
        result = 31 * result + getO();
        result = 31 * result + getP();
        result = 31 * result + getQ();
        result = 31 * result + (int) (getAaa() ^ (getAaa() >>> 32));
        result = 31 * result + (int) (getBbb() ^ (getBbb() >>> 32));
        result = 31 * result + (int) (getCcc() ^ (getCcc() >>> 32));
        result = 31 * result + (int) (getDdd() ^ (getDdd() >>> 32));
        result = 31 * result + (int) (getEee() ^ (getEee() >>> 32));
        result = 31 * result + (int) (getFff() ^ (getFff() >>> 32));
        result = 31 * result + (isAa() ? 1 : 0);
        result = 31 * result + (isBb() ? 1 : 0);
        result = 31 * result + (isCc() ? 1 : 0);
        result = 31 * result + (isDd() ? 1 : 0);
        result = 31 * result + (isEe() ? 1 : 0);
        result = 31 * result + (getTime() != null ? getTime().hashCode() : 0);
        return result;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    public String getG() {
        return g;
    }

    public void setG(String g) {
        this.g = g;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l = l;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getO() {
        return o;
    }

    public void setO(int o) {
        this.o = o;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public int getQ() {
        return q;
    }

    public void setQ(int q) {
        this.q = q;
    }

    public long getAaa() {
        return aaa;
    }

    public void setAaa(long aaa) {
        this.aaa = aaa;
    }

    public long getBbb() {
        return bbb;
    }

    public void setBbb(long bbb) {
        this.bbb = bbb;
    }

    public long getCcc() {
        return ccc;
    }

    public void setCcc(long ccc) {
        this.ccc = ccc;
    }

    public long getDdd() {
        return ddd;
    }

    public void setDdd(long ddd) {
        this.ddd = ddd;
    }

    public long getEee() {
        return eee;
    }

    public void setEee(long eee) {
        this.eee = eee;
    }

    public long getFff() {
        return fff;
    }

    public void setFff(long fff) {
        this.fff = fff;
    }

    public boolean isAa() {
        return aa;
    }

    public void setAa(boolean aa) {
        this.aa = aa;
    }

    public boolean isBb() {
        return bb;
    }

    public void setBb(boolean bb) {
        this.bb = bb;
    }

    public boolean isCc() {
        return cc;
    }

    public void setCc(boolean cc) {
        this.cc = cc;
    }

    public boolean isDd() {
        return dd;
    }

    public void setDd(boolean dd) {
        this.dd = dd;
    }

    public boolean isEe() {
        return ee;
    }

    public void setEe(boolean ee) {
        this.ee = ee;
    }

    long aaa,bbb,ccc,ddd,eee,fff;
    boolean aa,bb,cc,dd,ee;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    String time;

    public Drive() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.from);
        dest.writeString(this.to);
        dest.writeString(this.date);
        dest.writeString(this.email);
        dest.writeString(this.b);
        dest.writeString(this.c);
        dest.writeString(this.d);
        dest.writeString(this.e);
        dest.writeString(this.f);
        dest.writeString(this.g);
        dest.writeString(this.h);
        dest.writeInt(this.i);
        dest.writeInt(this.j);
        dest.writeInt(this.k);
        dest.writeInt(this.l);
        dest.writeInt(this.m);
        dest.writeInt(this.n);
        dest.writeInt(this.o);
        dest.writeInt(this.p);
        dest.writeInt(this.q);
        dest.writeLong(this.aaa);
        dest.writeLong(this.bbb);
        dest.writeLong(this.ccc);
        dest.writeLong(this.ddd);
        dest.writeLong(this.eee);
        dest.writeLong(this.fff);
        dest.writeByte(this.aa ? (byte) 1 : (byte) 0);
        dest.writeByte(this.bb ? (byte) 1 : (byte) 0);
        dest.writeByte(this.cc ? (byte) 1 : (byte) 0);
        dest.writeByte(this.dd ? (byte) 1 : (byte) 0);
        dest.writeByte(this.ee ? (byte) 1 : (byte) 0);
        dest.writeString(this.time);
    }

    protected Drive(Parcel in) {
        this.uid = in.readString();
        this.from = in.readString();
        this.to = in.readString();
        this.date = in.readString();
        this.email = in.readString();
        this.b = in.readString();
        this.c = in.readString();
        this.d = in.readString();
        this.e = in.readString();
        this.f = in.readString();
        this.g = in.readString();
        this.h = in.readString();
        this.i = in.readInt();
        this.j = in.readInt();
        this.k = in.readInt();
        this.l = in.readInt();
        this.m = in.readInt();
        this.n = in.readInt();
        this.o = in.readInt();
        this.p = in.readInt();
        this.q = in.readInt();
        this.aaa = in.readLong();
        this.bbb = in.readLong();
        this.ccc = in.readLong();
        this.ddd = in.readLong();
        this.eee = in.readLong();
        this.fff = in.readLong();
        this.aa = in.readByte() != 0;
        this.bb = in.readByte() != 0;
        this.cc = in.readByte() != 0;
        this.dd = in.readByte() != 0;
        this.ee = in.readByte() != 0;
        this.time = in.readString();
    }

    public static final Creator<Drive> CREATOR = new Creator<Drive>() {
        @Override
        public Drive createFromParcel(Parcel source) {
            return new Drive(source);
        }

        @Override
        public Drive[] newArray(int size) {
            return new Drive[size];
        }
    };
}
