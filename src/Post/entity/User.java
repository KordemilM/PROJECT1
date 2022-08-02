package Post.entity;

public class User {
    private String username;
    private boolean isBusiness;



    public String getName() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }

    public boolean isBusiness() {
        return isBusiness;
    }

    public void setBusiness(boolean isBusiness) {
        this.isBusiness = isBusiness;
    }


}
