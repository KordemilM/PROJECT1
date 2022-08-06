package Post.util;

import Post.entity.PostCom;
import entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class FollowerPosts {

    private static User user;

    public static void setUser(User user) {
        FollowerPosts.user = user;
    }

    public static void main(Scanner scanner, Connection connection) throws SQLException {
        while (true) {
            ArrayList<PostCom> posts = AppContext.getPostComRepos().getLast10Post(user.getUserName(), connection);
            Menu.showPostsMenu(posts);
            String choice = scanner.next();
            int postId;
            if(choice.equals("0")) {
                return;
            }
            try {
                postId = Integer.parseInt(choice)-1;
            }
            catch (Exception e) {
                System.out.println("Invalid input");
                continue;
            }
            PostCom post = posts.get(postId);
            ReactPost reactPost = new ReactPost();
            reactPost.setUser(user);
            reactPost.setPost(post);
            reactPost.main(scanner, connection);
        }
    }
}
