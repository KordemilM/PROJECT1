package entity;

public class User {

    private String name;
    private String userName;
    private String password;
    private String securityResponse;
    private String email;
    private String phoneNumber;
    private int age;
    private String bio;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityResponse() {
        return securityResponse;
    }

    public void setSecurityResponse(String securityResponse) {
        this.securityResponse = securityResponse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "User :" + '\n' +
                "name = " + name + '\n' +
                "userName = " + userName + '\n' +
                "bio = " + bio ;
    }
}
