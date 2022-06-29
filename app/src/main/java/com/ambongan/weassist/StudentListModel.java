package com.ambongan.weassist;

import java.io.Serializable;

public class StudentListModel implements Serializable {

    private String username;
    private String emailid;
    private String firstname;
    private String lastname;
    private String gender;
    private String grade;
    private String password;
    private String as;
    private String key;

    public StudentListModel() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lasttname) {
        this.lastname = lasttname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPassword() {
        return password;
    }

    public String getAs() {
        return as;
    }

    public void setAs(String asa) {
        this.as = asa;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    @Override
    public String toString() {
        return " " + firstname + "\n" +
                " " + emailid;
    }

    public StudentListModel(String username, String emailid, String firstname, String lastname, String gender, String grade, String password, String as) {
        this.username = username;
        this.emailid = emailid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.grade = grade;
        this.password = password;
        this.as = as;
    }
}
