package Post.util;

import Post.entity.PostCom;
import entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Explore {
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void main(Scanner scanner, Connection connection) throws SQLException {
        ArrayList<PostCom> posts;
        try {
            ShowAd.showAd(scanner, connection, user);
        } catch (Exception e) {
            System.out.println("No ads");
        }
        System.out.println("----------------------------------------------------");
        try {
            posts = AppContext.getPostComRepos().getAllPosts(user.getUserName(), connection);
        } catch (Exception e) {
            System.out.println("No posts");
            return;
        }
        PostCom post;
        while (true) {
            Menu.showPostsMenu(posts);
            String input = scanner.next();
            int postNum;
            if (input.equals("0")) {
                break;
            }
            try {
                postNum = Integer.parseInt(input) - 1;
            } catch (Exception e) {
                System.out.println("Invalid input");
                continue;
            }
            post = posts.get(postNum);
            AppContext.getPostComRepos().addView(post, user, connection);
            ReactPost reactPost = new ReactPost();
            reactPost.setPost(post);
            reactPost.setUser(user);
            reactPost.main(scanner, connection);
            }
        }

    public void mainCom (PostCom post, Scanner scanner, Connection connection) throws SQLException {
        ArrayList<PostCom> posts = AppContext.getPostComRepos().getChildren(post.getId(), connection);
        while (true) {
            Menu.showPostsMenu(posts);
            String input = scanner.next();
            int postNum;
            if (input.equals("0"))
                break;
            try {
                postNum = Integer.parseInt(input) - 1;
            } catch (Exception e) {
                System.out.println("Invalid input");
                continue;
            }
            PostCom temp = posts.get(postNum);
            ReactPost reactPost = new ReactPost();
            reactPost.setPost(temp);
            reactPost.setUser(user);
            reactPost.main(scanner, connection);
        }
    }
}
