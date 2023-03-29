package login;

import java.util.ArrayList;

public class Users {

    private int reset_code;
    private String username, name, email, password, user_type;
    private double balance;

    //Variables to store campaign details in which the Contributors are contributing.
    //And campaign details that the Fundraiser is starting.

    ArrayList<Campaign> campaigns;
    private int campaignCount;

    //Constructors for the Users class.
    Users(String username, String name, String email, String password){

        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.reset_code = (int)(Math.random()*(9999-1000+1)+1000);
    }

    //Getter and setter methods.
    public int getReset_code() {
        return reset_code;
    }

    public void setReset_code(int reset_code) {
        this.reset_code = reset_code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
