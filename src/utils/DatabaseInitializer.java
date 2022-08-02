package utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public void createTables(Connection connection) throws SQLException {
        userTable(connection.createStatement());
        followTable(connection.createStatement());
    }

    private void userTable(Statement statement) throws SQLException {
        statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS " +
                        "user (name varchar(255) NOT NULL," +
                        "user_name varchar(255) NOT NULL unique," +
                        "password varchar(255) NOT NULL," +
                        "security_response varchar(255) NOT NULL," +
                        "email varchar(255) NOT NULL," +
                        "phone_Number varchar(255) NOT NULL," +
                        "business_account int NOT NULL," +
                        "age int NOT NULL," +
                        "bio varchar(255)," +
                        "PRIMARY KEY (user_name))");
        statement.close();
    }

    private void followTable(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS " +
                "follow (id int NOT NULL AUTO_INCREMENT," +
                "fromId varchar(255) NOT NULL," +
                "toId varchar(255) NOT NULL," +
                "PRIMARY KEY (id))");
        statement.close();
    }
}
