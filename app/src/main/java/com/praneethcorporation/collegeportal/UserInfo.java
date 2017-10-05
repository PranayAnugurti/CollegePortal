package com.praneethcorporation.collegeportal;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PRANAYKUMAR on 30-08-2017.
 */

public class UserInfo implements Parcelable{

    public static  String reg_no;
    public static  String name;
    public static  String pass;
    public static  String image_url;
    public static  String image_server_link;
    public static  String pdf_server_link;
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

    public UserInfo(String reg_no,String name,String pass,String image_url,String image_server_link,String pdf_server_link,String course,String branch,
                    String dob,String email,String skype,String linkedin,String gender,
                    String category,String phd,String residential_status,String
                            guardian,String present_address,String permanent_address,String marital_status,String state,String country){
        this.reg_no=reg_no;
        this.name=name;
        this.pass=pass;
        this.image_url=image_url;
        this.image_server_link=image_server_link;
        this.pdf_server_link=pdf_server_link;
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

    protected UserInfo(Parcel in) {
        this.reg_no=in.readString();//in.readString();//in.readString();
        this.name=in.readString();//name;
        this.pass=in.readString();//pass;
        this.image_url=in.readString();//image_url;
        this.image_server_link=in.readString();
        this.pdf_server_link=in.readString();
        this.course=in.readString();//course;
        this.branch=in.readString();//branch;
        this.dob=in.readString();//dob;
        this.email=in.readString();//email;
        this.skype=in.readString();//skype;
        this.linkedin=in.readString();//linkedin;
        this.gender=in.readString();//gender;
        this.category=in.readString();//category;
        this.phd=in.readString();//phd;
        this.residential_status=in.readString();//residential_status;
        this.guardian=in.readString();//guardian;
        this.present_address=in.readString();//present_address;
        this.permanent_address=in.readString();//permanent_address;
        this.marital_status=in.readString();//marital_status;
        this.state=in.readString();//state;
        this.country=in.readString();//country;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(reg_no);
        dest.writeString(name);
        dest.writeString(pass);
        dest.writeString(image_url);
        dest.writeString(image_server_link);
        dest.writeString(pdf_server_link);
        dest.writeString(course);
        dest.writeString(branch);
        dest.writeString(dob);
        dest.writeString(email);
        dest.writeString(skype);
        dest.writeString(linkedin);
        dest.writeString(gender);
        dest.writeString(category);
        dest.writeString(phd);
        dest.writeString(residential_status);
        dest.writeString(guardian);
        dest.writeString(present_address);
        dest.writeString(permanent_address);
        dest.writeString(marital_status);
        dest.writeString(state);
        dest.writeString(country);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
