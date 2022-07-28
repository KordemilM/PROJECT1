package Post.util;

import Post.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class UserMain {
    private static User user;

    public static void setUser(User user) {
        UserMain.user = user;
    }

    public static void main(Scanner scanner, Connection connection) throws SQLException {
        boolean repeat = true;
        while (repeat) {
            Menu.showMenu();
            switch (scanner.nextInt()) {
                case 1 -> {
                    AddPost.setUser(user);
                    AddPost.addPost(scanner, connection);
                    break;
                }
                case 2 -> {
                    Explore explore = new Explore();
                    explore.setUser(user);
                    explore.main(scanner, connection);
                }
                case 3 -> {
                    MyPosts.setUser(user);
                    MyPosts.main(scanner, connection);
                    break;
                }

                case 4 -> {
                    repeat = false;
                }
            }
        }
    }
}
