package Post.util;

import Post.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class AddNew {
    public static void addNew(Scanner scanner, Connection connection) throws SQLException {
        while (true) {
            Menu.addUserMenu();
            String input = scanner.next();
            if (input.equals("Back"))
                break;
            User user = new User();
            user.setName(input);
            input = scanner.next();
            if (input.equals("Back"))
                break;
            if(input.equals("y")){
                user.setBusiness(true);
            }
            if(input.equals("n")){
                user.setBusiness(false);
            }
            AppContext.getUserRepos().addUser(user, connection);
            if(user.isBusiness()) {
                AppContext.getDatabaseInitializer().initLikesTable(connection.createStatement());
                AppContext.getDatabaseInitializer().initViewsTable(connection.createStatement());
            }
            System.out.println("User added");
        }
    }
}
