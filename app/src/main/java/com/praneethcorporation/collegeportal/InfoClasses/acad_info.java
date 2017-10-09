package com.praneethcorporation.collegeportal.InfoClasses;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PRANAYKUMAR on 01-09-2017.
 */

public class acad_info implements Parcelable{
  public static String name;
  public static String reg_no;
  public static String school_10;
  public static String board_10;
  public static String year_10;
  public static String per_10;
  public static String school_12;
  public static String board_12;
  public static String year_12;
  public static String per_12;
  public static String spi1;
  public static String spi2;
  public static String spi3;
  public static String spi4;
  public static String spi5;
  public static String spi6;
  public static String spi7;
  public static String spi8;
  public static String cpi;

  public acad_info(
      String reg_no,
      String name,
      String school_10,
      String board_10,
      String year_10,
      String per_10,
      String school_12,
      String board_12,
      String year_12,
      String per_12,
      String spi1,
      String spi2,
      String spi3,
      String spi4,
      String spi5,
      String spi6,
      String spi7,
      String spi8,
      String cpi){
    this.reg_no=reg_no;
    this.name=name;
    this.school_10=school_10;
    this.board_10=board_10;
    this.year_10=year_10;
    this.per_10=per_10;
    this.school_12=school_12;
    this.board_12=board_12;
    this.year_12=year_12;
    this.per_12=per_12;
    this.spi1=spi1;
    this.spi2=spi2;
    this.spi3=spi3;
    this.spi4=spi4;
    this.spi5=spi5;
    this.spi6=spi6;
    this.spi7=spi7;
    this.spi8=spi8;
    this.cpi=cpi;
  }


  protected acad_info(Parcel in) {

    reg_no=in.readString();
    name = in.readString();
    school_10 = in.readString();
    board_10 = in.readString();
    year_10 = in.readString();
    per_10 = in.readString();
    school_12 = in.readString();
    board_12 = in.readString();
    year_12 = in.readString();
    per_12 = in.readString();
    spi1 = in.readString();
    spi2 = in.readString();
    spi3 = in.readString();
    spi4 = in.readString();
    spi5 = in.readString();
    spi6 = in.readString();
    spi7 = in.readString();
    spi8 = in.readString();
    cpi = in.readString();
  }

  public static final Creator<acad_info> CREATOR = new Creator<acad_info>() {
    @Override
    public acad_info createFromParcel(Parcel in) {
      return new acad_info(in);
    }

    @Override
    public acad_info[] newArray(int size) {
      return new acad_info[size];
    }
  };

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(reg_no);
    dest.writeString(name);
    dest.writeString(school_10);
    dest.writeString(board_10);
    dest.writeString(year_10);
    dest.writeString(per_10);
    dest.writeString(school_12);
    dest.writeString(board_12);
    dest.writeString(year_12);
    dest.writeString(per_12);
    dest.writeString(spi1);
    dest.writeString(spi2);
    dest.writeString(spi3);
    dest.writeString(spi4);
    dest.writeString(spi5);
    dest.writeString(spi6);
    dest.writeString(spi7);
    dest.writeString(spi8);
    dest.writeString(cpi);
  }
}
