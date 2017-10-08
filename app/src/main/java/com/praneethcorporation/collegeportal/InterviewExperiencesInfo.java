package com.praneethcorporation.collegeportal;

/**
 * Created by user on 10/7/2017.
 */

public class InterviewExperiencesInfo {

    private String reg_no;
    private String companyName;
    private String studentName;
    private String date;
    private String experience;

   public InterviewExperiencesInfo(String reg_no,String companyName,String studentName,String date,String experience){
        this.reg_no=reg_no;
        this.companyName = companyName;
        this.studentName = studentName;
        this.date = date;
        this.experience=experience;
    }

    public String getReg_no(){return reg_no;}

    public String getExperience() {
        return experience;
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
