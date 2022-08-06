package Post.repository;

import Post.entity.PostCom;
import entity.User;

import java.sql.*;
import java.util.ArrayList;

public class UserRepos {
    // get user by username

    public User getUserByUsername(String username, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from project.user where user_name = ?");
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        User user = new User();
        user.setName("");
        if (resultSet.next()) {
            user.setUserName(resultSet.getString("user_name"));
            user.setAccount(resultSet.getInt("business_account"));
        }
        preparedStatement.close();
        return user;
    }
}
