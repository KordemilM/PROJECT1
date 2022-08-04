package Post;

import entity.User;
import Post.util.AppContext;
import Post.util.UserMain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Post {
    public static void run(String username) throws SQLException {
        Connection connection = DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/project?autoReconnect=true&useSSL=false","root","maziar.gohar123");
        AppContext.getDatabaseInitializer().initPostComTable(connection.createStatement());
        User user = AppContext.getUserRepos().getUserByUsername(username, connection);
        UserMain.setUser(user);
        UserMain.main(new Scanner(System.in), connection);

    }
}
