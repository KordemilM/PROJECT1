package Post.util;

import Post.entity.PostCom;
import entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class ShowAd {
    public static void showAd(Scanner scanner, Connection connection, User user) throws SQLException {
        PostCom post = AppContext.getPostComRepos().getRandomAdsPost(user.getUserName(),connection);
        if(post == null) {
            System.out.println("No ads");
            return;
        }
        AppContext.getPostComRepos().addView(post,user, connection);
        System.out.println("Ads: " + post);
        while (true) {
            System.out.println("1. React");
            System.out.println("2. Back");
            String choice = scanner.next();
            if (choice.equals("1")) {
                ReactPost reactPost = new ReactPost();
                reactPost.setUser(user);
                reactPost.setPost(post);
                reactPost.main(scanner, connection);
            } else if (choice.equals("2")) {
                break;
            }
        }
    }
}
