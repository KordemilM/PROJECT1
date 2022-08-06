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

    // get All Followers

    public ArrayList<User> getAllFollowers(String username, Connection connection) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from project.user where user_name in (select toId from project.follow where fromId = ?)");
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            User user = new User();
            user.setName(resultSet.getString("user_name"));
            user.setAccount(resultSet.getInt("business_account"));
            users.add(user);
        }
        preparedStatement.close();
        return users;
    }

    // get last 10 post of followers

    public ArrayList<PostCom> getLast10PostOfFollowers(String username, Connection connection) throws SQLException {
        ArrayList<PostCom> posts = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from project.postcom where username in (select toId from project.follow where fromId = ?) order by datetime desc limit 10");
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            PostCom post = new PostCom();
            post.setId(resultSet.getInt("id"));
            post.setUserName(resultSet.getString("username"));
            post.setContent(resultSet.getString("content"));
            post.setDate(resultSet.getTimestamp("datetime"));
            post.setLikes(resultSet.getInt("likes"));
            post.setSubject(resultSet.getString("subject"));
            post.setViews(resultSet.getInt("views"));
            post.setParent(resultSet.getInt("parent"));
            posts.add(post);
        }
        preparedStatement.close();
        return posts;
    }
}
