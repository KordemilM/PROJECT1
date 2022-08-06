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
            if(choice.equals("Back")) {
                return;
            }
            int postId = Integer.parseInt(choice)-1;
            PostCom post = posts.get(postId);
            ReactPost reactPost = new ReactPost();
            reactPost.setUser(user);
            reactPost.setPost(post);
            reactPost.main(scanner, connection);
        }
    }
}
