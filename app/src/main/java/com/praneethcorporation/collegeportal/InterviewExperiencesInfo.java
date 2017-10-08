package com.praneethcorporation.collegeportal;

/**
 * Created by user on 10/7/2017.
 */

public class InterviewExperiencesInfo {

    private String companyName;
    private String studentName;
    private String date;

   public InterviewExperiencesInfo(String companyName,String studentName,String date){
        this.companyName = companyName;
        this.studentName = studentName;
        this.date = date;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getDate() {
        return date;
    }
}
