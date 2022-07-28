package repository;

import entity.User;
import utils.Menu;
import java.sql.*;
import java.util.prefs.Preferences;

class UserRepository {

    public static void signUp(Connection connection) throws SQLException {
        String username , email , password, securityResponse , phoneNumber , bio ,repeatPassword , account;
        int age;
        User user = new User();
        System.out.println("name : ");
        String name = Menu.scanner.next();
        while (true){
            System.out.println("username :");
            username = Menu.scanner.next();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user WHERE user_name='" + username + "'");
            if(resultSet.next())
                System.out.println("a user exists with this username");
            if(!resultSet.next() && username.matches("\\w+")){
                break;
            }
        }
        while (true){
            System.out.println("password :");
            password = Menu.scanner.next();
            if(password.length()>=8 && password.matches("\\w+")){
                break;
            }
        }
        while (true){
            System.out.println("repeat password:");
            repeatPassword = Menu.scanner.next();
            if(repeatPassword.equals(password)){
                break;
            }
        }
        System.out.println("in which primary school did you start your first grade?");
        securityResponse = Menu.scanner.next();
        while (true){
            System.out.println("email :");
            email = Menu.scanner.next();
            if(email.matches("\\S+@(\\w+)\\.com")){
                break;
            }
        }
        while (true){
            System.out.println("Phone number :");
            phoneNumber = Menu.scanner.next();
            if(phoneNumber.matches("(0/91)?[7-9][0-9]{9}")){
                break;
            }
        }
        while (true){
            System.out.println("age :");
            age = Menu.scanner.nextInt();
            if(Integer.toString(age).matches("[0-9]+")) {
                break;
            }
        }
        System.out.println("bio :");
        bio = Menu.scanner.next();
        while (true){
            System.out.println("business account ? (answer with Yes or NO)");
            account = Menu.scanner.next();
            if(account.equalsIgnoreCase("yes")) {
                user.setAccount(0);
                break;
            }else if(account.equalsIgnoreCase("no")){
                user.setAccount(1);
                break;
            }
        }

        user.setName(name);   user.setUserName(username);   user.setPassword(password);
        user.setAge(age);     user.setEmail(email);         user.setPhoneNumber(phoneNumber);
        user.setBio(bio);     user.setSecurityResponse(securityResponse);

        PreparedStatement preparedStatement = connection.prepareStatement("insert into user " +
                "VALUES (?,?,?,?,?,?,?,?,?)");
        preparedStatement.setString(1,user.getName());     preparedStatement.setString(2,user.getUserName());
        preparedStatement.setString(3,user.getPassword()); preparedStatement.setString(4,user.getSecurityResponse());
        preparedStatement.setString(5,user.getEmail());    preparedStatement.setString(6,user.getPhoneNumber());
        preparedStatement.setInt(7,user.getAccount());     preparedStatement.setInt(8,user.getAge());
        preparedStatement.setString(9,user.getBio());
        preparedStatement.executeUpdate();

        System.out.println("sign up was successful");
        System.out.println("<------------------------->");
    }

    public static int login(Connection connection) throws SQLException {
        String username , password , string , answer , repeatPassword;
        System.out.println("username :");
        username = Menu.scanner.next();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE user_name=?");
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()) {
            Preferences userPreferences = Preferences.userNodeForPackage(UserRepository.class);
            userPreferences.put("id", username);

            System.out.println("Forgot password?");
            string = Menu.scanner.next();
            if (string.equalsIgnoreCase("yes")) {
                System.out.println("in which primary school did you start your first grade?");
                answer = Menu.scanner.next();
                if (!answer.equalsIgnoreCase(resultSet.getString(4))) {
                    System.out.println("Your answer is not correct");
                    return 1;
                } else {
                    while (true){
                        System.out.println("new password :");
                        password = Menu.scanner.next();
                        if(password.length()>=8 && password.matches("\\w+")){
                            break;
                        }
                    }
                    while (true){
                        System.out.println("repeat password:");
                        repeatPassword = Menu.scanner.next();
                        if(repeatPassword.equals(password)){
                            break;
                        }
                    }
                    PreparedStatement preparedStatement1 = connection.prepareStatement("update user set " +
                            "pass_word=? WHERE user_name=?");
                    preparedStatement1.setString(1, password);
                    preparedStatement1.setString(2, username);
                    preparedStatement1.executeUpdate();
                    System.out.println("<------------------------->");
                    return 0;
                }
            } else {
                System.out.println("password :");
                password = Menu.scanner.next();
                if (password.equals(resultSet.getString(3))) {
                    System.out.println("<------------------------->");
                    return 0;
                }else {
                    System.out.println("Your password is not correct");
                    return 1;
                }
            }
        }
        System.out.println("no user exists with this username");
        return 1;
    }
}
