package com.praneethcorporation.collegeportal;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PRANAYKUMAR on 30-08-2017.
 */

public class UserInfo{

    public static  String reg_no;
    public static  String name;
    public static  String pass;
    public static  String image_url;
    public static  String course;
    public static  String branch;
    public static  String dob;
    public static  String email;
    public static  String skype;
    public static  String linkedin;
    public static  String gender;
    public static  String category;
    public static  String phd;
    public static  String residential_status;
    public static  String guardian;
    public static  String present_address;
    public static  String permanent_address;
    public static  String marital_status;
    public static  String state;
    public static  String country;

public UserInfo(String reg_no,String name,String pass,String image_url,String course,String branch,String dob,String email,String skype,String linkedin,String gender,String category,String phd,String residential_status,String
    guardian,String present_address,String permanent_address,String marital_status,String state,String country){
  this.reg_no=reg_no;
  this.name=name;
  this.pass=pass;
  this.image_url=image_url;
  this.course=course;
  this.branch=branch;
  this.dob=dob;
  this.email=email;
  this.skype=skype;
  this.linkedin=linkedin;
  this.gender=gender;
  this.category=category;
  this.phd=phd;
  this.residential_status=residential_status;
  this.guardian=guardian;
  this.present_address=present_address;
  this.permanent_address=permanent_address;
  this.marital_status=marital_status;
  this.state=state;
  this.country=country;
}
}
