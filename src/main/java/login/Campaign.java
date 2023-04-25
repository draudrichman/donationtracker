package login;

public class Campaign {

    private int campaignID;
    private String title;
    private String category;
    private double goalAmount;
    private double currentAmount;
    private String description;
    private String status;

    private String image;

    //Constructor for the Campaign class.
    public Campaign(int campaignID, String title, String description, double goalAmount, double currentAmount, String status, String category) {
        this.campaignID = campaignID;
        this.title = title;
        this.category = category;
        this.goalAmount = goalAmount;
        this.currentAmount = currentAmount;
        this.description = description;
        this.status = status;
    }

    //Getter and Setter methods.
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(double goalAmount) {
        this.goalAmount = goalAmount;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCampaignID() {
        return campaignID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
