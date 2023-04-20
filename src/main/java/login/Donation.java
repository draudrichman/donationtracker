package login;

import java.sql.Date;
import java.sql.Timestamp;

public class Donation {
    private int donationID;
    private int campaignID;
    private int userID;
    private double amount;
    private String method;
    private Timestamp createdAt;
    private String status;

    // Constructor
    public Donation(int donationID, int campaignID, int userID, double amount, String method, Timestamp createdAt, String status) {
        this.donationID = donationID;
        this.campaignID = campaignID;
        this.userID = userID;
        this.amount = amount;
        this.method = method;
        this.createdAt = createdAt;
        this.status = status;
    }

    // Getters and Setters
    public int getCampaignID() {
        return campaignID;
    }

    public int getUserID() {
        return userID;
    }

    public int getDonationID() {
        return donationID;
    }

    public double getAmount() {
        return amount;
    }

    public String getMethod() {
        return method;
    }


    public Timestamp getCreatedAt() {
        return createdAt;
    }


    public String getStatus() {
        return status;
    }

}
