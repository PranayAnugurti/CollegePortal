package com.praneethcorporation.collegeportal.InfoClasses;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 10/12/2017.
 */

public class User {

    private String regNo;


    Context context;
    SharedPreferences sharedPreferences;


    public User(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);

    }

    public void remove() {
        sharedPreferences.edit().clear().commit();
    }

    public String getRegNo() {

        regNo = sharedPreferences.getString("regNo", "");
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
        sharedPreferences.edit().putString("regNo", regNo).commit();
    }


}

