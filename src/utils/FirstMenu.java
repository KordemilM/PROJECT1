package utils;

import repository.Follow;

import java.sql.Connection;
import java.sql.SQLException;

public class FirstMenu {

    public static void run(Connection connection) throws SQLException {

        while (true){
            System.out.println("1-Follow" + "\n" +"2-User suggestion" + "\n" + "3-Back");
            switch (Integer.parseInt(Menu.scanner.next())){
                case 1:
                    Follow.follower(connection);
                    break;
                case 2:
                   Follow.userSuggestion(connection);
                    break;
                default:
                    Menu.run(connection);
            }
        }

    }
}
