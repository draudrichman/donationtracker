package login;

import java.util.ArrayList;

public class Users {

    private int user_id;
    private int reset_code;
    private String username, name, email, password, user_type;
    private String phone, address, aboutMe;
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

    //Method 1: Adds a new campaign to the user.
    public void newCampaign(Campaign c){
        this.campaigns.add(c);
        this.campaignCount++;
    }

    //Getter and setter methods.
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

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

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
