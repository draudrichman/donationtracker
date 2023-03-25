package login;

public class Contributor {

    //    private int reset_code;
    private String username, name, email, password;

    //Constructors for the Contributor class.
    Contributor(String username, String name, String email, String password) {

        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
//        this.reset_code = (int)(Math.random()*(9999-1000+1)+1000);
    }

    //Getter and setter methods.
//    public int getReset_code() {
//        return reset_code;
//    }

//    public void setReset_code(int reset_code) {
//        this.reset_code = reset_code;
//    }

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

}
