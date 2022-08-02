package Post.util;

import Post.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class FirstPage {
    public static void firstPage(Scanner scanner, Connection connection) throws SQLException {
        boolean repeat = true;
        while (repeat){
            Menu.showLogMenu();
            switch (scanner.nextInt()) {
                case 1 -> {
                    AddNew.addNew(scanner, connection);
                    break;
                }
                case 2 -> {
                    System.out.println("Enter your name");
                    String username = scanner.next();
                    if (username.equals("Back"))
                        break;
                    User user = AppContext.getUserRepos().getUserByUsername(username, connection);
                    if (user.getName().equals("")) {
                        System.out.println("user not found");
                        break;
                    }
                    UserMain.setUser(user);
                    UserMain.main(scanner, connection);
                }
                case 3 -> {
                    System.out.println("Bye");
                    repeat = false;
                }
                default -> System.out.println("Wrong number");
            }
        }
    }
}
