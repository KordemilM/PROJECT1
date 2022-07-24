package repository;

import entity.BusinessAccount;
import entity.User;
import utils.Menu;
import java.sql.*;
import java.util.prefs.Preferences;

public class UserRepository {

    public static void signUp(Connection connection) throws SQLException {
        String username , email , password, securityResponse , phoneNumber , bio ,repeatPassword , account;
        int age;
        User user = new User();      BusinessAccount businessAccount = new BusinessAccount();
        System.out.println("name :");  String name = Menu.scanner.next();
        while (true){
            System.out.println("username :");
            username = Menu.scanner.next();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user WHERE user_name='" + username + "'");
            if(!resultSet.next()){
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
        System.out.println("business account ? (answer with 0 or 1)");
        account = Menu.scanner.next();

         if(account.equalsIgnoreCase("yes")){
             businessAccount.setName(name);
             businessAccount.setUserName(username);
             businessAccount.setPassword(password);
             businessAccount.setAge(age);
             businessAccount.setEmail(email);
             businessAccount.setPhoneNumber(phoneNumber);
             businessAccount.setBio(bio);
             businessAccount.setSecurityResponse(securityResponse);
         }else if(account.equalsIgnoreCase("no")){
             user.setName(name);         user.setUserName(username);
             user.setPassword(password); user.setAge(age);
             user.setEmail(email);       user.setPhoneNumber(phoneNumber);
             user.setBio(bio);           user.setSecurityResponse(securityResponse);
         }

        PreparedStatement preparedStatement = connection.prepareStatement("insert into user " +
                "VALUES (?,?,?,?,?,?,?,?,?)");
        preparedStatement.setString(1,name);     preparedStatement.setString(2,username);
        preparedStatement.setString(3,password); preparedStatement.setString(4,securityResponse);
        preparedStatement.setString(5,email);    preparedStatement.setString(6,phoneNumber);
        preparedStatement.setBoolean(7,          Boolean.parseBoolean(account));
        preparedStatement.setInt(8,age);         preparedStatement.setString(9,bio);
        preparedStatement.executeUpdate();

        System.out.println("sign up was successful");
    }

    public static int login(Connection connection) throws SQLException {
        String username , password , string , answer;
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
                System.out.println("****** ?");
                answer = Menu.scanner.next();
                if (!answer.equalsIgnoreCase(resultSet.getString(4))) {
                    System.out.println("Your answer is not correct");
                    return 1;
                } else {
                    System.out.println("new password :");
                    password = Menu.scanner.next();

                    PreparedStatement preparedStatement1 = connection.prepareStatement("update user set " +
                            "pass_word=? WHERE user_name=?");
                    preparedStatement1.setString(1, password);
                    preparedStatement1.setString(2, username);
                    preparedStatement1.executeUpdate();
                    return 0;
                }
            } else {
                System.out.println("password :");
                password = Menu.scanner.next();
                if (password.equals(resultSet.getString(3))) {
                    return 0;
                }
            }
        }
        System.out.println("no user exists with this username");
        return 1;
    }

}
