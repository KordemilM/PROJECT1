package Post.util;

import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {


    public void initPostComTable(Statement statement) throws SQLException {
        statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS " +
                        "PostCom(id int NOT NULL AUTO_INCREMENT, " +
                        "subject varchar(255), " +
                        "content varchar(255), " +
                        "username varchar(255), " +
                        "likes int, " +
                        "views int, " +
                        "parent int, " +
                        "DateTime datetime, " +
                        "PRIMARY KEY (id))");
        statement.close();
    }

    public void initUserTable(Statement statement) throws SQLException {
        statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS " +
                        "User(id int NOT NULL AUTO_INCREMENT, " +
                        "username varchar(255), " +
                        "isBusiness Boolean, " +
                        "PRIMARY KEY (id))");
        statement.close();
    }

    public void initLikesTable(Statement statement) throws SQLException {
        statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS " +
                        "likes(id int NOT NULL AUTO_INCREMENT, " +
                        "post_id int, " +
                        "assigndate Date, " +
                        "PRIMARY KEY (id))");
        statement.close();
    }

    public void initViewsTable(Statement statement) throws SQLException {
        statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS " +
                        "views(id int NOT NULL AUTO_INCREMENT, " +
                        "post_id int, " +
                        "assigndate Date, " +
                        "PRIMARY KEY (id))");
        statement.close();
    }
}
