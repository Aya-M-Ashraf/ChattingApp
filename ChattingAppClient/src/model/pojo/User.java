package model.pojo;

import java.io.Serializable;

public class User implements Serializable {
    
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String country;
    private String city;
    private String secuirtyQuestion;
    private String securityAnswer;
    private String status;
    private String gender;
    private boolean isOnline;
    
    public User(){
        this.email = null;
        this.firstName = null;
        this.lastName = null;
        this.password = null;
        this.country = null;
        this.city = null;
        this.secuirtyQuestion = null;
        this.securityAnswer = null;
        this.status = null;
        this.gender = null;
        this.isOnline = false;
    }

    public User(String email, String firstName, String lastName, String password, String country, String city, String secuirtyQuestion, String securityAnswer, String status, String gender, boolean isOnline) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.country = country;
        this.city = city;
        this.secuirtyQuestion = secuirtyQuestion;
        this.securityAnswer = securityAnswer;
        this.status = status;
        this.gender = gender;
        this.isOnline = isOnline;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSecuirtyQuestion() {
        return secuirtyQuestion;
    }

    public void setSecuirtyQuestion(String secuirtyQuestion) {
        this.secuirtyQuestion = secuirtyQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }   
}
