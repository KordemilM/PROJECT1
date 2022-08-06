package Post.repository;

import Post.entity.PostCom;
import Post.util.AppContext;
import entity.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class PostComRepos {




    public void addPost(PostCom post, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into PostCom(subject, content, username, likes, views, parent,datetime) values(?,?,?,?,?,?,?)"
        );
        preparedStatement.setString(1, post.getSubject());
        preparedStatement.setString(2, post.getContent());
        preparedStatement.setString(3, post.getUserName());
        preparedStatement.setInt(4, post.getLikes());
        preparedStatement.setInt(5, 0);
        preparedStatement.setInt(6, 0);
        preparedStatement.setTimestamp(7, post.getDate());
        preparedStatement.executeUpdate();
    }

    public ArrayList<PostCom> getAllPost(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "select * from PostCom WHERE parent = 0"
        );
        return getPostCom(statement, resultSet);
    }

    private ArrayList<PostCom> getPostCom(Statement statement, ResultSet resultSet) throws SQLException {
        ArrayList<PostCom> postComArrayList = new ArrayList<>();
        while (resultSet.next()) {
            PostCom postCom = new PostCom();
            postCom.setId(resultSet.getInt("id"));
            postCom.setSubject(resultSet.getString("subject"));
            postCom.setContent(resultSet.getString("content"));
            postCom.setUserName(resultSet.getString("username"));
            postCom.setLikes(resultSet.getInt("likes"));
            postCom.setViews(resultSet.getInt("views"));
            postCom.setParent(0);
            postCom.setDate(resultSet.getTimestamp("datetime"));
            postComArrayList.add(postCom);
        }
        statement.close();
        return postComArrayList;
    }

    public ArrayList<PostCom> getAllPostsByUser(String username, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "select * from PostCom WHERE parent = 0 AND username = '"+username+"'"
        );
        return getPostCom(statement, resultSet);
    }

    // get children of a post

    public ArrayList<PostCom> getChildren(int id, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "select * from PostCom WHERE parent = "+id
        );
        ArrayList<PostCom> postComArrayList = new ArrayList<>();
        while (resultSet.next()) {
            PostCom postCom = new PostCom();
            postCom.setId(resultSet.getInt("id"));
            postCom.setSubject(resultSet.getString("subject"));
            postCom.setContent(resultSet.getString("content"));
            postCom.setUserName(resultSet.getString("username"));
            postCom.setLikes(resultSet.getInt("likes"));
            postCom.setViews(resultSet.getInt("views"));
            postCom.setParent(id);
            postCom.setDate(resultSet.getTimestamp("datetime"));
            postComArrayList.add(postCom);
        }
        statement.close();
        return postComArrayList;
    }

    // get all post other than the user's post

    public ArrayList<PostCom> getAllPostOtherThanUser(String username, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "select * from PostCom WHERE parent = 0 AND username != '"+username+"'"
        );
        return getPostCom(statement, resultSet);
    }

    // add Children

    public void addChildren(PostCom post, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into PostCom(subject, content, username, likes, views, parent,datetime) values(?,?,?,?,?,?,?)"
        );
        preparedStatement.setString(1, post.getSubject());
        preparedStatement.setString(2, post.getContent());
        preparedStatement.setString(3, post.getUserName());
        preparedStatement.setInt(4, post.getLikes());
        preparedStatement.setInt(5, 0);
        preparedStatement.setInt(6, post.getParent());
        preparedStatement.setTimestamp(7, post.getDate());
        preparedStatement.executeUpdate();
    }

    // like a post

    public void likePost(PostCom post,User user, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "update PostCom set likes = likes + 1 where id = "+ post.getId()
        );
        preparedStatement.executeUpdate();
        preparedStatement.close();
        PreparedStatement preparedStatement1 = connection.prepareStatement(
                "insert into likes(post_id, assigndate,username) values(?,?,?)"
        );
        preparedStatement1.setInt(1, post.getId());
        preparedStatement1.setDate(2, Date.valueOf(LocalDate.now()));
        preparedStatement1.setString(3, user.getUserName());
        preparedStatement1.executeUpdate();
    }

    // add view to a post

    public void addView(PostCom post, User user, Connection connection) throws SQLException {
        if(!isViewed(post,user,connection)){
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update PostCom set views = views + 1 where id = "+ post.getId()
            );
            preparedStatement.executeUpdate();
            preparedStatement.close();
            PreparedStatement preparedStatement1 = connection.prepareStatement(
                    "insert into views(post_id, assigndate,username) values(?,?,?)"
            );
            preparedStatement1.setInt(1, post.getId());
            preparedStatement1.setDate(2, Date.valueOf(LocalDate.now()));
            preparedStatement1.setString(3, user.getUserName());
            preparedStatement1.executeUpdate();
        }
    }

    // get likes of a post by date

    public int getLikesByDate(PostCom post,Date date, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "select * from likes where " +
                        "post_id = " + post.getId() +
                        " and assigndate = '" + date + "'"
        );
        int likes = 0;
        while (resultSet.next()) {
            likes++;
        }
        statement.close();
        return likes;
    }

    public LinkedHashMap<Date, Integer> getLikes(PostCom post, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "select * from likes where post_id = " + post.getId() +
                        " ORDER by assigndate"
        );

        LinkedHashMap<Date, Integer> likes = new LinkedHashMap<>();
        while (resultSet.next()) {
            likes.put(resultSet.getDate("assigndate"), getLikesByDate(post, resultSet.getDate("assigndate"), connection));
        }
        statement.close();
        return likes;
    }

    // get views of a post order by date

    public int getViewsByDate(PostCom post, Date date, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "select * from views where " +
                        "post_id = " + post.getId() + " and assigndate = '" + date + "'"
        );
        int views = 0;
        while (resultSet.next()) {
            views++;
        }
        statement.close();
        return views;
    }

    public LinkedHashMap<Date, Integer> getViews(PostCom post, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "select * from views where post_id = " + post.getId() +
                        " ORDER by assigndate"
        );
        LinkedHashMap<Date, Integer> views = new LinkedHashMap<>();
        while (resultSet.next()) {
            views.put(resultSet.getDate("assigndate"), getViewsByDate(post, resultSet.getDate("assigndate"), connection));
        }
        statement.close();
        return views;
    }

    public ArrayList<PostCom> getLast10Post(String username, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "select * from PostCom where username = '"+username+"' or username in (select toId from follow where fromId = '"+username+"') order by datetime desc limit 10"
        );
        return getPostCom(statement, resultSet);
    }

    public boolean isLiked(PostCom post, User user, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "select * from likes where post_id = " + post.getId() + " and username = '" + user.getUserName() + "'"
        );
        boolean isLiked = false;
        while (resultSet.next()) {
            isLiked = true;
        }
        statement.close();
        return isLiked;
    }

    public boolean isViewed(PostCom post, User user, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "select * from views where post_id = " + post.getId() + " and username = '" + user.getUserName() + "'"
        );
        boolean isViewed = false;
        while (resultSet.next()) {
            isViewed = true;
        }
        statement.close();
        return isViewed;
    }
}
