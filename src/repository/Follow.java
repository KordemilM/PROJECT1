package repository;

import entity.User;
import utils.Menu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.prefs.Preferences;

public class Follow {

    public static void following(Connection connection) throws SQLException {
        System.out.println("Enter the desired id :");
        String follower = Menu.scanner.next();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE user_name=?");
        preparedStatement.setString(1, follower);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            User user = new User();
            user.setName(resultSet.getString(1));  user.setUserName(resultSet.getString(2));
            user.setBio(resultSet.getString(9));
            System.out.println(user.toString());

            System.out.println("Do you want to follow this person?");
            String answer = Menu.scanner.next();
            if(answer.equalsIgnoreCase("yes")){
                insertFollowing(connection,user);
                System.out.println("Now you follow "+ follower);
            }else {
                return;
            }
        }else{
            System.out.println("no user exists with this username");
            return;
        }
    }

    public static void insertFollowing(Connection connection, User user) throws SQLException {

        Preferences userPreferences = Preferences.userNodeForPackage(UserRepository.class);
        String id = userPreferences.get("id", "");

        PreparedStatement preparedStatement = connection.prepareStatement("insert into follow (fromId,told)" +
                "VALUES (?,?)");

        preparedStatement.setString(1,id);
        preparedStatement.setString(2,user.getUserName());
        preparedStatement.executeUpdate();
    }


    public static void userSuggestion(){

    }
}
