package Post.repository;

import Post.entity.User;

import java.sql.*;
import java.util.ArrayList;

public class UserRepos {

    // add user

    public void addUser(User user, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into User(username,isBusiness) " +
                        "values (?,?)");
        preparedStatement.setString(1, user.getName());
        preparedStatement.setBoolean(2, user.isBusiness());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    // show all users

    public ArrayList<User> getAllUsers(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet =
                statement.executeQuery("select * from user");
        ArrayList<User> users = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User();
            user.setName(resultSet.getString("username"));
            user.setBusiness(resultSet.getBoolean("isBusiness"));
            users.add(user);
        }

        statement.close();
        return users;
    }

    // get user by username

    public User getUserByUsername(String username, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from project.user where user_name = ?");
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        User user = new User();
        user.setName("");
        if (resultSet.next()) {
            user.setName(resultSet.getString("user_name"));
            user.setBusiness(resultSet.getBoolean("business_account"));
        }
        preparedStatement.close();
        return user;
    }
}
