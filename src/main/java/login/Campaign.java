package login;

public class Campaign {

    private String name, id, category, title ,description;
    private double goal_amount, current_amount;
    private boolean status;

    //Constructor for the Campaign class.
    public Campaign(String name, String id, String category, String title, String description, double goal_amount){

        this.name = name;
        this.id = id;
        this.category = category;
        this.title = title;
        this.description = description;
        this.goal_amount = goal_amount;
        this.status = true;
    }

    //Getter and Setter methods.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

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

    public double getGoal_amount() {
        return goal_amount;
    }

    public void setGoal_amount(double goal_amount) {
        this.goal_amount = goal_amount;
    }

    public double getCurrent_amount() {
        return current_amount;
    }

    public void setCurrent_amount(double current_amount) {
        this.current_amount = current_amount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
