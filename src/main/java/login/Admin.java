package login;

import java.util.ArrayList;

public class Admin {

    private final String username = "admin";
    private final String password = "admin";

    ArrayList<Campaign> activeCampaign;
    ArrayList<Campaign> finishedCampaign;

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void addCampaign(Campaign c){
        activeCampaign.add(c);
    }

    public void removeCampaign(Campaign c){
        activeCampaign.remove(c);
        finishedCampaign.add(c);
    }
}
