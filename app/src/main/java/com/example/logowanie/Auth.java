package com.example.logowanie;


public class Auth {
    public String Username;
    public boolean isAdmin;
    private String Password;

    public Auth(String user, String password, Boolean admin){
        Username = user;
        Password = "@#$%"+password+"%^&*";
        isAdmin = admin;
    }

    public boolean checkPass(String user, String password){
        String passSalt = "@#$%"+password+"%^&*";
        if(passSalt.equals(Password) && user.equals(Username))
            return true;
        else
            return false;
    }
}