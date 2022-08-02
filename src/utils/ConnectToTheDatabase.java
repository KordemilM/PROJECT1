package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToTheDatabase {

    public static Connection ConnectDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/project?autoReconnect=true&useSSL=false","root","maziar.gohar123");
        return connection;
    }
}
