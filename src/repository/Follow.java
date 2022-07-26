package repository;

import entity.User;
import utils.Menu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.prefs.Preferences;

public class Follow {

    public static void follower(Connection connection) throws SQLException {
        System.out.println("Enter the desired id :");
        String follower = Menu.scanner.next();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE user_name=?");
        preparedStatement.setString(1, follower);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            User user = new User();
            user.setName(resultSet.getString(1));  user.setUserName(resultSet.getString(2));
            user.setBio(resultSet.getString(9));

            System.out.println(user);
            System.out.println( "followers : " + numberOfFollowers(connection,user.getUserName()));
            System.out.println( "following : " + numberOfFollowing(connection,user.getUserName()));

            System.out.println("Do you want to follow this person?");
            String answer = Menu.scanner.next();
            if(answer.equalsIgnoreCase("yes")){
                if(addFollower(connection,user)){
                    System.out.println("Now you follow "+ follower);
                }
            }else {
                return;
            }
        }else{
            System.out.println("no user exists with this username");
            return;
        }
    }

    public static boolean addFollower(Connection connection, User user) throws SQLException {
        Preferences userPreferences = Preferences.userNodeForPackage(UserRepository.class);
        String id = userPreferences.get("id", "");

        if(findFollow(connection,id,user.getUserName())) {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into follow (fromId,toId)" +
                    "VALUES (?,?)");
            preparedStatement.setString(1,id);
            preparedStatement.setString(2,user.getUserName());
            preparedStatement.executeUpdate();
            return true;
        } else
            System.out.println("you used to follow " + user.getUserName());
        return false;
    }

    public static void userSuggestion(Connection connection) throws SQLException {
        HashMap<String,Integer> map = new HashMap<>();
        Preferences userPreferences = Preferences.userNodeForPackage(UserRepository.class);
        String id = userPreferences.get("id", "");

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM follow WHERE fromId IN " +
                "(SELECT toId FROM follow WHERE fromId = ?)" +
                " AND toId NOT IN (SELECT toId FROM follow WHERE fromId = ?)");
        preparedStatement.setString(1,id);
        preparedStatement.setString(2,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            map.put(resultSet.getString(3),0);
        }

        //SELECT COUNT(*) FROM follow WHERE fromId = ? AND toId IN (SELECT toId FROM follow WHERE fromId = ?)
        //SELECT COUNT(*) FROM follow WHERE " +
        //                    "(fromId = ? OR toId = ?) AND toId IN (SELECT toId FROM follow WHERE fromId = ?)

        for (String s : map.keySet()) {
            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT COUNT(*) FROM follow WHERE" +
                    " fromId = ? AND toId IN (SELECT toId FROM follow WHERE fromId = ?)");
            preparedStatement1.setString(1,s);
            preparedStatement1.setString(2,id);
            //preparedStatement1.setString(3,id);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            if(resultSet1.next()){
                map.put(s,resultSet1.getInt(1));
            }
        }
        ArrayList<String> list = new ArrayList<>(map.keySet());
        list.sort((o1, o2) -> Integer.compare(map.get(o2), map.get(o1)));
        System.out.println(list);
    }

    public static int numberOfFollowers(Connection connection,String username) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM follow WHERE toId=?");
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        int c = 0;
        while (resultSet.next()) {
            c++;
        }
        return c;
    }

    public static int numberOfFollowing(Connection connection,String username) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM follow WHERE fromId=?");
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        int c = 0;
        while (resultSet.next()) {
            c++;
        }
        return c;
    }

    public static boolean findFollow(Connection connection,String fromId,String toId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM follow WHERE fromId=? AND toId=?");
        preparedStatement.setString(1, fromId);
        preparedStatement.setString(2, toId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return false;
        }
        return true;
    }

}
