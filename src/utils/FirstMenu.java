package utils;

import Chat.messenger;
import repository.Follow;
import java.sql.Connection;
import java.sql.SQLException;
import Post.Post;
import repository.SignUpIn;

public class FirstMenu {

    public static void run(Connection connection) throws SQLException {

        while (true){
            System.out.println("1-Follow" + "\n" +"2-User suggestion" + "\n" +"3-chat" +"\n"+"4-post" +"\n" + "5-Back");
            switch (Menu.scanner.next()) {
                case "1" -> Follow.follower(connection);
                case "2" -> Follow.userSuggestion(connection);
                case "3" -> messenger.run();
                case "4" -> Post.run(SignUpIn.onlineUser_username);
                default -> Menu.run(connection);
            }
        }
    }
}
