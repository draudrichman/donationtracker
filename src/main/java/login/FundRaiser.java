package login;

public class FundRaiser extends Users{

    //Constructors for the Contributor class.
    public FundRaiser(String username, String name, String email, String password){
        super(username, name, email, password);
        this.setUser_type("Fundraiser");
    }

}
