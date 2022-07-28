package Post.util;

import Post.entity.PostCom;
import Post.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;

public class AddPost {
    private static User user;

    public static void setUser(User user) {
        AddPost.user = user;
    }

    public static void addPost(Scanner scanner, Connection connection) throws SQLException {
        while(true) {
            Menu.addPostMenu();
            PostCom postCom = new PostCom();
            String input = scanner.next();
            if (input.equals("Back"))
                return;
            postCom.setSubject(input);
            input = scanner.next();
            if (input.equals("Back"))
                return;
            postCom.setContent(input);
            postCom.setUserName(user.getName());
            postCom.setDate(Timestamp.valueOf(java.time.LocalDateTime.now()));
            postCom.setLikes(0);
            postCom.setParent(0);
            AppContext.getPostComRepos().addPost(postCom, connection);
        }
    }

    public static void addComment(PostCom post, Scanner scanner, Connection connection) throws SQLException {
        while(true) {
            Menu.addCommentMenu();
            PostCom postCom = new PostCom();
            postCom.setSubject(post.getSubject());
            String input = scanner.next();
            if (input.equals("Back"))
                return;
            postCom.setContent(input);
            postCom.setUserName(user.getName());
            postCom.setDate(Timestamp.valueOf(java.time.LocalDateTime.now()));
            postCom.setLikes(0);
            postCom.setParent(post.getId());
            AppContext.getPostComRepos().addChildren(postCom, connection);
        }
    }
}
