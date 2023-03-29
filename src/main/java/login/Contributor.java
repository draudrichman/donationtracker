package login;

public class Contributor extends Users{

    //Constructors for the Contributor class.
    Contributor(String username, String name, String email, String password) {
        super(username, name, email, password);
        this.setUser_type("Contributor");
    }
}
