package com.ambongan.weassist.modal;
public class dataLogin {
    private String id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String gender;
    private String grade;
    private String as;
    private String emailid;

    public dataLogin(String username,String emailid, String firstname, String lastname, String gender, String grade, String password, String as) {
        this.username = username;
        this.emailid = emailid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.grade = grade;
        this.password = password;
        this.as = as;
    }

    public dataLogin() {
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getGender() {
        return gender;
    }

    public String getGrade() {
        return grade;
    }

    public String getPassword() {
        return password;
    }

    public void setAs(String as) {
        this.as = as;
    }

    public String getAs() {
        return as;
    }

}